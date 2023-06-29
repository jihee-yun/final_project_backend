package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.MainDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.repository.MainRepository;
import com.kh.finalProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final MainRepository mainRepository;
    public List<MainDto> mainCafe() {
        List<Cafe> cafe = mainRepository.findAll();
        log.info("cafe = {}", cafe);
        List<MainDto> mainDto = new ArrayList<>();

        for(Cafe c : cafe) {
            MainDto tmp = new MainDto();
            tmp.setId(c.getId());
            tmp.setCafeName(c.getCafeName());
            tmp.setIntro(c.getIntro());
            mainDto.add(tmp);

        }
        return mainDto;
    }
}
