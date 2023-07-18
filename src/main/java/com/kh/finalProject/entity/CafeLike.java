package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_cafe_like")
@Getter @Setter @ToString
@NoArgsConstructor
public class CafeLike {
    @Id
    @Column(name = "cafe_like_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "T_User_userNum")
    private User user;

    @ManyToOne
    @JoinColumn(name = "T_userNum")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
