package com.scrappers.carsoccer.GameClient.Rooms;

public class Model {
    private String roomID;
    private long playersNum;


    public Model(String roomID){
        this.roomID=roomID;
    }
    public String getRoomID() {
        return roomID;
    }


    public void setPlayersNum(long playersNum) {
        this.playersNum = playersNum;
    }

    public long getPlayersNum() {
        return playersNum;
    }
}
