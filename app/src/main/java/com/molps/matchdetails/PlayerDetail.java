package com.molps.matchdetails;


public class PlayerDetail implements PlayerStats {

    private long heroDamage;
    private long buildingDamage;
    private int lastHits;
    private int denies;
    private int gpm;
    private int xpm;
    private int obs;
    private int sen;
    private int backpack0;
    private int backpack1;
    private int backpack2;

    public PlayerDetail() {
    }

    public PlayerDetail(long heroDamage, long buildingDamage, int lastHits, int denies, int gpm, int xpm, int obs, int sen, int backpack0, int backpack1, int backpack2) {
        this.heroDamage = heroDamage;
        this.buildingDamage = buildingDamage;
        this.lastHits = lastHits;
        this.denies = denies;
        this.gpm = gpm;
        this.xpm = xpm;
        this.backpack0 = backpack0;
        this.backpack1 = backpack1;
        this.backpack2 = backpack2;
        this.obs = obs;
        this.sen = sen;
    }

    public int getObs() {
        return obs;
    }

    public int getSen() {
        return sen;
    }

    public int getGpm() {
        return gpm;
    }

    public int getXpm() {
        return xpm;
    }

    public int getBackpack0() {
        return backpack0;
    }

    public int getBackpack1() {
        return backpack1;
    }

    public int getBackpack2() {
        return backpack2;
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
