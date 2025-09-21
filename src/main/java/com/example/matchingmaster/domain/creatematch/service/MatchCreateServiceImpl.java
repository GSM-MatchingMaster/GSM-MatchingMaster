package com.example.matchingmaster.domain.creatematch.service;

import com.example.matchingmaster.domain.creatematch.dto.MatchCreateRequest;
import com.example.matchingmaster.domain.creatematch.dto.MatchResponse;
import com.example.matchingmaster.domain.creatematch.entity.Match;
import com.example.matchingmaster.domain.creatematch.entity.MatchStatus;
import com.example.matchingmaster.domain.creatematch.repository.MatchRepository;
import com.example.matchingmaster.global.error.CustomException;
import com.example.matchingmaster.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchCreateServiceImpl implements MatchCreateService {

    private final MatchRepository matchRepository;

    @Override
    @Transactional
    public MatchResponse create(MatchCreateRequest request) {
        if (request.maxSize() < 2 || request.maxSize() > 30)
            throw new CustomException(ErrorCode.INVALID_SIZE);

        if (request.sport() == null)
            throw new CustomException(ErrorCode.INVALID_SPORT);

        if (matchRepository.existsByHostUserIdAndStatusIn(
                request.hostUserId(), List.of(MatchStatus.OPEN, MatchStatus.CONFIRMED)))
            throw new CustomException(ErrorCode.DUPLICATE_MATCH);

        Match m = Match.create(
                request.guildId(), request.channelId(), request.hostUserId(),
                request.sport(), request.session(), request.maxSize()
        );
        matchRepository.save(m);
        return MatchResponse.of(m);
    }
}

