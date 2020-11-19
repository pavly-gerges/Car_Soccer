package com.scrappers.carsoccer.JmeGame.SceneRenderer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.util.SkyFactory;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeGame;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness;
import com.scrappers.carsoccer.JmeGame.NPC.CommandReader;
import com.scrappers.carsoccer.JmeGame.Player.Controller;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.carsoccer.JmeGame.VehicleBuilder.VehicleBuilder;

public class SceneRenderer extends BaseAppState {

    private VehicleBuilder playerCar;
    private VehicleBuilder nPC;
    public SceneRenderer(){

    }
    @Override
    protected void initialize(Application app) {
        JmeGame.gamePhysics.getPhysicsSpace().add(GameStructure.getSelectedPlayGround());
        addSky();
        /* player car*/
        playerCar =new VehicleBuilder(app);
        playerCar.buildPlayer().initializeCamera().initializePlayer();
        /* Npc Car */
        nPC =new VehicleBuilder(app);
        nPC.buildPlayer().initializeNPC();
        /* add ball */
        BallRenderer ballRenderer=new BallRenderer();
        if(!app.getStateManager().hasState(ballRenderer)){
            app.getStateManager().attach(ballRenderer);
        }

    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        Controller controller=new Controller(JmeHarness.jmeHarness,JmeGame.gameContext);
        controller.setChassis(playerCar.getChassis());
        controller.setVehicleControl(playerCar.getVehicle());
        controller.initializeController();

        CommandReader commandReader=new CommandReader(nPC.getVehicle());
        commandReader.initializeFireBase();
        commandReader.setVehicleControl(nPC.getVehicle());

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {

    }

    private void addSky() {
        Geometry sky = (Geometry) SkyFactory.createSky(getApplication().getAssetManager(),getApplication().getAssetManager().loadTexture("sky.jpg"), Vector3f.UNIT_XYZ, SkyFactory.EnvMapType.EquirectMap);
        sky.getMaterial().getAdditionalRenderState().setDepthFunc(RenderState.TestFunction.LessOrEqual);
        JmeGame.gameContext.getRootNode().attachChild(sky);
    }



}
