package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tv.gregor.game.Main;

public class GameScreen implements Screen {

    public static final float SPEED = 300;

    public static final float ANIMATION_SPEED = 0.5f;

    public static final int CHAR_WIDTH_PIXEL = 17;
    public static final int CHAR_HEIGHT_PIXEL = 32;


    public static final int CHAR_WIDTH = CHAR_WIDTH_PIXEL * 3;
    public static final int CHAR_HEIGHT = CHAR_HEIGHT_PIXEL * 3;

    Animation<TextureRegion>[] rolls;

    float charX, charY;
    int roll;
    float stateTime;


    Main game;

    public GameScreen(Main game) {
        this.game = game;
        charY = Main.HEIGHT / 2 - CHAR_HEIGHT / 2;
        charX = Main.WIDTH / 2 - CHAR_WIDTH / 2;

        roll = 2;

        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), CHAR_WIDTH_PIXEL, CHAR_HEIGHT_PIXEL);

        rolls[roll] = new Animation(ANIMATION_SPEED, rollSpriteSheet[roll]);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            charX -= SPEED * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            charX += SPEED * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            charY -= SPEED * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            charY += SPEED * Gdx.graphics.getDeltaTime();
        }

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(rolls[roll].getKeyFrame(stateTime,true), charX, charY, CHAR_WIDTH, CHAR_HEIGHT);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
