package com.scrappers.carsoccer.JmeGame.SceneSelectorStage;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness;

public class SceneSelectorStage extends BaseAppState {
    private final Node rooNode;
    private final String[] availablePlaygrounds;
    private Spatial renderedPlayGround;
    public SceneSelectorStage(Node rootNode,String[] availablePlaygrounds) {
        this.rooNode=rootNode;
        this.availablePlaygrounds=availablePlaygrounds;
    }

    @Override
    protected void initialize(Application app) {
        loadSceneSelectorStage(rooNode,app.getAssetManager(),0);
    }

    public String[] getAvailablePlaygrounds() {
        return availablePlaygrounds;
    }

    /**
     * creates a simple physics test world with a floor, an obstacle and some test boxes
     *
     * @param rootNode where lights and geometries should be added
     * @param assetManager for loading assets
     */
    public void loadSceneSelectorStage(Node rootNode, AssetManager assetManager,int index) {
        rootNode.detachAllChildren();

        AmbientLight light = new AmbientLight();
        light.setColor(ColorRGBA.White);
        rootNode.addLight(light);


        Material soccerPlayGround = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        soccerPlayGround.setTexture("ColorMap", assetManager.loadTexture("Textures/soccer.jpg"));
        renderedPlayGround = assetManager.loadModel(availablePlaygrounds[index]);
        renderedPlayGround.setMaterial(soccerPlayGround);
        renderedPlayGround.setLocalTranslation(0f, -10f, 0f);
        renderedPlayGround.setLocalScale(15f, renderedPlayGround.getLocalScale().getY() * 4, 20f);
        renderedPlayGround.addControl(new RigidBodyControl(CollisionShapeFactory.createMeshShape(renderedPlayGround), 0));
        rootNode.attachChild(renderedPlayGround);
    }

    public Spatial getRenderedPlayGround() {
        return renderedPlayGround;
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        Controller controller=new Controller(JmeHarness.jmeHarness,this);
        controller.initializeGameControllers();
    }

    @Override
    protected void onDisable() {

    }
}
