package com.example.matchingmaster.domain.creatematch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=32)
    private String guildId;

    @Column(nullable=false, length=32)
    private String channelId;

    @Column(nullable=false, length=32)
    private String hostUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private MatchSport sport;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=10)
    private MatchSession session;

    @Column(nullable=false)
    private Integer maxSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=16)
    private MatchStatus status;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    @Column(nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
        if (status == null) status = MatchStatus.OPEN;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Match create(String guildId, String channelId, String hostUserId,
                               MatchSport sport, MatchSession session, int maxSize) {
        return Match.builder()
                .guildId(guildId)
                .channelId(channelId)
                .hostUserId(hostUserId)
                .sport(sport)
                .session(session)
                .maxSize(maxSize)
                .status(MatchStatus.OPEN)
                .build();
    }
}
