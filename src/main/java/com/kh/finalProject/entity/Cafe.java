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
    private String cafeName;
    private String thumbnail;
    private String address;
    private String detailIntro;
    private String intro;
    private String region;
    private String tel;
    private Long likeCount;

    @OneToMany(mappedBy = "cafe")
    private List<CafeImg> cafeImgList = new ArrayList<>();

    @OneToMany(mappedBy = "cafe")
    private List<CafeMenu> cafeMenuList = new ArrayList<>();
}
