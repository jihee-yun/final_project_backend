package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "t_region")
public class Region {
    @Id
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_parent")
    private String parentRegion;

    private int nx;
    private int ny;

    @Embedded
    private Weather weather;

    // 날씨 갱신
    public void updateRegionWeather(Weather weather) {
        this.weather = weather;
    }
}
