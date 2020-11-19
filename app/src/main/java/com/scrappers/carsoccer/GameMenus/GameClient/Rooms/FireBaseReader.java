package com.scrappers.carsoccer.GameMenus.GameClient.Rooms;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.carsoccer.JmeGame.GameDataNodes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import androidx.annotation.NonNull;

public class FireBaseReader implements ValueEventListener {
    private final RoomsAdapter roomsAdapter;
    private DatabaseReference databaseReference;
    public FireBaseReader(RoomsAdapter roomsAdapter){
        this.roomsAdapter=roomsAdapter;
    }
    public void initializeFireBase(){
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(this);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        modelArrayList.clear();
        DataSnapshot rooms=snapshot.child("Rooms");
        if(rooms.exists() && rooms.hasChildren()){
            for(DataSnapshot room:rooms.getChildren()){
                if( room.child("players").getChildrenCount()==1){
                    Model model = new Model(room.getKey());
                    model.setPlayersNum(room.child("players").getChildrenCount());
                    modelArrayList.add(model);
                }else if(room.child("players").getChildrenCount() == 0){
                    GameDataNodes gameDataNodes=new GameDataNodes();
                    gameDataNodes.removeRoomById(room.getKey());
                }
            }
        }
        roomsAdapter.setModelLinkedList(modelArrayList);
        roomsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
