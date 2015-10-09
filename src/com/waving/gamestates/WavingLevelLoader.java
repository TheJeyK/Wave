package com.waving.gamestates;

import com.waving.generator.Map;

import java.awt.*;

public class WavingLevelLoader extends GameState{

    Map map;

    public WavingLevelLoader(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void tick(double deltaTime) {
        map.tick(deltaTime);
    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);
    }

    @Override
    public void init() {
        map = new Map();
        map.init();
    }
}
