package com.waving.managers;

import com.waving.main.Assets;

import java.awt.*;
import java.awt.event.*;

public class MouseManager implements MouseWheelListener, MouseMotionListener, MouseListener {

    private static int mouseMovedX, mouseMovedY;

    public static Point mouse = new Point(mouseMovedX, mouseMovedY);

    private static boolean pressed;

    public void tick() {
        mouse = new Point(mouseMovedX, mouseMovedY);
    }

    public void render(Graphics2D g) {

        if (pressed) {
            g.drawImage(Assets.getMouse_pressed(), mouseMovedX, mouseMovedY, 32, 32, null);
        } else {
            g.drawImage(Assets.getMouse_released(), mouseMovedX, mouseMovedY,32, 32, null);
        }
    }

    public static boolean isPressed() {
        return pressed;
    }

    public static void isPressed(boolean press) {
        pressed = press;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovedX = e.getX();
        mouseMovedY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovedX = e.getX();
        mouseMovedY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
