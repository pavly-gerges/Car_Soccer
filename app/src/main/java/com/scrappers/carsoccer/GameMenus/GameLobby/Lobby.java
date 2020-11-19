package com.scrappers.carsoccer.GameMenus.GameLobby;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.scrappers.carsoccer.GameMenus.SystemVisibilityUI;
import com.scrappers.carsoccer.JmeGame.GameDataNodes;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.R;

import androidx.appcompat.app.AppCompatActivity;

public class Lobby extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        new SystemVisibilityUI(this).setGameMode();

       FireBaseReader fireBaseReader=new FireBaseReader(Lobby.this);
       fireBaseReader.initializeFireBase();

        GameDataNodes gameDataNodes =new GameDataNodes();
        gameDataNodes.checkForStart(this);
        findViewById(R.id.action).setOnClickListener(view -> {
            gameDataNodes.setMeReady();
            view.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            new SystemVisibilityUI(this).setGameMode();
        }
    }

    @Override
    public void onBackPressed() {
        GameDataNodes gameDataNodes =new GameDataNodes();
        gameDataNodes.removePlayer();
        gameDataNodes.removeNPC();
        finish();

    }

}