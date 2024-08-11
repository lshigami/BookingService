package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "homestay")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SqlResultSetMapping(
        name = "HomeStaySearchMapping",
        classes = @ConstructorResult(
                targetClass = com.example.demo.dto.response.HomeStaySearchResponse.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "longitude", type = Double.class),
                        @ColumnResult(name = "latitude", type = Double.class),
                        @ColumnResult(name = "night_amount", type = Double.class),
                        @ColumnResult(name = "total_amount", type = Double.class)
                }
        )
)
public class HomeStay  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "homeStay",fetch = FetchType.LAZY)
//    @JsonManagedReference
    private List<HomeStayImage> images;

//    @Column(name = "geom", columnDefinition = "geometry(Point,3857)")
//    private Point geom;

    @Column(name = "cost")
    private Double cost;
}
