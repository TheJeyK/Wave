package com.waving.movableObjects;

import com.waving.gameloop.GameLoop;
import com.waving.generator.World;
import com.waving.main.Animator;
import com.waving.main.Assets;
import com.waving.main.Check;
import com.waving.main.Main;
import com.waving.managers.GUImanager;
import com.waving.managers.HUDmanager;
import com.waving.managers.MouseManager;
import my.project.gop.main.Vector2F;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Clase del personaje manejado por el jugador
 */
public class Player implements KeyListener {

    Vector2F pos;
    private World world;
    private int width = 50;
    private int height = 75;
    private static boolean up, down, left, right;
    private static float runSpeed = 20F;
    private static float walkSpeed = 3F;
    private static float maxSpeed = 3F;
    //private static long animationRunningSpeed = 100L;
    //private static long animationWalkingSpeed = 300L;
    private static long animationSpeed = 300L;
    private float speedUp = 0;
    private float speedDown = 0;
    private float speedLeft = 0;
    private float speedRight = 0;
    private float slowDown = 0.08F;
    private float fixDt = 52F/60F;

    private boolean shiftPressed = false;

    MouseManager playerMouseManager = new MouseManager();

    private int renderDistanceWidth = Main.width/34;
    private int renderDistanceHeight = Main.height/38;
    public static Rectangle render;

    private int animationState = 0;

    private HUDmanager hudManager;
    private GUImanager guiManager;

    /*
     * 0 = Up
     * 1 = Down
     * 2 = Right
     * 3 = Left
     * 4 = Idle
     */

    private ArrayList<BufferedImage> listUp;
    Animator ani_Up;
    private ArrayList<BufferedImage> listDown;
    Animator ani_Down;
    private ArrayList<BufferedImage> listRight;
    Animator ani_Right;
    private ArrayList<BufferedImage> listLeft;
    Animator ani_Left;
    private ArrayList<BufferedImage> listIdle;
    Animator ani_Idle;


    private boolean running;


    /**
     * Inicializacion del personaje jugador en el centro de la pantalla
     */
    public Player() {
        pos = new Vector2F(Main.width/2 - width/2, Main.height/2 - height/2);
        hudManager = new HUDmanager(this);
        guiManager = new GUImanager();

    }

    public Vector2F getPos() {
        return pos;
    }

    public void init(World world) {

        this.world = world;

        render = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *32,
                renderDistanceHeight*32);

        listUp = new ArrayList<BufferedImage>();
        listDown = new ArrayList<BufferedImage>();
        listRight = new ArrayList<BufferedImage>();
        listLeft = new ArrayList<BufferedImage>();
        listIdle = new ArrayList<BufferedImage>();

        listUp.add(Assets.player.getTile(0, 0, 16, 24));
        listUp.add(Assets.player.getTile(16, 0, 16, 24));
        listUp.add(Assets.player.getTile(0, 0, 16, 24));
        listUp.add(Assets.player.getTile(32, 0, 16, 24));

        listDown.add(Assets.player.getTile(16, 24, 16, 24));
        listDown.add(Assets.player.getTile(0, 24, 16, 24));
        listDown.add(Assets.player.getTile(32, 24, 16, 24));
        listDown.add(Assets.player.getTile(0, 24, 16, 24));

        listRight.add(Assets.player.getTile(0, 72, 16, 24));
        listRight.add(Assets.player.getTile(16, 72, 16, 24));
        listRight.add(Assets.player.getTile(0, 72, 16, 24));
        listRight.add(Assets.player.getTile(32, 72, 16, 24));

        listLeft.add(Assets.player.getTile(0, 48, 16, 24));
        listLeft.add(Assets.player.getTile(16, 48, 16, 24));
        listLeft.add(Assets.player.getTile(0, 48, 16, 24));
        listLeft.add(Assets.player.getTile(32, 48, 16, 24));

        listIdle.add(Assets.player.getTile(0, 96, 16, 24));
        listIdle.add(Assets.player.getTile(16, 96, 16, 24));


        //UP
        ani_Up = new Animator(listUp);
        ani_Up.setSpeed(animationSpeed);
        ani_Up.play();

