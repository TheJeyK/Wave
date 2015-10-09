package com.waving.generator;

import com.waving.main.Main;
import com.waving.movableObjects.Player;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map {

    public static BufferedImage map = null;

    public World world1;

    public Map() {

    }

    public void init() {

        try {
            map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
        } catch (Exception e) {

        }

        world1 = new World("World1", map, 100, 100);
        world1.generateWorld();
    }

    public void tick(double deltaTime) {
        world1.tick(deltaTime);
    }

    public void render(Graphics2D g) {
        world1.render(g);
    }
}
