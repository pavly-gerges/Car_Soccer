package com.scrappers.carsoccer.JmeGame.VehicleBuilder;

import com.jme3.app.Application;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import com.scrappers.carsoccer.JmeGame.GameStructure;

public class VehicleBuilder {
    private final Application application;
    private VehicleControl vehicle;
    private VehicleGarage.VehicleAutoShop vehicleAutoShop;
    public VehicleBuilder(Application application){
        this.application=application;
    }

    public Application getApplication() {
        return application;
    }

    public Spatial getChassis() {
        return vehicleAutoShop.getChassis();
    }

    public VehicleControl getVehicle() {
        return vehicleAutoShop.getVehicle();
    }

    public VehicleGarage.VehicleAutoShop buildPlayer() {
        getApplication().getCamera().setFrustumFar(2000f);
        getApplication().getCamera().setRotation(Quaternion.IDENTITY);

        VehicleGarage vehicleGarage =new VehicleGarage(GameStructure.getSelectedCar(),getApplication().getAssetManager());
        vehicleGarage.initializeVehicle()
                .paintChassisMaterial(new ColorRGBA(0f,1f,3f,1f), "carTex.jpg")
                .paintAddOnsMaterial(null, "carTex.jpg")
                .paintBackLightsMaterial(ColorRGBA.Red,"")
                .paintFrontLightsMaterial(ColorRGBA.White,"")
                .paintGlassMaterial(ColorRGBA.BlackNoAlpha,"")
                .paintMirrorsMaterial(ColorRGBA.White,"")
                .paintU_TurnsMaterial(ColorRGBA.Yellow,"")
                .paintNitroMaterial(ColorRGBA.Blue,"carTex.jpg");
         vehicleAutoShop=new VehicleGarage.VehicleAutoShop(vehicleGarage.getChassis());
         vehicleAutoShop.initializeChassis()
                        .initializeVehiclePhysics()
                        .startRealTimeCarSimulation()
                        .loadWheels();
        return vehicleAutoShop;
    }
}
