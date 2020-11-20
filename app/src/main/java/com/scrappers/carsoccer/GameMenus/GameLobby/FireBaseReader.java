package com.scrappers.carsoccer.GameMenus.GameLobby;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FireBaseReader implements ValueEventListener {
    private final AppCompatActivity appCompatActivity;
    public FireBaseReader(AppCompatActivity appCompatActivity){
        this.appCompatActivity=appCompatActivity;
    }
    public void initializeFireBase(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(this);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ((TextView)appCompatActivity.findViewById(R.id.playerName)).setText(GameStructure.getPlayerAccount());
        ((TextView) appCompatActivity.findViewById(R.id.npcName)).setText(GameStructure.getNpcAccount()!=null?
                GameStructure.getNpcAccount() : "No NpC");


        boolean isMeReady=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getPlayer()).child("isReady").getValue()));
        boolean isNPCReady=Boolean.parseBoolean(String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getNPC()).child("isReady").getValue()));

        appCompatActivity.findViewById(R.id.playerReady).setBackground(isMeReady?
                ContextCompat.getDrawable(appCompatActivity,R.drawable.crystal_buttons) :  ContextCompat.getDrawable(appCompatActivity,R.drawable.nothing) );

        appCompatActivity.findViewById(R.id.NPCReady).setBackground(isNPCReady?
                ContextCompat.getDrawable(appCompatActivity,R.drawable.fireengine_circle_buttons) : ContextCompat.getDrawable(appCompatActivity,R.drawable.nothing));

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
