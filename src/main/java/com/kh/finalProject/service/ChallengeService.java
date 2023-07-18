package com.kh.finalProject.service;

import com.kh.finalProject.dto.ChallengeDto;
import com.kh.finalProject.entity.Challenge;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.MyChallenge;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.ChallengeRepository;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.MyChallengeRepository;
import com.kh.finalProject.repository.UserRepository;
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
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final MyChallengeRepository myChallengeRepository;
    private final MemberRepository memberRepository;

    // 챌린지 조회
    public List<ChallengeDto> getChallengeList() {
        List<Challenge> challenges = challengeRepository.findAll();
        List<ChallengeDto> challengeDtoList = new ArrayList<>();
        for (Challenge challenge : challenges) {
            ChallengeDto challengeDto = new ChallengeDto();
            challengeDto.setId(challenge.getId());
            challengeDto.setChallengeName(challenge.getChallengeName());
            challengeDto.setThumbnail(challenge.getThumbnail());
//            challengeDto.setDetail(challenge.getDetail());
//            challengeDto.setCount(challenge.getCount());
            challengeDtoList.add(challengeDto);
        }
        return challengeDtoList;
    }

    // 챌린지 신청
    public boolean applyChallenge(Long challengeId, Long memberId) {
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
        Optional<Member> member = memberRepository.findByMemberNum(memberId);

        if(challenge.isPresent() && member.isPresent()) {
            MyChallenge myChallenge = new MyChallenge();
            myChallenge.setChallenge(challenge.get());
            myChallenge.setMember(member.get());
            MyChallenge newData = myChallengeRepository.save(myChallenge);
            return true;
        } else {
            throw new IllegalArgumentException("챌린지 신청을 실패하였습니다.");
        }
    }
}
