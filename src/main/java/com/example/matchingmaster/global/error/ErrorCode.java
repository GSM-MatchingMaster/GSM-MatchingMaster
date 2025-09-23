package com.example.matchingmaster.global.error;

public enum ErrorCode {
    INVALID_SIZE("인원 수는 2~30 사이여야 합니다."),
    INVALID_SPORT("종목은 비어 있을 수 없습니다."),
    DUPLICATE_MATCH("이미 진행 중인 경기가 있습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
