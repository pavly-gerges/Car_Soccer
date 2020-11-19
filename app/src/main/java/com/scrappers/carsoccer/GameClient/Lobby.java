package com.scrappers.carsoccer.GameClient;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.scrappers.carsoccer.JmeGame.GameNodes;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.R;

import androidx.appcompat.app.AppCompatActivity;

public class Lobby extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        ((TextView)findViewById(R.id.playerName)).setText(GameStructure.getPlayerAccount());
        if(GameStructure.getNpcAccount()!=null){
            ((TextView) findViewById(R.id.npcName)).setText(GameStructure.getNpcAccount());
        }else{
            ((TextView) findViewById(R.id.npcName)).setText("No NPC");
        }

        GameNodes gameNodes=new GameNodes();
        gameNodes.checkForStart(this);
        findViewById(R.id.action).setOnClickListener(view -> {
            gameNodes.setMeReady();
            view.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        GameNodes gameNodes=new GameNodes();
        gameNodes.removePlayer();
        gameNodes.removeNPC();
        gameNodes.removeRoom();
    }
}