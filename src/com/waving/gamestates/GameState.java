package com.waving.gamestates;

import java.awt.*;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    public abstract void tick(double deltaTime);

    public abstract void render(Graphics2D g);

    public abstract void init();
}
