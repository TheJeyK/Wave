package com.waving.gamestates;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {

    public static Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<GameState>();
        states.push(new WavingLevelLoader(this));
    }

    public void tick(double deltaTime) {
        states.peek().tick(deltaTime);
    }

    public void render(Graphics2D g) {
        states.peek().render(g);
    }

    public void init() {
        states.peek().init();
    }
}
