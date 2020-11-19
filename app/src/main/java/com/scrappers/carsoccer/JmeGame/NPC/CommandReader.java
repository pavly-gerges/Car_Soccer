package com.scrappers.carsoccer.JmeGame.NPC;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jme3.bullet.control.VehicleControl;
import com.scrappers.carsoccer.JmeGame.GameStructure;

import androidx.annotation.NonNull;

public  class CommandReader implements ValueEventListener {
    private VehicleControl vehicleControl;
    private final float brakeForce = 300f;

    public CommandReader(VehicleControl vehicleControl){
        this.vehicleControl=vehicleControl;
    }

    public void initializeFireBase(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        databaseReference.addValueEventListener(this);
    }

    public void setVehicleControl(VehicleControl vehicleControl) {
        this.vehicleControl = vehicleControl;
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String command=String.valueOf(snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getNPC()).child("command").getValue());

        Object pulse = snapshot.child("Rooms").child(GameStructure.getRoomID()).child("players").child(GameStructure.getNPC()).child("pulse").getValue();

            switch (command){
                case "accelerate":
                    vehicleControl.accelerate(Float.parseFloat(String.valueOf(pulse)));
                    break;
                case "steerRT":
                    vehicleControl.steer(-Float.parseFloat(String.valueOf(pulse)));
                    break;
                case "steerLT":
                    vehicleControl.steer(Float.parseFloat(String.valueOf(pulse)));
                    break;
                case "reverse":
                    vehicleControl.accelerate(Float.parseFloat(String.valueOf(pulse)));
                    vehicleControl.brake(brakeForce/2);
                    break;
                case "nitro":
//                    System.err.println(String.valueOf(snapshot.child("room").child("player " + 0).child("pulse").child("x").getValue()));
//                    float x=Float.parseFloat(String.valueOf(snapshot.child("room").child("player " + 0).child("pulse").child("x").getValue()));
//                    float y=Float.parseFloat(String.valueOf(snapshot.child("room").child("player " + 0).child("pulse").child("y").getValue()));
//                    float z=Float.parseFloat(String.valueOf(snapshot.child("room").child("player " + 0).child("pulse").child("z").getValue()));
//                    vehicleControl.applyCentralImpulse(new Vector3f(x,
//                    y,
//                            z));
                    break;

                default:
                    vehicleControl.accelerate(0);
                    vehicleControl.clearForces();
                    vehicleControl.steer(0);
                    vehicleControl.brake(200);
                    break;
            }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }


}
