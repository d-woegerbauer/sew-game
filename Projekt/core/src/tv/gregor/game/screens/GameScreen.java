package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import tv.gregor.game.GameMaps.GameMap;
import tv.gregor.game.Main;
import tv.gregor.game.PositionsMap01;
import tv.gregor.game.entities.Bug01;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {

    public static final int CHAR_WIDTH_PIXEL = 17;
    public static final int CHAR_HEIGHT_PIXEL = 32;


    public static final int CHAR_WIDTH = CHAR_WIDTH_PIXEL * 3;
    public static final int CHAR_HEIGHT = CHAR_HEIGHT_PIXEL * 3;

    Animation<TextureRegion>[] rolls;

    Vector2[] positions;

    GameMap gameMap;

    OrthographicCamera cam;

    private float BaseHealth = 100;

    long lastTimeCountedFPS;
    long lastTimeCountedRound;

    private float sinceChangeFPS;
    private float sinceChangeTime;
    private float frameRate;
    private boolean isDone;
    private BitmapFont font;

    float charX, charY;
    int roll;
    float stateTime;
    float rollTimer;

    OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    ArrayList<PositionsMap01> enemies;

    Main game;

    public GameScreen(Main game) {
        this.game = game;
        isDone = false;
        lastTimeCountedFPS = TimeUtils.millis();
        lastTimeCountedRound = TimeUtils.millis();
        sinceChangeFPS = 0;
        sinceChangeTime = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = new BitmapFont();
        font.getData().setScale(1.2f);

        charY = Main.HEIGHT / 2 - CHAR_HEIGHT / 2;
        charX = Main.WIDTH / 2 - CHAR_WIDTH / 2;

        positions = new Vector2[]{
                new Vector2(Gdx.graphics.getWidth(), 14 * 16f),
                new Vector2(44 * 16f, 14 * 16f),
                new Vector2(44 * 16f, Gdx.graphics.getHeight())
        };


        roll = 2;
        rollTimer = 0;
        rolls = new Animation[5];


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        map = new TmxMapLoader().load("map01.tmx");
        cam = new OrthographicCamera(w, h);
        renderer = new OrthogonalTiledMapRenderer(map);

        enemies = new ArrayList<>();
        createNewEnemies(50);


        cam.setToOrtho(false, w, h);
        cam.update();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        long time = TimeUtils.timeSinceMillis(lastTimeCountedRound);
        lastTimeCountedRound = TimeUtils.millis();


        sinceChangeTime += time;

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();
        cam.update();

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        if (enemies.isEmpty()) {
            isDone = true;
        }

        for (Iterator<PositionsMap01> iter = enemies.iterator(); iter.hasNext(); ) {
            PositionsMap01 it = iter.next();
            if (it.isPositionEnd()) {
                iter.remove();
            }
        }


        if (!isDone) {
            for (PositionsMap01 enemy : enemies) {
                showEnemy(enemy);
            }
        } else {
            if (sinceChangeTime > 1000) {
                sinceChangeTime = 0;
                isDone = false;
                createNewEnemies(50);
            }
        }

        showFPS();
        font.draw(game.batch, (int) frameRate + " fps", 3, Gdx.graphics.getHeight() - 3);
        font.draw(game.batch, BaseHealth + " hp", 100, Gdx.graphics.getHeight() - 3);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        gameMap.dispose();
    }

    public void showFPS() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCountedFPS);
        lastTimeCountedFPS = TimeUtils.millis();

        sinceChangeFPS += delta;
        if (sinceChangeFPS >= 1000) {
            sinceChangeFPS = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public void createNewEnemies(int count) {
        for (int i = 0; i < count; i++) {
            enemies.add(new PositionsMap01(new Bug01(positions[0].x + i * 40, positions[0].y, 1)));
        }
    }

    public void showEnemy(PositionsMap01 enemy) {
        if (enemy.getEnemyType() instanceof Bug01) {

            Bug01 bug01 = (Bug01) enemy.getEnemyType();

            if (!enemy.isPositionEnd()) {

                if (enemy.isPosition1()) {
                    bug01.changePos(0, bug01.getSpeed() * Gdx.graphics.getDeltaTime());
                    if (bug01.getPos().y >= positions[2].y) {
                        System.out.println("test");
                        bug01.setPos(new Vector2(positions[2].x, positions[2].y));
                        enemy.setPositionEnd(true);
                        this.BaseHealth -= bug01.getDamage();
                        if (this.BaseHealth < 0)
                            this.BaseHealth = 0;
                    }

                } else {
                    bug01.changePos(-bug01.getSpeed() * Gdx.graphics.getDeltaTime(), 0);
                    if (bug01.getPos().x <= positions[1].x) {
                        bug01.setPos(new Vector2(positions[1].x, positions[1].y));
                        enemy.setPosition1(true);
                    }
                }

                bug01.render(game.batch);
            }

        }
    }

}
