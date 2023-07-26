package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class CafeRepositoryTest {
    @Autowired
    CafeRepository cafeRepository;
    @Test
    @DisplayName("find-with-keyword")
            void testKeywordSearch() {
        List<Cafe> result = cafeRepository.findWithKeyword("투썸");
        for(Cafe cafe : result) {
            System.out.println(cafe);
        }
    }




}