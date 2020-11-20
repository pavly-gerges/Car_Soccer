package com.scrappers.carsoccer.GameMenus.GameClient.Rooms;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scrappers.carsoccer.GameMenus.GameLobby.Lobby;
import com.scrappers.carsoccer.JmeGame.GameDataNodes;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RoomsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final AppCompatActivity appCompatActivity;
    private ArrayList<Model> modelLinkedList;

    public RoomsAdapter(AppCompatActivity appCompatActivity,ArrayList<Model> modelLinkedList){
        this.appCompatActivity=appCompatActivity;
        this.modelLinkedList=modelLinkedList;

    }

    public void setModelLinkedList(ArrayList<Model> modelLinkedList) {
        this.modelLinkedList = modelLinkedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_rooms_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room.setText(modelLinkedList.get(position).getRoomID());
        holder.numOfPlayers.setText(String.valueOf(modelLinkedList.get(position).getPlayersNum()));
        holder.room.setOnClickListener(view -> {
            GameStructure.setRoomID(holder.room.getText().toString());
            GameStructure.setPlayer("player2");
            GameStructure.setAdmin(false);
            GameStructure.setNPC("player1");

            GameDataNodes gameDataNodes =new GameDataNodes();
            gameDataNodes.addPlayer();
            gameDataNodes.addNPC();

            appCompatActivity.startActivity(new Intent(appCompatActivity, Lobby.class));
        });
    }


    @Override
    public int getItemCount() {
        if(modelLinkedList !=null){
            return modelLinkedList.size();
        }else {
            return 0;
        }
    }
}
