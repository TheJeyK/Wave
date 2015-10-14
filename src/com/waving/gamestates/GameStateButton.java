package com.waving.gamestates;

import com.waving.main.Assets;
import com.waving.managers.MouseManager;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameStateButton extends Rectangle{

    private GameState gameState;
    private Vector2F pos = new Vector2F();
    private GameStateManager gsm;
    private boolean isHeldOver;
    private int width = 32*3;
    private int height = 32;
    private BufferedImage defaultImage;
    private String buttonMessage;

    public GameStateButton(float xpos, float ypos, GameState gameState, GameStateManager gameStateManager, String buttonMessage) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        this.gameState = gameState;
        this.gsm = gameStateManager;
        this.buttonMessage = buttonMessage;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
        defaultImage = Assets.getButton_released();
    }

    public GameStateButton(float xpos, float ypos) {
        this.pos.xPos = xpos;
        this.pos.yPos = ypos;
        setBounds((int)pos.xPos, (int)pos.yPos, width, height);
        defaultImage = Assets.getButton_released();
    }

    public void tick() {

        setBounds((int)pos.xPos, (int)pos.yPos, width, height);

        if (getBounds().contains(MouseManager.mouse)) {

            isHeldOver = true;

        } else {

            isHeldOver = false;

        }

        if (isHeldOver) {

            if (defaultImage != Assets.getButton_heldOver()) {
                defaultImage = Assets.getButton_heldOver();
            }

        } else {

            if (defaultImage != Assets.getButton_released()) {
                defaultImage = Assets.getButton_released();
            }
        }

        if (gameState != null) {
            if (isHeldOver) {
                if (isPressed()) {
                    gsm.states.push(gameState);
                    isHeldOver = false;
                    MouseManager.isPressed(false);
                }
            }
        }
    }

    public void render(Graphics2D g) {

        g.drawImage(defaultImage, (int)pos.xPos, (int)pos.yPos, width, height, null);

        g.setColor(Color.BLUE);
        g.drawString(buttonMessage, (int)pos.xPos+25, (int)pos.yPos+20);
    }

    public boolean isHeldOver() {
        return isHeldOver;
    }

    public boolean isPressed() {
        return MouseManager.isPressed();
    }

}
