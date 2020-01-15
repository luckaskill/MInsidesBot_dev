package com.las.desktop.minsides;

import com.badlogic.gdx.Game;
import com.las.desktop.minsides.screen.MainScreen;
import org.springframework.stereotype.Component;


@Component
public class GdxMain extends Game {

    private MainScreen mainScreen;

    public GdxMain(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void create() {
        setScreen(mainScreen);
    }
}
