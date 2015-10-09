package com.waving.main;

import com.waving.gameloop.GameLoop;
import com.waving.managers.MouseManager;
import com.waving.movableObjects.Player;
import my.project.gop.main.GameWindow;

import java.awt.*;

/**
 * Inicializador del juego
 */
public class Main {

    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static int width = gd.getDisplayMode().getWidth();
    public static int height = gd.getDisplayMode().getHeight();

    public static void main(String[] args) {
        GameWindow frame = new GameWindow("Game", width, height);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0, 0), "Cursor");
        frame.setCursor(cursor);

        frame.addMouseListener(new MouseManager());
        frame.addMouseMotionListener(new MouseManager());
        frame.addMouseWheelListener(new MouseManager());

        frame.addKeyListener(new Player());
        frame.add(new GameLoop(width, height));
        frame.setVisible(true);
    }
}
