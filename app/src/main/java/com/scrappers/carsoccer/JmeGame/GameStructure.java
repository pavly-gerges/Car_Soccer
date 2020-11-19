package com.scrappers.carsoccer.JmeGame;

import com.jme3.scene.Spatial;

public class GameStructure {
    private static String player;
    private static String NPC;
    private static boolean admin;
    private static String playerAccount;
    private static String npcAccount;
    private static String roomID;
    private static String selectedCar;
    private static Spatial selectedPlayGround;
    private static String selectedBall;


    public static String getSelectedCar() {
        return selectedCar;
    }

    public static void setSelectedCar(String selectedCar) {
        GameStructure.selectedCar = selectedCar;
    }

    public static Spatial getSelectedPlayGround() {
        return selectedPlayGround;
    }

    public static void setSelectedPlayGround(Spatial selectedPlayGround) {
        GameStructure.selectedPlayGround = selectedPlayGround;
    }

    public static String getSelectedBall() {
        return selectedBall;
    }

    public static void setSelectedBall(String selectedBall) {
        GameStructure.selectedBall = selectedBall;
    }

    public static String getRoomID() {
        return roomID;
    }

    public static void setRoomID(String roomID) {
        GameStructure.roomID = roomID;
    }

    public static String getPlayerAccount() {
        return playerAccount;
    }

    public static void setPlayerAccount(String playerAccount) {
        GameStructure.playerAccount = playerAccount;
    }

    public static void setPlayer(String player) {
        GameStructure.player = player;
    }

    public static String getPlayer() {
        return player;
    }

    public static void setNPC(String NPC) {
        GameStructure.NPC = NPC;
    }

    public static String getNPC() {
        return NPC;
    }

    public static void setAdmin(boolean admin) {
        GameStructure.admin = admin;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setNpcAccount(String npcAccount) {
        GameStructure.npcAccount = (npcAccount!=null) ?  (npcAccount) : "";
    }

    public static String getNpcAccount() {
        return npcAccount;
    }
}
