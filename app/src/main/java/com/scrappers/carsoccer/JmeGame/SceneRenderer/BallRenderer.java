package com.scrappers.carsoccer.JmeGame.SceneRenderer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeGame;

public class BallRenderer extends BaseAppState {
    @Override
    protected void initialize(Application app) {
        //ball sphere with mesh collision shape
        Sphere sphere = new Sphere(15, 15, 5f);
        Geometry sphereGeometry = new Geometry("Sphere", sphere);

        Material material = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", app.getAssetManager().loadTexture("soccerTex.jpg"));
        sphereGeometry.setMaterial(material);
        sphereGeometry.setLocalTranslation(0f, -5f, 0f);
        RigidBodyControl ballControl=new RigidBodyControl(new SphereCollisionShape(5f), 0.5f);
        ballControl.setFriction(2f);
        ballControl.setLinearVelocity(new Vector3f(0.2f,0.2f,0.2f));
        ballControl.setRollingFriction(1f);

        sphereGeometry.addControl(ballControl);
        JmeGame.gameContext.getRootNode().attachChild(sphereGeometry);
        JmeGame.gamePhysics.getPhysicsSpace().add(sphereGeometry);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
