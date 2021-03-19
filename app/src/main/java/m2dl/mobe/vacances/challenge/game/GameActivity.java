package m2dl.mobe.vacances.challenge.game;

import android.os.Bundle;

import java.util.List;

import m2dl.mobe.vacances.challenge.game.background.Background;
import m2dl.mobe.vacances.challenge.game.mobengine.activities.MobeGameActivity;
import m2dl.mobe.vacances.challenge.game.mobengine.core.GameEngine;
import m2dl.mobe.vacances.challenge.game.player.LightEventListener;
import m2dl.mobe.vacances.challenge.game.mobengine.sensors.SensorManagerService;
import m2dl.mobe.vacances.challenge.game.player.AccelerometerEventListener;
import m2dl.mobe.vacances.challenge.game.player.Player;
import m2dl.mobe.vacances.challenge.onTouch.OnTouchListener;
import m2dl.mobe.vacances.challenge.exit.Exit;
import m2dl.mobe.vacances.challenge.utils.XMLParser;

public class GameActivity extends MobeGameActivity {

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getGameView().setOnTouchListener(new OnTouchListener(this));

        GameEngine.reset();

        player = new Player();

        readLevel(player);

        GameEngine.addGameElements(
                player,
                new Background(),
                new Exit()
        );

        SensorManagerService.requestSensorManager(this);
        SensorManagerService.addSensorListeners(
                new AccelerometerEventListener(player),
                new LightEventListener(player)
        );

        GameEngine.pause();

    }


    private void readLevel(Player player) {
        XMLParser xmlParser = new XMLParser(player);
        List<Object> list= xmlParser.readLevel(this);
        for(int i = 0 ; i < list.size() ; i++){
            GameEngine.addGameElements(list.get(i));
        }

    }


    public Player getPlayer() {
        return player;
    }
}