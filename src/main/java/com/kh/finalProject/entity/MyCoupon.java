package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_my_coupon")
@Getter @Setter
public class MyCoupon {

    @Id
    @Column(name = "my_coupon_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "T_user_num")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
