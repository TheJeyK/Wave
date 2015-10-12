package com.waving.generator;

import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlockEntity extends Rectangle{

    private Vector2F pos;
    private BufferedImage block_image;
    private double rotation;
    private double rotationSpeed = 0.8;
    private double blockSize = 24;
    private boolean isAlive;




    private int lifeTime;
    private boolean isDying;
    private float lifeFade = 1;



    public BlockEntity(Vector2F pos, BufferedImage block_image) {
        this.pos = pos;
        this.block_image = block_image;
        rotation = new Random().nextInt(180);
        setBounds((int)pos.xPos, (int)pos.yPos, (int)blockSize, (int)blockSize);
        isAlive = true;

        lifeTime = new Random().nextInt(300);
        if (lifeTime < 250) {
            lifeTime = new Random().nextInt(300);
        }
    }

    public void tick(double deltaTime) {

        if (isAlive) {
            setBounds((int) pos.xPos, (int) pos.yPos, (int) blockSize, (int) blockSize);
            rotation -= rotationSpeed;


            if (lifeTime != 0) {
                lifeTime--;
            }

            if (lifeTime == 0) {
                isDying = true;
            }

            if (isDying) {

                if (lifeFade != 0.01) {
                    lifeFade -= 0.01;
                }

                if (lifeFade <= 0.8 ) {
                    blockSize -= 0.2;
                    pos.xPos += 0.1;
                    pos.yPos += 0.1;
                }

                if (lifeFade <= 0.02) {
                    World.removeDroppedEntity(this);
                    isAlive = false;
                }
            }
        }
    }


    public void render(Graphics2D g) {

        if (isAlive) {

            if (isDying) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lifeFade));
            }

            g.rotate(Math.toRadians(rotation),
                    pos.getWorldLocation().xPos + blockSize/2, pos.getWorldLocation().yPos + blockSize/2);


            g.drawImage(block_image,
                    (int)pos.getWorldLocation().xPos,
                    (int)pos.getWorldLocation().yPos,
                    (int)blockSize, (int)blockSize, null);


            g.drawRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, (int)blockSize, (int)blockSize);


            g.rotate(-Math.toRadians(rotation),
                    pos.getWorldLocation().xPos + blockSize/2, pos.getWorldLocation().yPos + blockSize/2);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }

    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
