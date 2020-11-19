package com.scrappers.carsoccer.JmeGame;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness;
import com.scrappers.carsoccer.JmeGame.Player.CommandWriter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GameDataNodes {
    private final DatabaseReference parentNode;
    public GameDataNodes(){
        parentNode= FirebaseDatabase.getInstance().getReference();
    }
    public void createAccount(String name,String password){
        parentNode.child("Accounts").child(name).child("name").setValue(name);
        parentNode.child("Accounts").child(name).child("password").setValue(password);
    }
    public void addNewRoom(){
        parentNode.child("Rooms").child(GameStructure.getRoomID()).child("name").setValue("");
    }
    public void addPlayer(){
        CommandWriter commandWriter=new CommandWriter();
        commandWriter.initializeFireBase();
        commandWriter.writeCommand("neutralize",0);
    }
    public void removePlayer(){
                parentNode.child("Rooms").
                child(GameStructure.getRoomID()).
                child("players").
                child(GameStructure.getPlayer()).removeValue();
    }
    public void removeNPC(){
        GameStructure.setNPC("");
        GameStructure.setNpcAccount("");
    }
    public void removeRoom(){
        parentNode.child("Rooms").child(GameStructure.getRoomID()).removeValue();
        GameStructure.setRoomID("");
    }
    public void removeRoomById(String roomID){
        parentNode.child("Rooms").child(roomID).removeValue();
        GameStructure.setRoomID("");
    }
    public void addNPC(){
        parentNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameStructure.setNpcAccount(String.valueOf(snapshot.
                        child("Rooms").
                        child(GameStructure.getRoomID()).
                        child("players").child(GameStructure.getNPC()).
                        child("account").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setMeReady(){
        parentNode.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isReady").setValue(true);
    }
    public void checkForStart(AppCompatActivity appCompatActivity){
        parentNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isMeReady=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isReady").getValue()));
                boolean isNPCReady=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getNPC()).child("isReady").getValue()));
                if(isMeReady && isNPCReady){
                    appCompatActivity.startActivity(new Intent(appCompatActivity, JmeHarness.class));
                    parentNode.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setMeOut(){
        parentNode.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isOut").setValue(true);
    }
    public void checkForFinish(){
        parentNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isMeOut=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isOut").getValue()));
                boolean isNPCOut=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getNPC()).child("isOut").getValue()));
                if(isMeOut && isNPCOut){
                    JmeHarness.jmeHarness.finish();
                    parentNode.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
