package com.scrappers.carsoccer.JmeGame.InGameEffects;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class NitroState extends BaseAppState {
    private final AssetManager assetManager;
    private final Node nitroNode;
    private final String effectName;
    private final Vector3f gravity;
    private final float limitationTime=3f;
    private float counter=0.0f;
    private final ColorRGBA colorRGBA1;
    private final ColorRGBA colorRGBA2;
    public NitroState(AssetManager assetManager, Node nitroNode,Vector3f gravity, String effectName,ColorRGBA colorRGBA1,ColorRGBA colorRGBA2){
        this.assetManager=assetManager;
        this.nitroNode=nitroNode;
        this.gravity=gravity;
        this.effectName=effectName;
        this.colorRGBA1=colorRGBA1;
        this.colorRGBA2=colorRGBA2;
    }
    private ParticleEmitter advancedEffects(){
        ParticleEmitter nitroEffect = new ParticleEmitter(effectName, ParticleMesh.Type.Triangle, 20000);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Fire.png"));
        nitroEffect.setMaterial(fireMat);
        nitroEffect.getParticleInfluencer().setInitialVelocity(gravity);
        nitroEffect.getParticleInfluencer().setVelocityVariation(0.3f);
        nitroEffect.setImagesX(1);
        nitroEffect.setImagesY(1);
        nitroEffect.setStartColor(colorRGBA1);
        nitroEffect.setEndColor(colorRGBA2);
        nitroEffect.setStartSize(0.2f);
        nitroEffect.setEndSize(0.1f);
        nitroEffect.setGravity(gravity);
        nitroEffect.setLowLife(0.5f);
        nitroEffect.setHighLife(1f);
        nitroEffect.center();
        nitroEffect.emitAllParticles();
        nitroEffect.addLight(new DirectionalLight(nitroEffect.getLocalTranslation().normalize(),ColorRGBA.Cyan));
        return nitroEffect;
    }


    @Override
    protected void initialize(Application app) {

        nitroNode.attachChild(advancedEffects());
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
            nitroNode.detachChild(nitroNode.getChild(effectName));
            getStateManager().detach(this);
            counter=0.0f;
        }
    }
}
