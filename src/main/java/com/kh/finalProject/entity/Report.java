package com.kh.finalProject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_report")
@Getter @Setter @ToString
@NoArgsConstructor
public class Report {
    @Id
    @Column(name = "report_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportNum;
    @Lob
    @Column(nullable = false)
    private String userId;
    private String reportContent;
    private String title;
    private LocalDate reportDate;

}
