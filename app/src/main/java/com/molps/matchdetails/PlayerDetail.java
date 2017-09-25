package com.molps.matchdetails;


public class PlayerDetail implements PlayerStats {

    private long heroDamage;
    private long buildingDamage;
    private int lastHits;
    private int denies;

    public PlayerDetail() {
    }

    public PlayerDetail(long heroDamage, long buildingDamage, int lastHits, int denies) {
        this.heroDamage = heroDamage;
        this.buildingDamage = buildingDamage;
        this.lastHits = lastHits;
        this.denies = denies;
    }


    public long getHeroDamage() {
        return heroDamage;
    }

    public long getBuildingDamage() {
        return buildingDamage;
    }

    public int getLastHits() {
        return lastHits;
    }

    public int getDenies() {
        return denies;
    }

    @Override
    public int getPlayerStatsId() {
        return PlayerStats.ID_DETAIL;
    }
}
