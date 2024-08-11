package com.example.demo.service.auth;

import com.example.demo.dto.request.auth.AuthenticationRequest;
import com.example.demo.dto.request.auth.IntrospectRequest;
import com.example.demo.dto.request.auth.LogOutRequest;
import com.example.demo.dto.request.auth.RefreshRequest;
import com.example.demo.dto.request.oauth.ExchangeTokenRequest;
import com.example.demo.dto.response.auth.AuthenticationResponse;
import com.example.demo.dto.response.auth.IntrospectResponse;
import com.example.demo.dto.response.oauth.ExchangeTokenResponse;
import com.example.demo.dto.response.oauth.OutboundUserResponse;
import com.example.demo.entity.InvalidatedToken;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.InvalidatedTokenRepository;
import com.example.demo.repository.httpclient.OutboundIdentityClient;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.httpclient.OutboundUserClient;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;
    @Autowired
    private OutboundIdentityClient outboundIdentityClient;
    @Autowired
    private OutboundUserClient outboundUserClient;

    @NonFinal
    @Value("${jwt.key}")
    private String secretKey;

    @NonFinal
    @Value("${client.id}")
    private String clientId;

    @NonFinal
    @Value("${client.secret}")
    private String clientSecret;

    @NonFinal
    @Value("${redirect.uri}")
    private String redirectUri;

    @NonFinal
    private String grantType = "authorization_code";


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User findUser = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean auth = passwordEncoder.matches(request.getPassword(), findUser.getPassword());
        if (!auth) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(findUser);

        return AuthenticationResponse.builder().token(token).authenticated(auth)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        try {
            verifyToken(token);
        } catch (AppException e) {
            return IntrospectResponse.builder().actived(false).build();
        }
        return IntrospectResponse.builder().actived(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("http://localhost:8080")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
//                .claim("scope", Arrays.stream(user.getRoles().split(","))
//                        .map(String::trim)
//                        .collect(Collectors.joining(" ")) )
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println("Error in generating token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                joiner.add("ROLE_" + role.getName());
                if (!role.getPermissions().isEmpty()) {
                    role.getPermissions().forEach(permission -> {
                        joiner.add(permission.getName());
                    });
                }
            });
        }
        log.info("Scope: {}", joiner.toString());
        return joiner.toString();
    }

    private SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean signatureValid = signedJWT.verify(jwsVerifier);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime.before(new Date()) || !signatureValid) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void logout(LogOutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken());
        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jti).expiryDate(expirationTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken());


        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jti).expiryDate(expirationTime).build();

        //save invalidated token
        invalidatedTokenRepository.save(invalidatedToken);

        //generate new token
        User user = userRepository.findByUsername(signedJWT.getJWTClaimsSet().getSubject()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true)
                .build();

    }

    public AuthenticationResponse outboundAuthentication(String code) {
        log.info("Code service: {}", code);
        ExchangeTokenResponse response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType(grantType)
                .build());
        log.info("Response from outbound identity service: {}", response);

        OutboundUserResponse userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());
        log.info("User info: {}", userInfo);
        Set<Role> roleSet = Set.of(Role.builder().name("USER").build());
        User user = userRepository.findByUsername(userInfo.getEmail()).orElseGet(() -> {
            User newUser = User.builder()
                    .username(userInfo.getEmail())
                    .firstname(userInfo.getGivenName())
                    .lastname(userInfo.getFamilyName())
                    .roles(roleSet)
                    .build();
            return userRepository.save(newUser);
        });
        String token= generateToken(user);


        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
//                .token(response.getAccessToken())
                .token(token)
                .authenticated(true)
                .build();
        log.info("Authentication response: {}", authenticationResponse);
        return authenticationResponse;
    }

}


