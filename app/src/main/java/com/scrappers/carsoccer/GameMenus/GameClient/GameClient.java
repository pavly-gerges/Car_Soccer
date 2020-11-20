package com.scrappers.carsoccer.GameMenus.GameClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;

import com.scrappers.carsoccer.GameMenus.GameLobby.Lobby;
import com.scrappers.carsoccer.GameMenus.OptionPane;
import com.scrappers.carsoccer.GameMenus.GameClient.Rooms.FireBaseReader;
import com.scrappers.carsoccer.GameMenus.GameClient.Rooms.RoomsAdapter;
import com.scrappers.carsoccer.GameMenus.SystemVisibilityUI;
import com.scrappers.carsoccer.JmeGame.GameDataNodes;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GameClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_client);
        new SystemVisibilityUI(this).setGameMode();
        findViewById(R.id.addNewRoom).setOnClickListener(listener ->{
            OptionPane optionPane=new OptionPane(GameClient.this);
            optionPane.showDialog(R.layout.dialog_new_room, Gravity.CENTER);
            optionPane.getInflater().findViewById(R.id.createRoom).setOnClickListener(view -> {
                optionPane.getAlertDialog().dismiss();
                String roomName=String.valueOf(((EditText)optionPane.getInflater().findViewById(R.id.roomID)).getText());
                GameStructure.setRoomID(roomName);
                /* set an admin player */
                GameStructure.setPlayer("player1");
                GameStructure.setAdmin(true);
                GameStructure.setNPC("player2");
                /* write a command to create the room */
                GameDataNodes gameDataNodes =new GameDataNodes();
                gameDataNodes.addPlayer();
                gameDataNodes.addNPC();
                startActivity(new Intent(GameClient.this, Lobby.class));
            });
        });
        RecyclerView recyclerView=findViewById(R.id.roomsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RoomsAdapter roomsAdapter=new RoomsAdapter(this,new ArrayList<>());
        recyclerView.setAdapter(roomsAdapter);
        FireBaseReader fireBaseReader=new FireBaseReader(roomsAdapter);
        fireBaseReader.initializeFireBase();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            new SystemVisibilityUI(this).setGameMode();
        }
    }
}