package com.kh.finalProject.dto;

import com.kh.finalProject.entity.Weather;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    private Long id;
    private String region;
    private Double temp; // 온도
    private Double rainAmount; // 강수량
    private Double humid; // 습도
    private String lastUpdateTime; // 마지막 갱신 시각 (시간 단위)
    private Weather weather;

}
