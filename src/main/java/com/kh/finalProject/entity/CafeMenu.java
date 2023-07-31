package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_cafe_menu")
@Getter @Setter
@NoArgsConstructor
public class CafeMenu {
    @Id
    @Column(name = "cafe_menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
