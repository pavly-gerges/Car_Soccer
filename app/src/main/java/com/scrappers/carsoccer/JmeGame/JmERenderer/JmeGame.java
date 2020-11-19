package com.scrappers.carsoccer.JmeGame.JmERenderer;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.scrappers.carsoccer.JmeGame.VehicleSelectorStage.VehicleSelectorStage;

public class JmeGame extends SimpleApplication {

    public static SimpleApplication gameContext;
    public static BulletAppState gamePhysics;
    @Override
    public void simpleInitApp() {
        gameContext=this;
        gamePhysics=new BulletAppState();
        stateManager.attach(gamePhysics);
        gamePhysics.setDebugEnabled(false);
        VehicleSelectorStage vehicleSelectorStage =new VehicleSelectorStage(rootNode,new String[]{"ladaCar.j3o"});
        if(!stateManager.hasState(vehicleSelectorStage)){
            stateManager.attach(vehicleSelectorStage);
        }

    }

    @Override
    public void simpleUpdate(float tpf) {
    }

}