        //DOWN
        ani_Down = new Animator(listDown);
        ani_Down.setSpeed(animationSpeed);
        ani_Down.play();

        //RIGHT
        ani_Right = new Animator(listRight);
        ani_Right.setSpeed(animationSpeed);
        ani_Right.play();

        //LEFT
        ani_Left = new Animator(listLeft);
        ani_Left.setSpeed(animationSpeed);
        ani_Left.play();

        //IDLE
        ani_Idle = new Animator(listIdle);
        ani_Idle.setSpeed(800);
        ani_Idle.play();
    }

    /**
     Movimiento del personaje con deteccion de colision* @param deltaTime
     */
    public void tick(double deltaTime) {

        playerMouseManager.tick();

        render = new Rectangle((int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth *32/2 + width/2),
                (int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*32/2 + height/2),
                renderDistanceWidth *32,
                renderDistanceHeight*32);

        render.setBounds(render);
        float moveAmountUp = speedUp * fixDt;
        float moveAmountDown = speedDown * fixDt;
        float moveAmountRight = speedRight * fixDt;
        float moveAmountLeft = speedLeft * fixDt;


        if (up) {

            moveMapUp(moveAmountUp);
            animationState = 0;

        } else {

            moveMapUpGlide(moveAmountUp);

        }
        if (down) {

            moveMapDown(moveAmountDown);
            animationState = 1;

        } else {

            moveMapDownGlide(moveAmountDown);

        }
        if (right) {

            moveMapRight(moveAmountRight);
            animationState = 2;

        } else {

            moveMapRightGlide(moveAmountRight);
        }
        if (left) {

            moveMapLeft(moveAmountLeft);
            animationState = 3;

        } else {
            moveMapLeftGlide(moveAmountLeft);
        }

        if (!up && !down && !left && !right) {

            animationState = 4; //standing still
        }
    }


    public void moveMapUp(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)))) {

            if (speedUp < maxSpeed) {
                speedUp += slowDown;
            } else {
                speedUp = maxSpeed;
            }

            GameLoop.map.yPos -= speed;
        } else {
            speedUp = 0;
        }
    }

    public void moveMapUpRun(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)))) {

            if (speedUp < runSpeed) {
                speedUp += slowDown;
            } else {
                speedUp = runSpeed;
            }

            GameLoop.map.yPos -= speed;
        } else {
            speedUp = 0;
        }
    }

    public void moveMapUpGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos - speed)))) {

            if (speedUp != 0) {
                speedUp -= slowDown;
                if (speedUp < 0) {
                    speedUp = 0;
                }
            }
            GameLoop.map.yPos -= speed;
        } else {
            speedUp = 0;
        }
    }

    public void moveMapDown(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)))) {

            if (speedDown < maxSpeed) {
                speedDown += slowDown;
            } else {
                speedDown = maxSpeed;
            }

            GameLoop.map.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapDownRun(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)))) {

            if (speedDown < runSpeed) {
                speedDown += slowDown;
            } else {
                speedDown = runSpeed;
            }

            GameLoop.map.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapDownGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                        (int)(pos.yPos + GameLoop.map.yPos + height + speed)))) {

            if (speedDown != 0) {
                speedDown -= slowDown;
                if (speedDown < 0) {
                    speedDown = 0;
                }
            }
            GameLoop.map.yPos += speed;
        } else {
            speedDown = 0;
        }
    }

    public void moveMapRight(float speed) {


        if (!Check.CollisionPlayerBlock(
                new Point((int) (pos.xPos + GameLoop.map.xPos + width + speed),
                        (int) (pos.yPos + GameLoop.map.yPos)),
                new Point((int) (pos.xPos + GameLoop.map.xPos + width + speed),
                        (int) (pos.yPos + GameLoop.map.yPos + height)))) {

            if (speedRight < maxSpeed) {
                speedRight += slowDown;
            } else {
                speedRight = maxSpeed;
            }

            GameLoop.map.xPos += speed;
        } else {
            speedRight = 0;
        }
    }

    public void moveMapRightRun(float speed) {


        if (!Check.CollisionPlayerBlock(
                new Point((int) (pos.xPos + GameLoop.map.xPos + width + speed),
                        (int) (pos.yPos + GameLoop.map.yPos)),
                new Point((int) (pos.xPos + GameLoop.map.xPos + width + speed),
                        (int) (pos.yPos + GameLoop.map.yPos + height)))) {

            if (speedRight < runSpeed) {
                speedRight += slowDown;
            } else {
                speedRight = runSpeed;
            }

            GameLoop.map.xPos += speed;
        } else {
            speedRight = 0;
        }
    }

    public void moveMapRightGlide(float speed) {

        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos + width + speed),
                        (int)(pos.yPos + GameLoop.map.yPos)),
                new Point((int)(pos.xPos + GameLoop.map.xPos + width + speed),
                        (int)(pos.yPos + GameLoop.map.yPos + height)))) {

            if (speedRight != 0) {
                speedRight -= slowDown;
                if (speedRight < 0) {
                    speedRight = 0;
                }
            }
            GameLoop.map.xPos += speed;
        } else {
            speedRight = 0;
        }

    }

    public void moveMapLeft(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos + height)),
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos)))) {

            if (speedLeft < maxSpeed) {
                speedLeft += slowDown;
            } else {
                speedLeft = maxSpeed;
            }

            GameLoop.map.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }

    public void moveMapLeftRun(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos + height)),
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos)))) {

            if (speedLeft < runSpeed) {
                speedLeft += slowDown;
            } else {
                speedLeft = runSpeed;
            }

            GameLoop.map.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }

    public void moveMapLeftGlide(float speed) {
        if (!Check.CollisionPlayerBlock(
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos + height)),
                new Point((int)(pos.xPos + GameLoop.map.xPos - speed),
                        (int)(pos.yPos + GameLoop.map.yPos)))) {

            if (speedLeft != 0) {
                speedLeft -= slowDown;
                if (speedLeft < 0) {
                    speedLeft = 0;
                }
            }

            GameLoop.map.xPos -= speed;
        } else {
            speedLeft = 0;
        }
    }

    public void render(Graphics2D g) {

        //Vista cinematografica
        /*
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.width, Main.height/6);
        g.fillRect(0,600, Main.width, Main.height/6);
        g.fillRect(0, 0, Main.width/4, Main.height);
        g.fillRect(1050, 0, Main.width/4, Main.height);
        g.setColor(Color.WHITE);
        */

        //UP
        if (animationState == 0) {
            g.drawImage(ani_Up.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (up) {
                /*if (running) {
                    ani_Up.setSpeed(100);
                } else {
                    ani_Up.setSpeed(300);
                }*/
                ani_Up.update(System.currentTimeMillis());
            }
        }

        //DOWN
        if (animationState == 1) {
            g.drawImage(ani_Down.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (down) {
                /*if (running) {
                    ani_Down.setSpeed(100);
                } else {
                    ani_Down.setSpeed(300);
                }*/
                ani_Down.update(System.currentTimeMillis());
            }
        }

        //RIGHT
        if (animationState == 2) {
            g.drawImage(ani_Right.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (right) {
                /*if (running) {
                    ani_Right.setSpeed(100);
                } else {
                    ani_Right.setSpeed(300);
                }*/
                ani_Right.update(System.currentTimeMillis());
            }
        }

        //LEFT
        if (animationState == 3) {
            g.drawImage(ani_Left.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (left) {
                /*if (running) {
                    ani_Left.setSpeed(100);
                } else {
                    ani_Left.setSpeed(300);
                }*/
                ani_Left.update(System.currentTimeMillis());
            }
        }

        //IDLE
        if (animationState == 4) {
            g.drawImage(ani_Idle.sprite, (int)pos.xPos, (int)pos.yPos, width, height, null);
            if (!left && !right && !down && !up) {
                ani_Idle.update(System.currentTimeMillis());
            }
        }

        /*g.drawRect((int)pos.xPos - renderDistanceWidth *32/2 + width/2, (int)pos.yPos-renderDistanceHeight*32/2 + height/2,
                renderDistanceWidth *32, renderDistanceHeight*32);*/
        guiManager.render(g);
        hudManager.render(g);
        playerMouseManager.render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = true;
        }
        if (key == KeyEvent.VK_S) {
            down = true;
        }
        if (key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_D) {
            right = true;
        }
        if (key == KeyEvent.VK_K) {
            //running = true;
            //animationSpeed = animationRunningSpeed;
            maxSpeed = runSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = false;
        }
        if (key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_D) {
            right = false;
        }
        if (key == KeyEvent.VK_K) {
            //running = false;
            //animationSpeed = animationWalkingSpeed;
            maxSpeed = walkSpeed;
        }
    }

    /*                    public void playerMoveCode() {
        if (up) {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos),
                            (int)(pos.yPos + GameLoop.map.yPos - moveAmountUp)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                            (int)(pos.yPos + GameLoop.map.yPos - moveAmountUp)))) {

                if (speedUp < maxSpeed) {
                    speedUp += slowDown;
                } else {
                    speedUp = maxSpeed;
                }

                pos.yPos -= moveAmountUp;
            } else {
                speedUp = 0;
            }

        }else {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos),
                            (int)(pos.yPos + GameLoop.map.yPos - moveAmountUp)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                            (int)(pos.yPos + GameLoop.map.yPos - moveAmountUp)))) {

                if (speedUp != 0) {
                    speedUp -= slowDown;

                    if (speedUp < 0) {
                        speedUp = 0;
                    }
                }
                pos.yPos -= moveAmountUp;
            }else {
                speedUp =0;
            }

        }
        if (down) {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos),
                            (int)(pos.yPos + GameLoop.map.yPos + height + moveAmountDown)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                            (int)(pos.yPos + GameLoop.map.yPos + height + moveAmountDown)))) {

                if (speedDown < maxSpeed) {
                    speedDown += slowDown;
                } else {
                    speedDown = maxSpeed;
                }

                pos.yPos += moveAmountDown;
            } else {
                speedDown = 0;
            }

        }else {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos),
                            (int)(pos.yPos + GameLoop.map.yPos + height + moveAmountDown)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width),
                            (int)(pos.yPos + GameLoop.map.yPos + height + moveAmountDown)))) {

                if (speedDown != 0) {
                    speedDown -= slowDown;
                    if (speedDown < 0) {
                        speedDown = 0;
                    }
                }
                pos.yPos += moveAmountDown;
            } else {
                speedDown = 0;
            }

        }
        if (right) {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width + moveAmountRight),
                            (int)(pos.yPos + GameLoop.map.yPos)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width + moveAmountRight),
                            (int)(pos.yPos + GameLoop.map.yPos + height)))) {

                if (speedRight < maxSpeed) {
                    speedRight += slowDown;
                } else {
                    speedRight = maxSpeed;
                }

                pos.xPos += moveAmountRight;
            } else {
                speedRight = 0;
            }

        }else {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width + moveAmountRight),
                            (int)(pos.yPos + GameLoop.map.yPos)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos + width + moveAmountRight),
                            (int)(pos.yPos + GameLoop.map.yPos + height)))) {

                if (speedRight != 0) {
                    speedRight -= slowDown;
                    if (speedRight < 0) {
                        speedRight = 0;
                    }
                }
                pos.xPos += moveAmountRight;
            } else {
                speedRight = 0;
            }

        }

        if (left) {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos - moveAmountLeft),
                            (int)(pos.yPos + GameLoop.map.yPos + height)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos - moveAmountLeft),
                            (int)(pos.yPos + GameLoop.map.yPos)))) {

                if (speedLeft < maxSpeed) {
                    speedLeft += slowDown;
                } else {
                    speedLeft = maxSpeed;
                }

                pos.xPos -= moveAmountLeft;
            } else {
                speedLeft = 0;
            }

        }else {

            if (!Check.CollisionPlayerBlock(
                    new Point((int)(pos.xPos + GameLoop.map.xPos - moveAmountLeft),
                            (int)(pos.yPos + GameLoop.map.yPos + height)),
                    new Point((int)(pos.xPos + GameLoop.map.xPos - moveAmountLeft),
                            (int)(pos.yPos + GameLoop.map.yPos)))) {

                if (speedLeft != 0) {
                    speedLeft -= slowDown;
                    if (speedLeft < 0) {
                        speedLeft = 0;
                    }
                }

                pos.xPos -= moveAmountLeft;
            } else {
                speedLeft = 0;
            }

        }

    }*/
}
