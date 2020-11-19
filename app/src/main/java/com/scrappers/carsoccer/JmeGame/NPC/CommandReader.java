package com.scrappers.carsoccer.JmeGame.NPC;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.math.Vector3f;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.JmeGame.Player.CommandWriter;

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
        DataSnapshot nPCNode=snapshot.child("Rooms").
                child(GameStructure.getRoomID()).
                child("players").
                child(GameStructure.getNPC());

        String command=String.valueOf(
                nPCNode.
                child("command").getValue());

        Object pulse = nPCNode.child("pulse").getValue();

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
                default:
                    vehicleControl.accelerate(0);
                    vehicleControl.clearForces();
                    vehicleControl.steer(0);
                    vehicleControl.brake(200);
                    break;
            }

        readExtraCommand(nPCNode,"nitro");
        readExtraCommand(nPCNode,"jump");

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private void readExtraCommand(DataSnapshot nPCNode,String command){
        DataSnapshot extraCommandNode=nPCNode.child(command);
        try {
            float x = Float.parseFloat(String.valueOf(extraCommandNode.child("x").getValue()));
            float y = Float.parseFloat(String.valueOf(extraCommandNode.child("y").getValue()));
            float z = Float.parseFloat(String.valueOf(extraCommandNode.child("z").getValue()));
            vehicleControl.applyCentralImpulse(new Vector3f(x, y, z));
        }catch (NumberFormatException | NullPointerException e){
            e.printStackTrace();
        }
        /* remove the command to stop doing the jump/nitro */
        CommandWriter commandWriter=new CommandWriter().editDataNodes();
        commandWriter.writeExtraCommand(command,new Vector3f(0f,0f,0f));
    }


}
