package m2dl.mobe.vacances.challenge.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

import m2dl.mobe.vacances.challenge.game.background.Background;
import m2dl.mobe.vacances.challenge.game.mobengine.activities.MobeGameActivity;
import m2dl.mobe.vacances.challenge.game.mobengine.core.GameEngine;
import m2dl.mobe.vacances.challenge.game.mobengine.sensors.LightEventListener;
import m2dl.mobe.vacances.challenge.game.mobengine.sensors.SensorManagerService;
import m2dl.mobe.vacances.challenge.game.player.AccelerometerEventListener;
import m2dl.mobe.vacances.challenge.game.player.Player;
import m2dl.mobe.vacances.challenge.game_over.GameOverActivity;
import m2dl.mobe.vacances.challenge.utils.XMLParser;

public class GameActivity extends MobeGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Background background = new Background();


        GameEngine.reset();
        GameEngine.addGameElements(
                background
        );
        readLevel();
        GameEngine.start();

        Player p = new Player(0, 0);
        Background b = new Background();

        GameEngine.addGameElements(p, b);

        SensorManagerService.requestSensorManager(this);
        SensorManagerService.addSensorListeners(
                new AccelerometerEventListener(p),
                new LightEventListener(p)
        );

    }


    private void readLevel() {
        XMLParser xmlParser = new XMLParser();
        System.out.println(xmlParser.read(this));

    }


    public void loose(View v) {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("score", new Random().nextInt(10000));
        startActivity(intent);
        finish();
    }
}