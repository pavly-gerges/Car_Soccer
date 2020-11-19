package com.scrappers.carsoccer.GameClient.Rooms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.carsoccer.GameClient.Lobby;
import com.scrappers.carsoccer.JmeGame.GameNodes;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.JmeGame.Player.CommandWriter;
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

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_rooms_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room.setText(modelLinkedList.get(position).getRoomID());
        holder.room.setOnClickListener(view -> {
            GameStructure.setRoomID(holder.room.getText().toString());
            GameStructure.setPlayer("player2");
            GameStructure.setAdmin(false);
            GameStructure.setNPC("player1");

            GameNodes gameNodes=new GameNodes();
            gameNodes.addPlayer();
            gameNodes.addNPC();

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
