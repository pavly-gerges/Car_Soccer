package com.scrappers.carsoccer.JmeGame.Player;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jme3.math.Vector3f;
import com.scrappers.carsoccer.JmeGame.GameStructure;

public class CommandWriter {
    private DatabaseReference databaseReference;
    public void initializeFireBase(){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("account").setValue(GameStructure.getPlayerAccount());
        databaseReference.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isReady").setValue(false);
    }
    public CommandWriter editDataNodes(){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        return this;
    }
    public void writeCommand(String command, Object pulse){
        databaseReference.
                child("Rooms").
                child(GameStructure.getRoomID()).
                child("players").
                child(GameStructure.getPlayer()).
                child("command").setValue(command);
        databaseReference.
                child("Rooms").
                child(GameStructure.getRoomID()).
                child("players").
                child(GameStructure.getPlayer()).
                child("pulse").setValue(pulse);
    }
    public void writeExtraCommand(String command,Vector3f vector3f){
        float x=vector3f.getX();
        float y=vector3f.getY();
        float z=vector3f.getZ();
        DatabaseReference commandNode=databaseReference.
                child("Rooms").
                child(GameStructure.getRoomID()).
                child("players").
                child(GameStructure.getPlayer()).
                child(command);
        commandNode.child("x").setValue(x);
        commandNode.child("y").setValue(y);
        commandNode.child("z").setValue(z);


    }
}
