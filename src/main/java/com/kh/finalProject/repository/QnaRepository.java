package com.kh.finalProject.repository;

import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.QnaList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaList, Long> {
    // 사용자, 사업자 Qna 리스트 불러오기
    List<QnaList> findByCategory(QnaCategory category);

    // Qna 검색 조회
    @Query("SELECT qna FROM QnaList qna WHERE qna.question LIKE %:keyword% OR qna.answer LIKE %:keyword% ORDER BY qna.id DESC")
    List<QnaList> findWithKeyword(@Param("keyword") String keyword);
}
