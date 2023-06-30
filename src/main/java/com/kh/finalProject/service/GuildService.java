package com.kh.finalProject.service;

import com.kh.finalProject.dto.GuildDetailDto;
import com.kh.finalProject.dto.GuildDto;
import com.kh.finalProject.entity.Guild;
import com.kh.finalProject.entity.GuildMember;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.GuildMemberRepository;
import com.kh.finalProject.repository.GuildRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final GuildMemberRepository guildMemberRepository;

    // 생성 길드 전체 조회(네이티브 쿼리문 날리지 않고)
    public List<GuildDto> allGuildListWithUsers(String guildList) {
        List<Guild> guilds = guildRepository.findAll();
        List<GuildDto> guildDtos = new ArrayList<>();

        for (Guild guild : guilds) {
            Long guildId = guild.getId();
            List<GuildMember> guildMembers = guildMemberRepository.findByGuildId(guildId);
            List<String> memberProfileList = new ArrayList<>();
            int members = guildMembers.size();

            for (GuildMember guildMember : guildMembers) {
                Long userId = guildMember.getUser().getUserNum();
                Optional<User> user = userRepository.findByUserNum(userId);

                if (user.isPresent()) {
                    User userData = user.get();
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
        int members = guildMembers.size();

        for(GuildMember guildMember : guildMembers) {
            Long userId = guildMember.getUser().getUserNum();
            Optional<User> user = userRepository.findByUserNum(userId);

            if (user.isPresent()) {
                User userData = user.get();
                memberProfileList.add(userData.getProfileImgUrl());
            }
        }

        if(guild.isPresent()) {
            Guild guild1 = guild.get();
            User leader = guild1.getUser();
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
            guildDetailDto.setCountMember(members);
            guildDetailDtos.add(guildDetailDto);
        }
        return guildDetailDtos;
    }
}
