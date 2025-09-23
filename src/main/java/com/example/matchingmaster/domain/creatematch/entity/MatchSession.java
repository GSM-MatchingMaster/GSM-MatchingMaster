package com.example.matchingmaster.domain.creatematch.entity;

public enum MatchSession {
    LUNCH("점심"), DINNER("저녁");
    private final String label;
    MatchSession(String label){ this.label = label; }
    public String label(){ return label; }

    public static MatchSession fromLabel(String label){
        for (var v : values()) if (v.label.equals(label)) return v;
        throw new IllegalArgumentException("invalid session: " + label);
    }
}
