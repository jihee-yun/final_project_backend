package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Region, Long> {
}
