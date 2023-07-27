package com.kh.finalProject.service;


import com.kh.finalProject.dto.QnaDto;

import com.kh.finalProject.entity.QnaList;
import com.kh.finalProject.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor

public class QnaService {

    private final QnaRepository qnaRepository;

    public List<QnaDto> searchDataLoad(String keyword) {
        List<QnaDto> qnas = new ArrayList<>();
        List<QnaList> qnaList = qnaRepository.findWithKeyword(keyword);
        log.info("qna = {}", qnaList);
        for (QnaList q : qnaList) {
            QnaDto qnaDtos = new QnaDto();
            qnaDtos.setQuestion(q.getQuestion());
            qnaDtos.setAnswer(q.getAnswer());

            qnas.add(qnaDtos);
        }

        return qnas;
    }
}
