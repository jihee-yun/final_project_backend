package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_review_like")
@Getter @Setter @ToString
@NoArgsConstructor
public class ReviewLike {
    @Id
    @Column(name = "review_like_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_num")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "T_User_userNum")
    private User user;
}
