package edu.upc.gessi.glidegamificationengine.type;

import java.sql.Date;

public enum StateType {
    Preparation, Playing, Finished;

    public static StateType getState(Date startDate, Date endDate) {
        Date now = new Date(System.currentTimeMillis());
        if (now.before(startDate)) {
            return StateType.Preparation;
        } else if (now.after(endDate)) {
            return StateType.Finished;
        } else {
            return StateType.Playing;
        }
    }
}
