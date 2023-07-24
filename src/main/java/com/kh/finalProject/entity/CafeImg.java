package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_cafe_img")
@Getter @Setter @ToString
@NoArgsConstructor
public class CafeImg {
    @Id
    @Column(name = "cafe_img_id", length = 2000)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
