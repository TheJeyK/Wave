package com.waving.generator;

import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlockEntity {

    private Vector2F pos;
    private BufferedImage block_image;
    private double rotation;
    private double rotationSpeed = 0.8;
    private double blockSize = 24;

    public BlockEntity(Vector2F pos, BufferedImage block_image) {
        this.pos = pos;
        this.block_image = block_image;
        rotation = new Random().nextInt(180);
    }

    public void tick(double deltaTime) {
        rotation -= rotationSpeed;
    }

    public void render(Graphics2D g) {
        g.rotate(Math.toRadians(rotation),
                pos.getWorldLocation().xPos + blockSize/2, pos.getWorldLocation().yPos + blockSize/2);


        g.drawImage(block_image,
                (int)pos.getWorldLocation().xPos,
                (int)pos.getWorldLocation().yPos,
                (int)blockSize, (int)blockSize, null);


        g.drawRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, (int)blockSize, (int)blockSize);


        g.rotate(-Math.toRadians(rotation),
                pos.getWorldLocation().xPos + blockSize/2, pos.getWorldLocation().yPos + blockSize/2);
    }
}
