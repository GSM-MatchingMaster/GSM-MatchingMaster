package com.example.matchingmaster.domain.creatematch.repository;

import com.example.matchingmaster.domain.creatematch.entity.Match;
import com.example.matchingmaster.domain.creatematch.entity.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByHostUserIdAndStatusIn(String hostUserId, Collection<MatchStatus> statuses);
}
