package com.scrappers.carsoccer.JmeGame.InGameEffects;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterPointShape;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class DoughNutState extends BaseAppState {
    private final AssetManager assetManager;
    private final Node node;
    private final String effectName;
    private final Vector3f gravity;
    private final float limitationTime=1.5f;
    private float counter=0.0f;
    private final ColorRGBA colorRGBA1;
    private final ColorRGBA colorRGBA2;
    private final Node wheel2;
    public DoughNutState(AssetManager assetManager, Node node, Node wheel2, Vector3f gravity, String effectName, ColorRGBA colorRGBA1, ColorRGBA colorRGBA2){
        this.assetManager=assetManager;
        this.node = node;
        this.wheel2=wheel2;
        this.gravity=gravity;
        this.effectName=effectName;
        this.colorRGBA1=colorRGBA1;
        this.colorRGBA2=colorRGBA2;
    }
    private ParticleEmitter advancedEffects(){
        ParticleEmitter nitroEffect = new ParticleEmitter(effectName, ParticleMesh.Type.Triangle, 2000);
        nitroEffect.setParticlesPerSec(1000);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Dirt_Bottom-3072.jpg"));
        nitroEffect.setMaterial(fireMat);
        nitroEffect.getParticleInfluencer().setInitialVelocity(gravity);
        nitroEffect.getParticleInfluencer().setVelocityVariation(0.8f);
        nitroEffect.setLocalTranslation(0,-3f,0);
        nitroEffect.setImagesX(1);
        nitroEffect.setImagesY(1);
        nitroEffect.setShape(new EmitterPointShape(new Vector3f(1,1,1)));
        nitroEffect.setStartColor(colorRGBA1);
        nitroEffect.setEndColor(colorRGBA2);
        nitroEffect.setStartSize(0.2f);
        nitroEffect.setEndSize(0.1f);
        nitroEffect.setGravity(gravity);
        nitroEffect.setLowLife(0.5f);
        nitroEffect.setHighLife(1f);
        nitroEffect.addLight(new DirectionalLight(nitroEffect.getLocalTranslation().normalize(),ColorRGBA.Cyan));
        return nitroEffect;
    }


    @Override
    protected void initialize(Application app) {
        node.attachChild(advancedEffects());
        wheel2.attachChild(advancedEffects());
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

    @Override
    public void update(float tpf) {
        counter+=tpf;
        if(counter>limitationTime){
            node.detachChild(node.getChild(effectName));
            wheel2.detachChild(wheel2.getChild(effectName));
            getStateManager().detach(this);
            counter=0.0f;
        }
    }
}
