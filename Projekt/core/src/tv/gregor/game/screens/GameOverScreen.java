package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import tv.gregor.game.Main;

public class GameOverScreen  implements Screen{
    private static final int MAP_WIDTH = 1920;
    private static final int MAP_HEIGHT = 1080;

    public static final int FONT_SIZE = 50;

    private OrthographicCamera cam;
    private ShapeRenderer sr;
    private Vector3 pos;
    Main game;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    //Maps - textures
    Texture gameOver;

    public GameOverScreen(Main game) {
        this.game = game;

        sr = new ShapeRenderer();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        pos = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 2, 0);


        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Plumpfull.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = FONT_SIZE;

        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);

        //Game Over Image
        gameOver = new Texture("game_over.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        //Game Over Screen
        game.batch.draw(gameOver, 0, 0, MAP_WIDTH, MAP_HEIGHT);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(new LevelScreen(game));
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

    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }
}
