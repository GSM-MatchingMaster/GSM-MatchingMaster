package com.example.matchingmaster.domain.creatematch.service;

import com.example.matchingmaster.domain.creatematch.dto.MatchCreateRequest;
import com.example.matchingmaster.domain.creatematch.dto.MatchResponse;

public interface MatchCreateService {
    MatchResponse create(MatchCreateRequest request);
}
