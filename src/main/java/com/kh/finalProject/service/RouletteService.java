package com.kh.finalProject.service;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Roulette;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.RouletteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RouletteService {
    private final RouletteRepository rouletteRepository;
    private final MemberRepository memberRepository;

    public boolean hasSpunToday(Member member) {
        Roulette roulette = rouletteRepository.findByMember(member);
        return roulette != null && isSameDay(roulette.getLastSpinTime(), new Date());
    }

    public boolean spinRoulette(Long member) {
        Optional<Member> member1 = memberRepository.findByMemberNum(member);
        if(member1.isPresent()) {
            Roulette roulette = new Roulette();
            roulette.setMember(member1.get());
            roulette.setLastSpinTime(new Date());
            Roulette newData = rouletteRepository.save(roulette);
            return true;
        }
        return false;
    }

//    public List<Roulette> getRouletteHistory(Long member) {
//        Optional<Member> member1 = memberRepository.findByMemberNum(member);
//        List<Roulette> roulettes = new ArrayList<>();
//        if(member1.isPresent()) {
//
//        }
//        return rouletteRepository.findByMember(member1.get());
//    }

    public boolean checkHistory(Long memNum) {
        Optional<Member> member = memberRepository.findByMemberNum(memNum);
        if(member.isPresent()) {
            List<Roulette> roulettes = rouletteRepository.findByMemberOrderByLastSpinTimeDesc(member.get());
            if(!roulettes.isEmpty()) {
                Date latestSpin = roulettes.get(0).getLastSpinTime();
                Date now = new Date();

                return isSameDay(latestSpin, now);
            }
        }
        return false;
    }
    // 두 날짜가 동일한 날짜인지 비교
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
