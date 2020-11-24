package com.scrappers.carsoccer.JmeGame.VehicleSelectorStage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;

import com.scrappers.carsoccer.JmeGame.SceneSelectorStage.SceneSelectorStage;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeGame;
import com.scrappers.carsoccer.JmeGame.JmERenderer.JmeHarness;
import com.scrappers.carsoccer.R;
import com.scrappers.carsoccer.JmeGame.GameStructure;
import com.scrappers.jmeGamePad.GamePadView;
import com.scrappers.jmeGamePad.GameStickView;

@SuppressLint("ViewConstructor")
public class Controller extends GameStickView {
    private final VehicleSelectorStage vehicleSelectorStage;
    private int spaceShipIndex=0;

    /**
     * create a gameStickView & OverRide its abstract methods(gameStickView Listeners).
     *
     * @param appCompatActivity activity instance
     * @apiNote in order to ensure a proper use , extend this class better than using anonymous class instance.
     */
    public Controller(Activity appCompatActivity, VehicleSelectorStage vehicleSelectorStage) {
        super(appCompatActivity);
        this.vehicleSelectorStage = vehicleSelectorStage;

    }

    @Override
    public void accelerate(float pulse) {

    }

    @Override
    public void reverseTwitch(float pulse) {

    }

    @Override
    public void steerRT(float pulse) {
        JmeGame.gameContext.enqueue(()->{
            int lastIndex=vehicleSelectorStage.getAvailableSpaceShips().length-1;
            if(spaceShipIndex < lastIndex){
                vehicleSelectorStage.changeVehicle(vehicleSelectorStage.getAvailableSpaceShips()[++spaceShipIndex]);
            }
        });

    }

    @Override
    public void steerLT(float pulse) {
        JmeGame.gameContext.enqueue(()->{
            int startOffset=0;
            if(spaceShipIndex > startOffset){
                vehicleSelectorStage.changeVehicle(vehicleSelectorStage.getAvailableSpaceShips()[--spaceShipIndex]);

            }
        });

    }

    @Override
    public void neutralizeState(float pulseX, float pulseY) {

    }

    public void initializeGameControllers() {
        /*LIBRARY CODE*/
        /*run the gamePad Attachments & listeners from the android activity UI thread */
        JmeHarness.jmeHarness.runOnUiThread(() -> {

            GamePadView gamePadView = new GamePadView(JmeHarness.jmeHarness, Controller.this);
            /* Initialize GamePad Parts*/
            gamePadView.initializeGamePad(GamePadView.DEFAULT_GAMEPAD_DOMAIN, GamePadView.ONE_THIRD_SCREEN)
                    .initializeGameStickHolder(R.drawable.bigger_circle)
                    .initializeGameStick(R.drawable.circle, R.color.transparent, 140);
            /*initialize the gameStick track */
            gamePadView.setMotionPathColor(Color.WHITE);
            gamePadView.setMotionPathStrokeWidth(7);
            gamePadView.setStickPathEnabled(true);
            /* initialize pad buttons & listeners A,B,X,Y */
            gamePadView.addControlButton("Go", GamePadView.GAMEPAD_BUTTON_X, R.drawable.crystal_buttons, R.drawable.ic_twotone_play_arrow_24,view -> {
                //start game or move to other game state
                JmeGame.gameContext.enqueue(() -> {
                    JmeGame.gameContext.getStateManager().detach(vehicleSelectorStage);
                    JmeGame.gameContext.getRootNode().detachAllChildren();
                    gamePadView.removeAllViewsInLayout();
                    //to scene selector stage
                    SceneSelectorStage sceneSelectorStage=new SceneSelectorStage(JmeGame.gameContext.getRootNode(),new String[]{"Scenes/SoccerPlayGround.j3o", "Scenes/VehicleSelectorStage.j3o"});
                    if(!JmeGame.gameContext.getStateManager().hasState(sceneSelectorStage)){
                        JmeGame.gameContext.getStateManager().attach(sceneSelectorStage);
                    }
                    GameStructure.setSelectedCar(vehicleSelectorStage.getAvailableSpaceShips()[spaceShipIndex]);

                });

            }, null);
        });


    }
}
