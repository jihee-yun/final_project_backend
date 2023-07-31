package com.kh.finalProject.entity;

import com.kh.finalProject.constant.QnaCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.kh.finalProject.constant.RequestCategory;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "t_report")
@Getter @Setter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_num")
    private Long reportNum;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length=2000)
    private String reportContent;

    @Column(nullable = false)
    private String title;

    private LocalDate reportDate;

    @Column (name = "category", length = 10)
    @Enumerated(EnumType.STRING)
    private RequestCategory category;

    @Column(name = "member_type")
    @Enumerated(EnumType.STRING)
    private QnaCategory memberType;
}
