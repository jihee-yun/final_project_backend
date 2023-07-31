package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_admin")
@Getter @Setter
@NoArgsConstructor
public class Admin {
    @Id
    @Column(name = "admin_id", unique = true)
    private String adminId;
    private String password;
    private String phone;
    private String name;
    private LocalDate birthday; // 생년월일

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    @Enumerated(EnumType.STRING)
    private Existence existence; // 탈퇴 여부
    @Enumerated(EnumType.STRING)
    private Authority authority; // 회원 종류

    @Builder
    public Admin(String adminId, String password, String name, String phone, LocalDate birthday, Gender gender, Existence existence,
                 Authority authority) {
        this.adminId = adminId;
        this.password = password;
        this.birthday = birthday;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.existence = existence;
        this.authority = authority;
    }
}
