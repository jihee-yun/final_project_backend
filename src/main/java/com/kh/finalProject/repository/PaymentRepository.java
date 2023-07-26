package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMember (Member member);
    List<Payment> findByMemberAndPaymentDateBetween(Member member, LocalDate startDate, LocalDate endDate);
    // 회원 객체로 결제 타입 찾기
    @Query("SELECT p.paymentType FROM Payment p WHERE p.member = :member")
    List<String> findPaymentTypeByMember(@Param("member") Member member);

}
