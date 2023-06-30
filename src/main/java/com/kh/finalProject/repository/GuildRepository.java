package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    Optional<Guild> findById(Long id);
}
