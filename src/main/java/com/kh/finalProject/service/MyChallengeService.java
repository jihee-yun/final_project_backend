package com.kh.finalProject.service;

import com.kh.finalProject.dto.MyChallengeDto;
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
public class MyChallengeService {
    private final MyChallengeRepository myChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

     // 마이 챌린지 조회
    public List<MyChallengeDto> getMyChallengeList(Long memberNum, Long challengeId) {
        log.info("memberNum : " + memberNum);
        Optional<Member> member = memberRepository.findByMemberNum(memberNum);
//        List<Challenge> challenge = challengeRepository.findAll();
        List<MyChallengeDto> myChallenges1 = new ArrayList<>();
        if (member.isPresent()) {
            List<MyChallenge> myChallenges = myChallengeRepository.findByMember(member.get());

            for (MyChallenge myChallenge : myChallenges) {
                MyChallengeDto myChallengeDto = new MyChallengeDto();
                myChallengeDto.setId(myChallenge.getId());
                myChallengeDto.setChallengeId(myChallenge.getChallenge().getId());
                myChallengeDto.setMemberNum(member.get().getMemberNum());
                myChallengeDto.setChallengeName(myChallenge.getChallenge().getChallengeName());

                myChallenges1.add(myChallengeDto);
            }
        }
        return myChallenges1;
    }
}
