package com.kh.finalProject.service;

import com.kh.finalProject.dto.MyChallengeDto;
import com.kh.finalProject.entity.Challenge;
import com.kh.finalProject.entity.MyChallenge;
import com.kh.finalProject.repository.MyChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyChallengeService {
    private final MyChallengeRepository myChallengeRepository;

    // 마이 챌린지 조회
    public List<MyChallengeDto> getMyChallengeList(Long userNum) {
        log.info("userNum : " + userNum);
        Optional<MyChallenge> myChallenges = myChallengeRepository.findById(userNum);
        List<MyChallengeDto> myChallengeDtoList = new ArrayList<>();

        if(myChallenges.isPresent()) {
            log.info("getId : " + myChallenges.get().getChallenges());
            for(Challenge challenge : myChallenges.get().getChallenges()) {
                log.info("getMyChallenge : " + challenge.getMyChallenge());
                log.info("getChallengeName : " + challenge.getChallengeName());
                log.info("getId : " + challenge.getId());
            }
        }


        return myChallengeDtoList;
    }
}
