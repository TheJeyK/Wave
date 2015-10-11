package com.waving.gamestates;

import my.project.gop.main.Vector2F;

import java.awt.*;

public class GameStateButton extends Rectangle{

    private GameState gameState;
    private Vector2F pos = new Vector2F();
    private GameStateManager gsm;
    private boolean isClicked;
    private boolean isHeldOver;
    private int width = 32*3;
    private int height = 32;

    public GameStateButton(float xpos, float ypos, GameState gameState, GameStateManager gameStateManager) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        this.gameState = gameState;
        this.gsm = gameStateManager;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
    }

    public GameStateButton(float xpos, float ypos) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
    }

    public void tick() {
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
    }

    public void render(Graphics2D g) {

        if (!isHeldOver) {
            g.setColor(Color.RED);
            g.fillRect((int)pos.xPos, (int)pos.yPos, width, height);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect((int)pos.xPos, (int)pos.yPos, width, height);
        }
    }

    public boolean isHeldOver() {
        return isHeldOver;
    }

    public boolean isClicked() {
        return isClicked;
    }
}
