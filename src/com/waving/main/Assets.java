package com.waving.main;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

import java.awt.image.BufferedImage;

public class Assets {

    SpriteSheet blocks = new SpriteSheet();
    SpriteSheet iceblocks = new SpriteSheet();

    //PLAYER
    public static SpriteSheet player = new SpriteSheet();

    //MOUSE
    public static SpriteSheet mouse = new SpriteSheet();
    public static BufferedImage mouse_pressed;
    public static BufferedImage mouse_released;

    //BUTTON
    public static SpriteSheet button = new SpriteSheet();
    public static BufferedImage button_pressed;
    public static BufferedImage button_released;
    public static BufferedImage button_heldOver;

    //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
    public static BufferedImage stone_1;
    public static BufferedImage wall_1;
    public static BufferedImage roof_1;
    public static BufferedImage inter_wall_1;
    public static BufferedImage ice_wall_1;
    public static BufferedImage ice_roof_1;
    public static BufferedImage ice_floor_1;
    public static BufferedImage ice_road_horizontal;
    public static BufferedImage ice_road_vertical;
    public static BufferedImage ice_road_left_down;
    public static BufferedImage ice_road_up_right_down;

    public static BufferedImage getButton_released() {
        return button_released;
    }

    public void init() {
        mouse.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "mouse_sprites.png"));
        blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "sprites.png"));
        iceblocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "ice_world.png"));
        player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "playersprite.png"));
        button.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "gamestate_button.png"));

        //MOUSE
        mouse_pressed = mouse.getTile(7,0, 7, 7);
        mouse_released = mouse.getTile(0, 0,7, 7);

        //BUTTON
        button_released = button.getTile(0, 0, 21, 7);
        button_pressed = button.getTile(0, 14, 21, 7);
        button_heldOver = button.getTile(0, 7, 21, 7);

        //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
        stone_1 = blocks.getTile(0, 0, 16, 16);
        wall_1 = blocks.getTile(16, 0, 16, 16);
        roof_1 = blocks.getTile(32, 0, 16, 16);
        inter_wall_1 = blocks.getTile(48, 0, 16, 16);
        ice_wall_1 = iceblocks.getTile(48, 16, 16, 16);
        ice_roof_1 = iceblocks.getTile(0, 0, 16, 16);
        ice_floor_1 = iceblocks.getTile(32, 16, 16, 16);
        ice_road_horizontal = iceblocks.getTile(0, 32, 16, 16);
        ice_road_vertical = iceblocks.getTile(16, 32, 16, 16);
        ice_road_left_down = iceblocks.getTile(48, 32, 16, 16);
        ice_road_up_right_down = iceblocks.getTile(16, 48, 16 ,16);
    }

    public static BufferedImage getButton_heldOver() {
        return button_heldOver;
    }

    public static BufferedImage getButton_pressed() {
        return button_pressed;
    }

    public static BufferedImage getMouse_pressed() {
        return mouse_pressed;
    }

    public static BufferedImage getMouse_released() {
        return mouse_released;
    }

    public static BufferedImage getStone_1() {
        return stone_1;
    }

    public static BufferedImage getWall_1() {
        return wall_1;
    }

    public static BufferedImage getRoof_1() {
        return roof_1;
    }

    public static BufferedImage getInter_wall_1() {
        return inter_wall_1;
    }

    public static BufferedImage getIce_wall_1() {
        return ice_wall_1;
    }

    public static BufferedImage getIce_roof_1() {
        return ice_roof_1;
    }

    public static BufferedImage getIce_floor_1() {
        return ice_floor_1;
    }

    public static BufferedImage getIce_road_horizontal() {
        return ice_road_horizontal;
    }

    public static BufferedImage getIce_road_vertical() {
        return ice_road_vertical;
    }

    public static BufferedImage getIce_road_left_down() {
        return ice_road_left_down;
    }

    public static BufferedImage getIce_road_up_right_down() {
        return ice_road_up_right_down;
    }
}
