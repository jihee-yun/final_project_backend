package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_my_challenge")
@Getter @Setter @ToString
@NoArgsConstructor
public class MyChallenge {
    @Id
    @Column(name = "my_challenge_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    // 장바구니
//    @OneToMany(mappedBy = "myChallenge")
//    private List<Challenge> challenges = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}
