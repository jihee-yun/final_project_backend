package com.kh.finalProject.repository;

import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.entity.QnaList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaList, Long> {
    List<QnaList> findByCategory(QnaCategory category);
}
