package com.waving.gameloop;

import com.waving.gamestates.GameStateManager;
import com.waving.main.Assets;
import my.project.gop.main.GOPGameLoop;
import my.project.gop.main.Vector2F;

public class GameLoop extends GOPGameLoop {

    GameStateManager gsm;
    public static Assets assets = new Assets();
    public static Vector2F map = new Vector2F();

    public GameLoop(int width, int height) {
        super(width, height);
    }

    @Override
    public void init() {
        assets.init();
        Vector2F.setWorldVariables(map.xPos, map.yPos);
        gsm = new GameStateManager();
        gsm.init();
        super.init();
    }

    @Override
    public void tick(double deltaTime) {
        Vector2F.setWorldVariables(map.xPos, map.yPos);
        gsm.tick(deltaTime);
    }

    @Override
    public void render() {
        super.render();
        gsm.render(graphics2D);
        clear();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
