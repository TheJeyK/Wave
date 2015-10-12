package com.waving.generator;

import com.waving.movableObjects.Player;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private TileManager tiles = new TileManager();
    private Player player = new Player();

    private String worldName;
    private BufferedImage world_image;
    private int world_width;
    private int world_height;

    public World(String worldName, BufferedImage world_image, int world_width, int world_height) {
        this.worldName = worldName;
        this.world_image = world_image;
        this.world_width = world_width;
        this.world_height = world_height;

    }

    public void generateWorld() {

        for (int x=0; x < world_width; x++) {
            for (int y=0; y < world_height; y++) {

                int col = world_image.getRGB(x, y);

                //MODIFICAR AL AÑADIR UN NUEVO BLOQUE
                switch (col & 0xFFFFFF) {
                    case 0x808080:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.STONE_1));
                        break;
                    case 0x202020:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.WALL_1));
                        break;
                    case 0x606060:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ROOF_1));
                        break;
                    case 0x101010:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.INTER_WALL_1));
                        break;
                    case 0x93d6bf:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_WALL_1));
                        break;
                    case 0x26c30f:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_ROOF_1));
                        break;
                    case 0x2697f0:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_FLOOR_1));
                        break;
                    case 0xbfb4f8:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_ROAD_HORIZONTAL));
                        break;
                    case 0xd93cf0:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_ROAD_VERTICAL));
                        break;
                    case 0x403578:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_ROAD_LEFT_DOWN));
                        break;
                    case 0x6c2940:
                        tiles.blocks.add(
                                new Block(new Vector2F(x*Block.getBlockSize(),
                                        y*Block.getBlockSize()), Block.BlockType.ICE_ROAD_UP_RIGHT_DOWN));
                        break;
                }
            }
        }
        player.init(this);
    }

    public void tick(double deltaTime) {
        tiles.tick(deltaTime);

        for (BlockEntity entity : blockEnts) {
            if (player.render.intersects(entity)) {
                entity.tick(deltaTime);

                entity.setIsAlive(true);
            } else {
                entity.setIsAlive(false);
            }
        }

        player.tick(deltaTime);
    }

    public void render(Graphics2D g) {
        tiles.render(g);

        for (BlockEntity entity : blockEnts) {
            if (player.render.intersects(entity)) {
                entity.render(g);
            }
        }

        player.render(g);

        g.drawString(blockEnts.size()+"", 200, 200);
    }

    public static CopyOnWriteArrayList<BlockEntity> blockEnts = new CopyOnWriteArrayList<>();

    public static void dropBlockEntity(Vector2F pos, BufferedImage block_image) {
        BlockEntity entity = new BlockEntity(pos, block_image);

        if (!blockEnts.contains(entity)) {
            blockEnts.add(entity);
        }
    }

    public static void removeDroppedEntity(BlockEntity entity) {

        if (blockEnts.contains(entity)) {
            blockEnts.remove(entity);
        }
    }
}
