package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByMemberMemberNum(Long memberNum);

    // 회원 객체(내부 값)로 카페 조회
    List<Cafe> findByMember(Member member);

    // 지역별 카페 조회
    List<Cafe> findByRegion(String region);

    // 전체 카페 조회(+인기순)
    List<Cafe> findAllByOrderByLikeCountDesc();

    // 전체 카페 조회(+별점순)
    List<Cafe> findAllByOrderByScoreDesc();

    // 지역별 카페 조회(+인기순)
    List<Cafe> findByRegionOrderByLikeCountDesc(String region);

    // 지역별 카페 조회(+별점순)
    List<Cafe> findByRegionOrderByScoreDesc(String region);

    // 카페 디테일 정보 조회
    @Query("SELECT c FROM Cafe c LEFT JOIN FETCH c.cafeMenuList cm WHERE c.id = :cafe_id")
    Optional<Cafe> findByIdWithDetails(@Param("cafe_id") Long cafeId);

    // 카페 이름 & 메뉴로 검색 조회
    @Query("SELECT c FROM Cafe c LEFT JOIN CafeMenu menu ON c.id = menu.id WHERE c.cafeName LIKE %:keyword% OR menu.name LIKE %:keyword% ORDER BY c.id DESC")
    List<Cafe> findWithKeyword(@Param("keyword") String keyword);

    // 카페 이름만 등록 회원 번호로 조회
    @Query("SELECT c.cafeName FROM Cafe c WHERE c.member.memberNum = :memberNum")
    List<String> findCafeNamesByMemberNum(@Param("memberNum") Long memberNum);

}
