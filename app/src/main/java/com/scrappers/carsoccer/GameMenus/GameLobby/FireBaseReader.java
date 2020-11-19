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
        if(GameStructure.getNpcAccount()!=null){
            ((TextView) appCompatActivity.findViewById(R.id.npcName)).setText(GameStructure.getNpcAccount());
        }else{
            ((TextView) appCompatActivity.findViewById(R.id.npcName)).setText("No NPC");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
