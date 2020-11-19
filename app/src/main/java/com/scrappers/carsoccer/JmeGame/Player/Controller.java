package com.scrappers.carsoccer.JmeGame.Player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scrappers.carsoccer.JmeGame.InGameEffects.NitroState;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeGame;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness;
import com.scrappers.jmeGamePad.GamePadView;
import com.scrappers.jmeGamePad.GameStickView;

@SuppressLint("ViewConstructor")
public class Controller extends GameStickView {
    private VehicleControl vehicleControl;
    private final SimpleApplication jmeContext;
    private final float accelerationForce = FastMath.pow(5, 3.5f);
    private final Vector3f jumpForce = new Vector3f(0, 2000, 0);
    private final float brakeForce = 300f;
    private float accelerationValue = 0;
    private Spatial chassis;
    private final CommandWriter commandWriter;
    public Controller(Activity appCompatActivity, SimpleApplication jmeContext) {
        super( appCompatActivity);
        this.jmeContext=jmeContext;
        commandWriter=new CommandWriter();
        commandWriter.initializeFireBase();
    }

    public void setVehicleControl(VehicleControl vehicleControl) {
        this.vehicleControl = vehicleControl;
    }

    public VehicleControl getVehicleControl() {
        return vehicleControl;
    }

    @Override
    public void accelerate(final float pulse) {
        jmeContext.enqueue(() -> {
            accelerationValue+=pulse;
            accelerationValue+=accelerationForce;
            vehicleControl.accelerate(accelerationValue);
            commandWriter.writeCommand("accelerate",accelerationValue+"");
        });

    }

    @Override
    public void reverseTwitch(float pulse) {
        jmeContext.enqueue(() -> {
            vehicleControl.accelerate(-accelerationForce*2);
            vehicleControl.brake(brakeForce/2);
            commandWriter.writeCommand("reverse",""+(-accelerationForce*2));

        });

    }

    @Override
    public void steerRT(final float pulse) {
        jmeContext.enqueue(() -> {
            vehicleControl.steer(-pulse/16);
            commandWriter.writeCommand("steerRT",""+pulse/16);
        });

    }

    @Override
    public void steerLT(final float pulse) {
        jmeContext.enqueue(() -> {
            vehicleControl.steer(pulse/8);
            commandWriter.writeCommand("steerLT",""+pulse/8);

        });

    }

    @Override
    public void neutralizeState(float pulseX, float pulseY) {
        jmeContext.enqueue(() -> {
            accelerationValue=0;
            vehicleControl.accelerate(0);
            vehicleControl.clearForces();
            vehicleControl.steer(0);
            vehicleControl.brake(200f);
            commandWriter.writeCommand("neutralize",""+0);


        });

    }
    public void initializeController(){

        /*LIBRARY CODE*/
        /*run the gamePad Attachments & listeners from the android activity UI thread */
        JmeHarness.jmeHarness.runOnUiThread(() -> {

            /* create a gamePadView instance of cardView/FrameLayout to display gamePad Component */
            GamePadView gamePadView=new GamePadView( JmeHarness.jmeHarness,Controller.this);
            /* Initialize GamePad Parts*/
            gamePadView.initializeGamePad(GamePadView.DEFAULT_GAMEPAD_DOMAIN,GamePadView.ONE_THIRD_SCREEN)
                    .initializeGameStickHolder(GamePadView.FLIPPED_COLOR_STICK_DOMAIN)
                    .initializeGameStick(GamePadView.TRIS_BUTTONS,GamePadView.NOTHING_IMAGE,150);
            /*initialize the gameStick track */
            gamePadView.setMotionPathColor(Color.WHITE);
            gamePadView.setMotionPathStrokeWidth(10);
            gamePadView.setStickPathEnabled(true);
            /* initialize pad buttons & listeners A,B,X,Y */
            gamePadView.addControlButton("BUTTON A",GamePadView.GAMEPAD_BUTTON_A ,GamePadView.TRIS_BUTTONS,GamePadView.NOTHING_IMAGE, view -> {
//
                getVehicleControl().applyCentralImpulse(jumpForce);
                commandWriter.writeExtraCommand("jump",jumpForce);

            },null);
            gamePadView.addControlButton("Nitro",GamePadView.GAMEPAD_BUTTON_B , GamePadView.TRIS_BUTTONS,GamePadView.NOTHING_IMAGE, view -> {
                getVehicleControl().applyCentralImpulse(getVehicleControl().getLinearVelocity().mult(150));
                commandWriter.writeExtraCommand("nitro",getVehicleControl().getLinearVelocity().mult(150));

                Node nitroNode=((Node)((Node) chassis).getChild("nitro"));
                JmeGame.gameContext.getStateManager().attach(new NitroState(JmeGame.gameContext.getAssetManager(),nitroNode, Vector3f.UNIT_Z.negate(),"nitroEffect", ColorRGBA.Cyan,ColorRGBA.Blue));

            },null);
            gamePadView.addControlButton("BUTTON X",GamePadView.GAMEPAD_BUTTON_X , GamePadView.TRIS_BUTTONS,GamePadView.NOTHING_IMAGE, view -> getVehicleControl().brake(brakeForce),null);
            gamePadView.addControlButton("BUTTON Y",GamePadView.GAMEPAD_BUTTON_Y , GamePadView.TRIS_BUTTONS,GamePadView.NOTHING_IMAGE, view -> getVehicleControl().brake(brakeForce),null);

        });
    }

    public void setChassis(Spatial chassis) {
        this.chassis=chassis;
    }
}
