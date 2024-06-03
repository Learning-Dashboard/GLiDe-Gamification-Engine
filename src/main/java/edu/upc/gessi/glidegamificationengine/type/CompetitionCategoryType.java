package edu.upc.gessi.glidegamificationengine.type;

public enum CompetitionCategoryType {
    Ranking     { @Override public String getDescription() { return "All players are positioned in a table"; }; },
    Leaderboard { @Override public String getDescription() { return "Top-three players are positioned in a podium"; }; };

    public abstract String getDescription();
}
