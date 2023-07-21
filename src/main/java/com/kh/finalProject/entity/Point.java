package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_point")
@Getter @Setter @ToString
@NoArgsConstructor
public class Point {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "int default 0") // 0부터 시작
    private int point;

    @Column
    private String pointType;

    @OneToOne
    @JoinColumn(name = "user_num", referencedColumnName = "userNum")
    private User user;

    @ManyToOne
    @JoinColumn(name = "T_User_userNum")
    private Member member;

    @Column(name = "point_date")
    private LocalDate pointDate; // 포인트 변동 날짜
}
