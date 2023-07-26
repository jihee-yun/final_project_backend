package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_roulette")
@Getter @Setter @ToString
@NoArgsConstructor
public class Roulette {
    @Id
    @Column(name = "roulette_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Member member;

    @Column
    private Date lastSpinTime;
}
