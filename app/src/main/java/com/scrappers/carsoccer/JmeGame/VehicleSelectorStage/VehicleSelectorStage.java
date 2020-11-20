package com.scrappers.carsoccer.JmeGame.VehicleSelectorStage;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;
import com.scrappers.carsoccer.JmeGame.InGameEffects.NitroStateStage;
import com.scrappers.carsoccer.JmeGame.VehicleBuilder.VehicleGarage;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

import static com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness.jmeHarness;

public class VehicleSelectorStage extends BaseAppState {
    private Node vehicle;
    private Node rootNode;
    private String[] availableSpaceShips;
    private DirectionalLight stageDirectLight;
    private DirectionalLight metallicLight;
    private boolean startGame=false;
    private Label spec;
    private Container container;
    private AmbientLight ambientLight;


    public VehicleSelectorStage(Node rootNode, String[] availableSpaceShips){
        this.rootNode=rootNode;
        this.availableSpaceShips=availableSpaceShips;
    }

    public String[] getAvailableSpaceShips() {
        return availableSpaceShips;
    }

    @Override
    protected void initialize(Application app) {

        ambientLight = new AmbientLight(ColorRGBA.White);
        rootNode.addLight(ambientLight);


        rootNode.attachChild(app.getAssetManager().loadModel("Scenes/VehicleSelectorStage.j3o"));
        vehicle = (Node) rootNode.getChild("Vehicle");
        vehicle.detachAllChildren();

        VehicleGarage vehicleGarage =new VehicleGarage(availableSpaceShips[0],app.getAssetManager());
        vehicleGarage.initializeVehicle()
                     .paintChassisMaterial(new ColorRGBA(0f,1f,3f,1f), "Textures/carTex.jpg")
                     .paintAddOnsMaterial(null, "Textures/carTex.jpg")
                     .paintBackLightsMaterial(ColorRGBA.Red,"")
                     .paintFrontLightsMaterial(ColorRGBA.White,"")
                     .paintGlassMaterial(ColorRGBA.BlackNoAlpha,"")
                     .paintMirrorsMaterial(ColorRGBA.White,"")
                     .paintU_TurnsMaterial(ColorRGBA.Yellow,"")
                     .paintNitroMaterial(ColorRGBA.Blue, "Textures/carTex.jpg");
        Node nitroNode=((Node)((Node) vehicleGarage.getChassis()).getChild("nitro"));
        app.getStateManager().attach(new NitroStateStage(app.getAssetManager(),nitroNode,Vector3f.UNIT_Z.negate().mult(1f),"nitroEffect",ColorRGBA.Cyan,ColorRGBA.Blue));

        vehicle.attachChild(vehicleGarage.getChassis().scale(4f));

        ChaseCamera vehicleStageCamera = new ChaseCamera(app.getCamera(), vehicle);
        vehicleStageCamera.setDefaultDistance(-25);
        vehicleStageCamera.setChasingSensitivity(0.05f);
        vehicleStageCamera.setSmoothMotion(true);


        vehicleStageCamera.registerWithInput(app.getInputManager());


        stageDirectLight = new DirectionalLight();
        stageDirectLight.setDirection(rootNode.getChild("stage").getLocalTranslation().negate());
        stageDirectLight.setColor(ColorRGBA.Blue);
        rootNode.addLight(stageDirectLight);


        metallicLight = new DirectionalLight();
        metallicLight.setDirection(rootNode.getChild("stage").getLocalTranslation().negate());
        metallicLight.setColor(ColorRGBA.Blue);
        vehicle.addLight(metallicLight);
        Geometry sky = (Geometry) SkyFactory.createSky(app.getAssetManager(),app.getAssetManager().loadTexture("Textures/sky.jpg"), Vector3f.UNIT_XYZ, SkyFactory.EnvMapType.EquirectMap);
        sky.getMaterial().getAdditionalRenderState().setDepthFunc(RenderState.TestFunction.LessOrEqual);
        rootNode.attachChild(sky);


    }


    public void changeVehicle(String selectedSpaceShip) {
        try{
            Thread.sleep(50);
            vehicle=(Node) rootNode.getChild("Vehicle");
            vehicle.detachAllChildren();
            vehicle.attachChild(getApplication().getAssetManager().loadModel(selectedSpaceShip).scale(1.5f));

            metallicLight = new DirectionalLight();
            metallicLight.setDirection(rootNode.getChild("stage").getLocalTranslation().negate());
            metallicLight.setColor(ColorRGBA.Blue);
            vehicle.addLight(metallicLight);
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
    }



    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        Controller controller=new Controller(jmeHarness,this);
        controller.initializeGameControllers();
    }

    @Override
    protected void onDisable() {

    }



    }
