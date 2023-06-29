package com.kh.finalProject.service;

import com.kh.finalProject.repository.GuildRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GuildService {
    // 생성 길드 전체 조회
    private final GuildRepository guildRepository;
    private final UserRepository userRepository;

}
