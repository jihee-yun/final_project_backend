package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_challenge")
@Getter @Setter @ToString
@NoArgsConstructor
public class Challenge {
    @Id
    @Column(name = "challenge_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String challengeName;

    @Column(nullable = false, length = 1000)
    private String thumbnail;

    @Column(nullable = false, length = 1000)
    private String detail; // 챌린지 내용

    @Column (nullable = false)
    private int count; // 챌린지 몇개 성공했는지

    @Column (nullable = false)
    private LocalDateTime startTime;

    @Column (nullable = false)
    private LocalDateTime endTime;

    // 상품
    @ManyToOne
    @JoinColumn(name = "my_challenge_id")
    private MyChallenge myChallenge;

//    @OneToMany(mappedBy = "challenge")
//    private List<MyChallenge> myChallenges = new ArrayList<>();

}
