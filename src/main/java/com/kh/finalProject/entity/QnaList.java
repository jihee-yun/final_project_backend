package com.kh.finalProject.entity;

import com.kh.finalProject.constant.QnaCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table (name = "t_qna_list")
@Getter @Setter @ToString
public class QnaList {
    @Id
    @Column(name = "qna_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "question", length = 500)
    private  String question;

    @Column (name = "answer", length = 1000)
    private String answer;

    @Column (name = "category", length = 10)
    @Enumerated(EnumType.STRING)
    private QnaCategory category;

}
