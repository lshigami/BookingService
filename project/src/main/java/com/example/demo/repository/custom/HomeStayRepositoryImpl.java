package com.example.demo.repository.custom;

import com.example.demo.dto.response.HomeStaySearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HomeStayRepositoryImpl implements HomeStayRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HomeStaySearchResponse> search(Double longitude, Double latitude, Integer guests, LocalDate checkinDate, LocalDate checkoutDate, Double radius, Integer nights) {
        String sql = """
            with distance as (
                select st_transform(st_setsrid(st_makepoint(:longitude,:latitude),4326),3857) as geom
            ),
            homestay_images as (
                select home_stay_id, array_agg(image) as images
                from home_stay_images
                group by home_stay_id
            )
            select h.id, h.description, h.longitude, h.latitude, avg(ha.price) as night_amount, sum(ha.price) as total_amount,
                   array_to_string(hsi.images, ',') as images
            from homestay h
            join homestay_availability ha on ha.homestay_id = h.id
            left join homestay_images hsi on h.id = hsi.home_stay_id
            cross join distance d
            where h.guests >= :guests
            and ha.date between :checkinDate and :checkoutDate
            and ha.status = 0
            and st_dwithin(h.geom, d.geom, :radius)
            group by h.id, h.geom, d.geom, hsi.images
            having count(ha.date) = :nights
            order by h.geom <-> d.geom;
        """;

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("longitude", longitude)
                .setParameter("latitude", latitude)
                .setParameter("guests", guests)
                .setParameter("checkinDate", checkinDate)
                .setParameter("checkoutDate", checkoutDate)
                .setParameter("radius", radius)
                .setParameter("nights", nights);

        List<Object[]> results = query.getResultList();
        List<HomeStaySearchResponse> homeStaySearchResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long id = ((Number) result[0]).longValue();
            String description = (String) result[1];
            Double lon =  ((Number) result[2]).doubleValue();
            Double lat = ( ((Number) result[3]).doubleValue());
            Double nightAmount =  ((Number) result[4]).doubleValue();
            Double totalAmount =  ((Number) result[5]).doubleValue();
            String imagesString = (String) result[6];
            List<String> images = imagesString != null ? List.of(imagesString.split(",")) : List.of();

            HomeStaySearchResponse response = new HomeStaySearchResponse(id, description, lon, lat, nightAmount, totalAmount, images);
            homeStaySearchResponses.add(response);
        }

        return homeStaySearchResponses;
    }
}
