package com.molps.matchdetails;


public class PlayerBasic implements PlayerStats {


    private boolean radiantWin;
    private long duration;
    private int radiantScore;
    private int direScore;

    public PlayerBasic(boolean radiantWin, long duration, int radiantScore, int direScore) {
        this.radiantWin = radiantWin;
        this.duration = duration;
        this.radiantScore = radiantScore;
        this.direScore = direScore;
    }



    public boolean isRadiantWin() {
        return radiantWin;
    }

    public long getDuration() {
        return duration;
    }

    public int getRadiantScore() {
        return radiantScore;
    }

    public int getDireScore() {
        return direScore;
    }

    @Override
    public int getPlayerStatsId() {
        return PlayerStats.ID_BASIC;
    }
}
