package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import tv.gregor.game.Main;

public class MainMenuScreen implements Screen {

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 330;
    private static final int PLAY_BUTTON_HEIGHT = 150;

    public static float SPAWN_TIME;
    Main game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;


    public MainMenuScreen(Main game) {
        this.game = game;
        SPAWN_TIME = 1.2f;

        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        //Exit Button
        int x = Gdx.graphics.getWidth() / 2 - EXIT_BUTTON_WIDTH / 2;
        int y = Gdx.graphics.getHeight() / 3 - EXIT_BUTTON_HEIGHT;
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && Main.HEIGHT - Gdx.input.getY() < y + EXIT_BUTTON_HEIGHT && Main.HEIGHT - Gdx.input.getY() > y) {
            game.batch.draw(exitButtonActive, x, y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, x, y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        // Play Button
        int x2 = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
        int y2 = Gdx.graphics.getHeight() * 2 / 3 - PLAY_BUTTON_HEIGHT;

        if (Gdx.input.getX() < x2 + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x2 && Main.HEIGHT - Gdx.input.getY() < y2 + PLAY_BUTTON_HEIGHT && Main.HEIGHT - Gdx.input.getY() > y2) {
            game.batch.draw(playButtonActive, x2, y2, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, x2, y2, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

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
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
    }

    @Override
    public void dispose() {
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
    }
}
