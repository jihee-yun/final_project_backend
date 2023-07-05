package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private int totalPoint;

    @OneToOne
    @JoinColumn(name = "user_num", referencedColumnName = "userNum")
    private User user;
}
