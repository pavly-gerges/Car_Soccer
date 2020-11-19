package com.scrappers.carsoccer.GameClient.Rooms;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrappers.carsoccer.JmeGame.GameStructure;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FireBaseReader implements ValueEventListener {
    private ArrayList<Model> modelArrayList;
    private final RoomsAdapter roomsAdapter;
    private final RecyclerView roomsList;
    private DatabaseReference databaseReference;
    public FireBaseReader(RecyclerView roomsList,RoomsAdapter roomsAdapter){
        this.roomsList=roomsList;
        this.roomsAdapter=roomsAdapter;
    }
    public void initializeFireBase(){
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(this);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        DataSnapshot rooms=snapshot.child("Rooms");
        if(rooms.exists() && rooms.hasChildren()){
            modelArrayList=new ArrayList<>();
            for(DataSnapshot room:rooms.getChildren()){
                if(room.hasChildren() ){
                    Model model = new Model(room.getKey());
                    model.setPlayersNum(room.child("players").getChildrenCount());
                    modelArrayList.add(model);
                }else{
                    databaseReference.child("Rooms").child(Objects.requireNonNull(room.getKey())).removeValue();
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
