package com.example.matchingmaster.domain.creatematch.dto;

import com.example.matchingmaster.domain.creatematch.entity.MatchSession;
import com.example.matchingmaster.domain.creatematch.entity.MatchSport;

public record MatchCreateRequest(
        String guildId,
        String channelId,
        String hostUserId,
        MatchSport sport,
        MatchSession session,
        int maxSize,
        String sourceMessageId
) {}