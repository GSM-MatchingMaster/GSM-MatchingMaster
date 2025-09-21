package com.example.matchingmaster.domain.creatematch.dto;

import com.example.matchingmaster.domain.creatematch.entity.Match;
import com.example.matchingmaster.domain.creatematch.entity.MatchSession;
import com.example.matchingmaster.domain.creatematch.entity.MatchStatus;
import com.example.matchingmaster.domain.creatematch.entity.MatchSport;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public record MatchResponse(
        Long id, MatchSport sport, MatchSession session, int maxSize, MatchStatus status,
        String guildId, String channelId, String hostUserId, OffsetDateTime createdAt
) {
    public static MatchResponse of(Match m){
        return new MatchResponse(
                m.getId(), m.getSport(), m.getSession(), m.getMaxSize(), m.getStatus(),
                m.getGuildId(), m.getChannelId(), m.getHostUserId(),
                m.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toOffsetDateTime()
        );
    }
}