package com.kh.finalProject.service;

import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.dto.GuildDetailDto;
import com.kh.finalProject.dto.GuildDto;
import com.kh.finalProject.entity.Guild;
import com.kh.finalProject.entity.GuildMember;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.GuildMemberRepository;
import com.kh.finalProject.repository.GuildRepository;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GuildService {
    private final GuildRepository guildRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final EntityManager entityManager;

    // 생성 길드 전체 조회(네이티브 쿼리문 날리지 않고)
    public List<GuildDto> allGuildListWithUsers(String guildList) {
//        List<Guild> guilds = guildRepository.findAll();
        List<Guild> guilds;

        if (guildList.equals("All")) {
            guilds = guildRepository.findAllByOrderByCreateDateDesc();
        } else if (guildList.equals("퀘스트")) {
            guilds = guildRepository.findAllByCategoryOrderByCreateDateDesc(2);
        } else if (guildList.equals("친목")) {
            guilds = guildRepository.findAllByCategoryOrderByCreateDateDesc(1);
        } else {
            throw new IllegalArgumentException("잘못된 카테고리입니다.");
        }

        List<GuildDto> guildDtos = new ArrayList<>();

        for (Guild guild : guilds) {
            Long guildId = guild.getId();
            List<GuildMember> guildMembers = guildMemberRepository.findByGuildId(guildId);
            List<String> memberProfileList = new ArrayList<>();
            int members = guildMembers.size();

            for (GuildMember guildMember : guildMembers) {
                Long userId = guildMember.getMember().getMemberNum();
                Optional<Member> member = memberRepository.findByMemberNum(userId);

                if (member.isPresent()) {
                    Member userData = member.get();
                    memberProfileList.add(userData.getProfileImgUrl());
                }
            }
            GuildDto guildDto = new GuildDto();
            guildDto.setGuildName(guild.getGuildName());
            guildDto.setId(guild.getId());
            guildDto.setRegion(guild.getRegion());
            guildDto.setThumbnail(guild.getThumbnail());
            guildDto.setLimitMember(guild.getLimitMember());
            guildDto.setMemberProfileList(memberProfileList);
            guildDto.setCountMember(members);

            guildDtos.add(guildDto);
        }
        return guildDtos;
    }

    // 길드 상세 정보 조회(네이티브 쿼리 없이)
    public List<GuildDetailDto> guildDetail(Long guildNum) {
        Optional<Guild> guild = guildRepository.findById(guildNum);
        List<GuildDetailDto> guildDetailDtos = new ArrayList<>();
        List<GuildMember> guildMembers = guildMemberRepository.findByGuildId(guildNum);
        List<String> memberProfileList = new ArrayList<>();
        List<Long> memberNumList = new ArrayList<>();
        int members = guildMembers.size();

        for(GuildMember guildMember : guildMembers) {
            Long userId = guildMember.getMember().getMemberNum();
            Optional<Member> member = memberRepository.findByMemberNum(userId);

            if (member.isPresent()) {
                Member userData = member.get();
                memberProfileList.add(userData.getProfileImgUrl());
                memberNumList.add(userData.getMemberNum());
            }
        }

        if(guild.isPresent()) {
            Guild guild1 = guild.get();
            Member leader = guild1.getMember();
            GuildDetailDto guildDetailDto = new GuildDetailDto();

            guildDetailDto.setId(guild1.getId());
            guildDetailDto.setGuildName(guild1.getGuildName());
            guildDetailDto.setLeaderId(leader.getName());
            guildDetailDto.setLeaderIntro(leader.getIntro());
            guildDetailDto.setLeaderProfileList(leader.getProfileImgUrl());
            guildDetailDto.setLimitMember(guild1.getLimitMember());
            guildDetailDto.setRegion(guild1.getRegion());
            guildDetailDto.setMeetDay(guild1.getMeetDay());
            guildDetailDto.setDetailIntro(guild1.getDetailIntro());
            guildDetailDto.setThumbnail(guild1.getThumbnail());
            guildDetailDto.setMemberProfileList(memberProfileList);
            guildDetailDto.setMemberNumList(memberNumList);
            guildDetailDto.setCountMember(members);
            guildDetailDtos.add(guildDetailDto);
        }
        return guildDetailDtos;
    }

    // 새로운 길드 생성
    public boolean createNewGuild(Long memNum, int category, String name, String intro, String detailIntro, LocalDateTime meetDay, int limitMem, String region, String thumbnail) {
        Optional<Member> member = memberRepository.findByMemberNum(memNum);
        if (member.isPresent()) {
            Guild guild = new Guild();
            guild.setMember(member.get());
            guild.setGuildName(name);
            guild.setCategory(category);
            guild.setIntro(intro);
            guild.setDetailIntro(detailIntro);
            guild.setMeetDay(meetDay);
            guild.setRegion(region);
            guild.setThumbnail(thumbnail);
            guild.setLimitMember(limitMem);
            guild.setCreateDate(LocalDateTime.now());
            guild.setExistence(Existence.Yes);
            Guild newGuild = guildRepository.save(guild);
            return true;
        } else {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다. memNum: " + memNum);
        }
    }

    // 길드 가입 회원 확인
    public int isMember(Long guildNum, Long userNum) {
        Optional<Long> isLeader = guildRepository.findUserNumByGuildIdAndMemberId(guildNum, userNum);
        Optional<Long> isMember = guildMemberRepository.findMemberNumByGuildIdAndMemberId(guildNum, userNum);

        if(isLeader.isPresent() || isMember.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    // 길드 가입하기
    public boolean joinGuild(Long guildNum, Long userNum) {
        Optional<Guild> guild = guildRepository.findById(guildNum);
        Optional<Member> member = memberRepository.findByMemberNum(userNum);

        if(guild.isPresent() && member.isPresent()) {
            GuildMember guildMember = new GuildMember();
            guildMember.setGuild(guild.get());
            guildMember.setMember(member.get());
            GuildMember newGuildMember = guildMemberRepository.save(guildMember);
            return true;
        } else {
            throw new IllegalArgumentException("오류");
        }
    }

    public List<GuildDto> getGuildInfoByMemberNum(Long memberNum) {
        Member member = new Member();
        member.setMemberNum(memberNum);
        List<Guild> guildList = guildRepository.findByMember(member);

        List<GuildDto> guildDtoList = new ArrayList<>();
        for (Guild guild : guildList) {
            GuildDto guildDto = new GuildDto();
            guildDto.setId(guild.getId());
            guildDto.setGuildName(guild.getGuildName());
            guildDto.setCategory(guild.getCategory());
            guildDto.setRegion(guild.getRegion());
            guildDto.setIntro(guild.getIntro());
            guildDto.setThumbnail(guild.getThumbnail());
            guildDtoList.add(guildDto);
        }
        return guildDtoList;
    }

}
