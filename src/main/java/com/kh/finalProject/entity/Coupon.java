package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_coupon")
@Getter @Setter @ToString
@NoArgsConstructor
public class Coupon {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false)
    private String couponName;

    @Column (nullable = false)
    private int price;

    @Column (nullable = false)
    private LocalDateTime startTime;

    @Column (nullable = false)
    private LocalDateTime endTime;
}
