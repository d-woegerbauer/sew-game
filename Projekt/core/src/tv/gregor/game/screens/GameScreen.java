package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class GameScreen implements Screen {

    public static final float SPEED = 500;

    public static final float ANIMATION_SPEED = 0.5f;

    public static final int CHAR_WIDTH_PIXEL = 17;
    public static final int CHAR_HEIGHT_PIXEL = 32;

    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;

    public static final int CHAR_WIDTH = CHAR_WIDTH_PIXEL * 3;
    public static final int CHAR_HEIGHT = CHAR_HEIGHT_PIXEL * 3;

    Animation<TextureRegion>[] rolls;

    Vector2[] positions;

    GameMap gameMap;

    OrthographicCamera cam;

    private float BaseHealth = 100;

    long lastTimeCountedFPS;
    long lastTimeCountedMob;

    private float sinceChangeFPS;
    private float sinceChangeMob;
    private float frameRate;
    private BitmapFont font;

    float charX, charY;
    int roll;
    float stateTime;
    float rollTimer;

    OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    ArrayList<PositionsMap01> enemies;

    float changeTime = 0;

    Main game;

    private boolean hasLoaded = false;

    public GameScreen(Main game) {
        this.game = game;
        lastTimeCountedFPS = TimeUtils.millis();
        lastTimeCountedMob = TimeUtils.millis();
        sinceChangeFPS = 0;
        sinceChangeMob = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = new BitmapFont();


        charY = Main.HEIGHT / 2 - CHAR_HEIGHT / 2;
        charX = Main.WIDTH / 2 - CHAR_WIDTH / 2;

        positions = new Vector2[]{
                new Vector2(Gdx.graphics.getWidth(), 14*16f),
                new Vector2(44*16f, 14*16f),
                new Vector2(44*16f, Gdx.graphics.getHeight())
        };



        roll = 2;
        rollTimer = 0;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), CHAR_WIDTH_PIXEL, CHAR_HEIGHT_PIXEL);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        map = new TmxMapLoader().load("map01.tmx");
        cam = new OrthographicCamera(w, h);
        renderer = new OrthogonalTiledMapRenderer(map);

        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            enemies.add(new PositionsMap01(new Bug01(positions[0].x+i*100, positions[0].y, 1)));
        }


        cam.setToOrtho(false, w, h);
        cam.update();

        rolls[0] = new Animation(ANIMATION_SPEED, rollSpriteSheet[2]);
        rolls[1] = new Animation(ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation(ANIMATION_SPEED, rollSpriteSheet[0]);
        rolls[3] = new Animation(ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation(ANIMATION_SPEED, rollSpriteSheet[4]);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            charX -= SPEED * Gdx.graphics.getDeltaTime();
            if (charX < 0)
                charX = 0;

            //Update roll if button just clicked
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll > 0) {
                rollTimer = 0;
                roll--;
            }
            //add roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer = 0;
                roll--;
            }
        } else {
            if (roll < 2) {
                //update roll to make it go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME) {
                    rollTimer = 0;
                    roll++;

                    if (roll > 4) roll = 4;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            charX += SPEED * Gdx.graphics.getDeltaTime();

            if (charX + CHAR_WIDTH > Gdx.graphics.getWidth())
                charX = Gdx.graphics.getWidth() - CHAR_WIDTH;

            //add roll
            rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                rollTimer = 0;
                roll++;
            }
        } else {
            if (roll > 2) {
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0;
                    roll--;

                    if (roll < 0) roll = 0;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            charY -= SPEED * Gdx.graphics.getDeltaTime();

            if (charY < 0)
                charY = 0;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            charY += SPEED * Gdx.graphics.getDeltaTime();
            if (charY + CHAR_HEIGHT > Gdx.graphics.getHeight())
                charY = Gdx.graphics.getHeight() - CHAR_HEIGHT;

        }

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();
        cam.update();

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), charX, charY, CHAR_WIDTH, CHAR_HEIGHT);

        for (PositionsMap01 enemy : enemies) {
               showEnemy(enemy);
        }
        if(!hasLoaded){
            hasLoaded = true;
        }

        showFPS();
        font.draw(game.batch, (int) frameRate + " fps", 3, Gdx.graphics.getHeight() - 3);
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

    public boolean hasTimePassed(float time) {
        long delta = TimeUtils.timeSinceMillis(lastTimeCountedMob);
        lastTimeCountedMob = TimeUtils.millis();
        sinceChangeMob += delta;

        if (sinceChangeMob >= 1000+time) {
            changeTime++;
            return true;
        }
        return false;
    }

    public void showEnemy(PositionsMap01 enemy) {
        if (enemy.getEnemyType() instanceof Bug01) {

            Bug01 bug01 = (Bug01) enemy.getEnemyType();

            if (!enemy.isPositionEnd()) {

                if (enemy.isPosition1()) {
                    bug01.changePos(0, bug01.getSpeed() * Gdx.graphics.getDeltaTime());
                    if (bug01.getPos().y >= positions[2].y) {
                        bug01.setPos(new Vector2(positions[2].x, positions[2].y));
                        enemy.setPositionEnd(true);
                    }

                } else {
                    bug01.changePos(-bug01.getSpeed() * Gdx.graphics.getDeltaTime(), 0);
                    if (bug01.getPos().x <= positions[1].x) {
                        bug01.setPos(new Vector2(positions[1].x, positions[1].y));
                        enemy.setPosition1(true);
                    }
                }

                bug01.render(game.batch);
            } else {
                this.BaseHealth -= bug01.getDamage();
            }

        }
    }
}
