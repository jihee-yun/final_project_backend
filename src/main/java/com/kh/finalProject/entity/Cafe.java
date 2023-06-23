package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe")
@Getter @Setter @ToString
@NoArgsConstructor
public class Cafe {
    @Id
    @Column(name = "cafe_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String cafeName;

    @Column(nullable = false, length = 1000)
    private String thumbnail;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 1000)
    private String detailIntro;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false, length = 30)
    private String region;

    @Column(nullable = false, length = 50)
    private String tel;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private String operatingTime; // 운영 시간

    @OneToMany(mappedBy = "cafe")
    private List<CafeImg> cafeImgList = new ArrayList<>();

    @OneToMany(mappedBy = "cafe")
    private List<CafeMenu> cafeMenuList = new ArrayList<>();
}
