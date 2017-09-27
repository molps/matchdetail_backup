package com.molps.matchdetails;


public class PlayerBasic implements PlayerStats {

    private String playerName;
    private int heroImageId;
    private int heroLevel;
    private int kills;
    private int deaths;
    private int assists;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private boolean expandend = false;


    public PlayerBasic(String playerName, int heroImageId, int heroLevel, int kills, int deaths, int assists, int item0, int item1, int item2, int item3, int item4, int item5) {
        this.playerName = playerName;
        this.heroImageId = heroImageId;
        this.heroLevel = heroLevel;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.item0 = item0;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
    }

    public boolean isExpanded() {
        return expandend;
    }

    public void setExpandend(boolean arg) {
        this.expandend = arg;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHeroImageId() {
        return heroImageId;
    }

    public int getHeroLevel() {
        return heroLevel;
    }

    public int getItem0() {
        return item0;
    }

    public int getItem1() {
        return item1;
    }

    public int getItem2() {
        return item2;
    }

    public int getItem3() {
        return item3;
    }

    public int getItem4() {
        return item4;
    }

    public int getItem5() {
        return item5;
    }

    public String getKDA() {
        return kills + "/" + deaths + "/" + assists;
    }

    @Override
    public int getPlayerStatsId() {
        return PlayerStats.ID_BASIC;
    }
}
