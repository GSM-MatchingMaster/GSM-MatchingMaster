package com.example.matchingmaster.domain.creatematch.entity;

public enum MatchSport {
    축구("축구"), 농구("농구"), 배구("배구"), 배드민턴("배드민턴"), 탁구("탁구");
    private final String label;
    MatchSport(String label){ this.label = label; }
    public String label(){ return label; }
    public static MatchSport fromLabel(String label){
        for (var v : values()) if (v.label.equals(label)) return v;
        throw new IllegalArgumentException("invalid sport: " + label);
    }
}
