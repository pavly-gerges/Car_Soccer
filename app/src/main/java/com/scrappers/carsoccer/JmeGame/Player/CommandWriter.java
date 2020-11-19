package com.scrappers.carsoccer.JmeGame.Player;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.carsoccer.JmeGame.GameStructure;

public class CommandWriter {
    private DatabaseReference databaseReference;
    public void initializeFireBase(){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("account").setValue(GameStructure.getPlayerAccount());
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isReady").setValue(false);
    }
    public void writeCommand(String command, Object pulse){
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("command").setValue(command);
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("pulse").setValue(pulse);
    }
}
