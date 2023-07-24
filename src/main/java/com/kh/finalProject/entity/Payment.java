package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_payment")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Payment { // 결제 내역 저장 테이블
    @Id
    @Column(name = "payment_id_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "t_member_member_num_pk")
    private Member member;

    @Column(name = "point")
    private int point;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_date")
    private LocalDate paymentDate;
}
