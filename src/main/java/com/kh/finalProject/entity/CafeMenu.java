package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cafe_menu")
@Getter @Setter @ToString
@NoArgsConstructor
public class CafeMenu {
    @Id
    @Column(name = "cafe_menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
