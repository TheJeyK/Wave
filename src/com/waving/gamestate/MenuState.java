package com.waving.gamestate;

import com.waving.gamestates.GameState;
import com.waving.gamestates.GameStateButton;
import com.waving.gamestates.GameStateManager;
import com.waving.main.Main;
import com.waving.managers.MouseManager;

import java.awt.*;

public class MenuState extends GameState{

    GameStateButton startGame;

    MouseManager mouseManager;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void tick(double deltaTime) {
        startGame.tick();

        mouseManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        startGame.render(g);

        mouseManager.render(g);

        g.clipRect(0, 0, Main.width, Main.height);
    }

    @Override
    public void init() {
        mouseManager = new MouseManager();

        startGame = new GameStateButton(Main.width/3, 200, new WavingLevelLoader(gsm), gsm, "Testing");
    }
}
