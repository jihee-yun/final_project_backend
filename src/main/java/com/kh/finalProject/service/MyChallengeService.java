package com.kh.finalProject.service;

import com.kh.finalProject.dto.MyChallengeDto;
import com.kh.finalProject.entity.MyChallenge;
import com.kh.finalProject.repository.MyChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyChallengeService {
    private final MyChallengeRepository myChallengeRepository;

    // 마이 챌린지 조회
    public List<MyChallengeDto> getMyChallengeList(Long userNum) {
        List<MyChallenge> myChallenges = myChallengeRepository.findByUser(userNum);
        List<MyChallengeDto> myChallengeDtoList = new ArrayList<>();

        for (MyChallenge myChallenge : myChallenges) {
            MyChallengeDto myChallengeDto = new MyChallengeDto();
            myChallengeDto.setId(myChallenge.getId());
            // 오류 부분
            myChallengeDto.setChallengeName(myChallengeDto.getChallengeName());
            myChallengeDto.setUserNum(myChallengeDto.getUserNum());
            myChallengeDto.setChallengeId(myChallengeDto.getChallengeId());
            myChallengeDtoList.add(myChallengeDto);
        }
        return myChallengeDtoList;
    }
}
