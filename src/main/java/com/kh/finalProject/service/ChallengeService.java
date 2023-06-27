package com.kh.finalProject.service;

import com.kh.finalProject.dto.ChallengeDto;
import com.kh.finalProject.entity.Challenge;
import com.kh.finalProject.repository.ChallengeRepository;
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
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    // 챌린지 조회
    public List<ChallengeDto> getChallengeList() {
        List<Challenge> challenges = challengeRepository.findAll();
        List<ChallengeDto> challengeDtoList = new ArrayList<>();
        for (Challenge challenge : challenges) {
//            System.out.println(challenge.getChallengeName());
//            System.out.println(challenge.getThumbnail());
//            System.out.println(challenge.getDetail());
//            System.out.println(challenge.getCount());
            ChallengeDto challengeDto = new ChallengeDto();
            challengeDto.setChallengeName(challenge.getChallengeName());
            challengeDto.setThumbnail(challenge.getThumbnail());
            challengeDto.setDetail(challenge.getDetail());
            challengeDto.setCount(challenge.getCount());
            challengeDtoList.add(challengeDto);
        }
        return challengeDtoList;
    }
}
