package edu.upc.gessi.glidegamificationengine.type;

public enum CompetitionCategoryType {
    Ranking     { @Override public String getDescription() { return "Players are ranked based on their performance"; }; },
    Leaderboard { @Override public String getDescription() { return "Players are ranked based on their performance and the leaderboard is visible to all players"; }; };

    public abstract String getDescription();
}
