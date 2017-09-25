package com.molps.matchdetails;



public class PlayerName implements PlayerStats {
    private String name;
    private boolean isExpanded = false;

    public PlayerName(String name) {
        this.name = name;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getPlayerStatsId() {
        return PlayerStats.ID_NAME;
    }
}
