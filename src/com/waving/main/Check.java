package com.waving.main;

import com.waving.generator.Block;
import com.waving.generator.TileManager;

import java.awt.*;

/**
 * Comprobador de colisiones
 */
public class Check {

    /**Comprueba si el jugador colisiona con un objeto solido
     * @param p1
     * @param p2
     * @return
     */
    public static boolean CollisionPlayerBlock(Point p1, Point p2) {
        for (Block block : TileManager.blocks) {
            if (block.isSolid()) {
                if (block.contains(p1) || block.contains(p2)) {
                    return true;
                }
            }
        }

        return false;
    }
}
