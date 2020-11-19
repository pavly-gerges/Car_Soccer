package com.scrappers.carsoccer.JmeGame.VehicleBuilder;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeGame;

public class VehicleGarage {
    private final String vehicle;
    private final AssetManager assetManager;
    private Spatial chassis;

    /**
     *
     * @param vehicle
     * @param assetManager
     */
    public VehicleGarage(String vehicle, AssetManager assetManager){
        this.vehicle=vehicle;
        this.assetManager=assetManager;
    }

    public VehicleGarage initializeVehicle(){
        chassis =assetManager.loadModel(vehicle);
        return this;
    }

    public Spatial getChassis() {
        return chassis;
    }

    public VehicleGarage paintGlassMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("glass").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }
    public VehicleGarage paintChassisMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("chassis").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }
    public VehicleGarage paintAddOnsMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("addOns").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }
    public VehicleGarage paintFrontLightsMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("frontLight").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }

    public VehicleGarage paintBackLightsMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("backLights").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }
    public VehicleGarage paintU_TurnsMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("uTurns").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }
    public VehicleGarage paintMirrorsMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("mirrors").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }

    public VehicleGarage paintNitroMaterial(ColorRGBA colorRGBA, String matTex){
        ((Node) chassis).getChild("nitro").setMaterial(createMat(colorRGBA,matTex));
        return this;
    }

    private Material createMat(ColorRGBA colorRGBA, String Tex){
        Material material=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        if(colorRGBA !=null){
            material.setColor("Color", colorRGBA);
        }
        if(Tex.length() >1){
            Texture texture=assetManager.loadTexture(Tex);
            material.setTexture("ColorMap",texture);
        }
        material.setReceivesShadows(true);

        return material;
    }

    /**
     * A custom class for advanced Vehicle workout : Tyres , Tyres formula , Speed formulas , breaks , etc.
     */
    public static class VehicleAutoShop{
        private Spatial chassis;
        private Node vehicleNode;
        private VehicleControl vehicle;

        public VehicleAutoShop(Spatial chassis){
            this.chassis=chassis;
        }

        public VehicleControl getVehicle() {
            return vehicle;
        }

        public Spatial getChassis() {
            return chassis;
        }

        public VehicleAutoShop initializeChassis(){
            //create a compound shape and attach the BoxCollisionShape for the car body at 0,1,0
            //this shifts the effective center of mass of the BoxCollisionShape to 0,-1,0
            //create vehicle node
            chassis.setLocalScale(2.2f,2.2f,2.2f);
            chassis.setLocalTranslation(new Vector3f(0, 1, 0));

            vehicleNode=new Node("vehicleNode");
            vehicleNode.attachChild(chassis);
            return this;
        }
        public VehicleAutoShop initializeCamera(){
            ChaseCamera chaseCam=new ChaseCamera(JmeGame.gameContext.getCamera(), vehicleNode);
            chaseCam.setDefaultDistance(-15f);
            chaseCam.registerWithInput(JmeGame.gameContext.getInputManager());
            chaseCam.setDragToRotate(true);
            return this;
        }
        public VehicleAutoShop initializeVehiclePhysics(){
            CompoundCollisionShape compoundShape = (CompoundCollisionShape)CollisionShapeFactory.createDynamicMeshShape(chassis);
            compoundShape.translate(new Vector3f(0,1,0));
            vehicle = new VehicleControl(compoundShape, 600f);
            vehicleNode.addControl(vehicle);

            return this;
        }
        public VehicleAutoShop initializePlayer(){
            vehicle.setPhysicsLocation(new Vector3f(0.2489425f, -9.613701f, -458.60062f));
            return this;
        }
        public VehicleAutoShop initializeNPC(){
            vehicle.setPhysicsLocation(new Vector3f(0.2489425f, -9.613701f, 458.60062f));
            vehicle.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.PI,Vector3f.UNIT_Y));
            return this;
        }
        public VehicleAutoShop loadWheels(){
            //Create four wheels and add them at their locations
            Vector3f wheelDirection = new Vector3f(0,-1F, 0); // was 0, -1, 0
            Vector3f wheelAxle = new Vector3f(-6, 0, 0); // was -1, 0, 0
            float radius = 0.5f;
            float restLength = 0.1f;
            float yOff = radius;
            float xOff = 4*radius;
            float zOff = 6.5f*radius;

            Material wheelsMat = new Material(JmeGame.gameContext.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            wheelsMat.getAdditionalRenderState().setWireframe(false);
            wheelsMat.setColor("Color", ColorRGBA.Black);

            Material wireFrameMat = new Material(JmeGame.gameContext.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            wireFrameMat.setColor("Color", ColorRGBA.White);

            Node node1 = new Node("wheel 1 node");
//            Geometry wheels1 = new Geometry("wheel 1", wheelMesh);
            Spatial wheels1=JmeGame.gameContext.getAssetManager().loadModel("tyre1.j3o");
            ((Node)wheels1).getChild("Cylinder.001").setMaterial(wheelsMat);
            ((Node)wheels1).getChild("Cylinder.002").setMaterial(wireFrameMat);
            wheels1.setLocalScale(0.35f,0.5f,0.35f);
            node1.attachChild(wheels1);
            wheels1.rotate(0, FastMath.PI, 0);
            vehicle.addWheel(node1, new Vector3f(-xOff, yOff, zOff),
                    wheelDirection, wheelAxle, restLength, radius, true);

            Node node2 = new Node("wheel 2 node");
            Spatial wheels2=JmeGame.gameContext.getAssetManager().loadModel("tyre1.j3o");
            ((Node)wheels2).getChild("Cylinder.001").setMaterial(wheelsMat);
            ((Node)wheels2).getChild("Cylinder.002").setMaterial(wireFrameMat);
            wheels2.setLocalScale(0.35f,0.5f,0.35f);
            node2.attachChild(wheels2);
            wheels2.rotate(0, 0, 0);
            vehicle.addWheel(node2, new Vector3f(xOff, yOff, zOff),
                    wheelDirection, wheelAxle, restLength, radius, true);


            Node node3 = new Node("wheel 3 node");
            Spatial wheels3=JmeGame.gameContext.getAssetManager().loadModel("tyre1.j3o");
            ((Node)wheels3).getChild("Cylinder.001").setMaterial(wheelsMat);
            ((Node)wheels3).getChild("Cylinder.002").setMaterial(wireFrameMat);
            wheels3.setLocalScale(0.35f,0.5f,0.35f);
            node3.attachChild(wheels3);
            wheels3.rotate(0, FastMath.PI, 0);
            vehicle.addWheel(node3, new Vector3f(-xOff, yOff, -zOff),
                    wheelDirection, wheelAxle, restLength, radius, false);

            Node node4 = new Node("wheel 4 node");
            Spatial wheels4=JmeGame.gameContext.getAssetManager().loadModel("tyre1.j3o");
            ((Node)wheels4).getChild("Cylinder.001").setMaterial(wheelsMat);
            ((Node)wheels4).getChild("Cylinder.002").setMaterial(wireFrameMat);
            wheels4.setLocalScale(0.35f,0.5f,0.35f);
            node4.attachChild(wheels4);
            wheels4.rotate(0, 0, 0);
            vehicle.addWheel(node4, new Vector3f(xOff, yOff, -zOff),
                    wheelDirection, wheelAxle, restLength, radius, false);

            vehicleNode.attachChild(node1);
            vehicleNode.attachChild(node2);
            vehicleNode.attachChild(node3);
            vehicleNode.attachChild(node4);
            JmeGame.gameContext.getRootNode().attachChild(vehicleNode);
            JmeGame.gamePhysics.getPhysicsSpace().add(vehicle);

            return this;
        }
        public VehicleAutoShop startRealTimeCarSimulation(){

            //setting suspension values for wheels, this can be a bit tricky
            //see also https://docs.google.com/Doc?docid=0AXVUZ5xw6XpKZGNuZG56a3FfMzU0Z2NyZnF4Zmo&hl=en
            float stiffness =30.0f;//200=f1 car
            float compValue = 0.5f; //(should be lower than damp)
            float dampValue = 3f;
            //compression force of spring(Shock Producer)
            vehicle.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
            //stretch force of spring(Shock Absorber)
            vehicle.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
            vehicle.setSuspensionStiffness(stiffness);
            vehicle.setMaxSuspensionForce(FastMath.pow(2, 20));

            return this;
        }

    }
}
