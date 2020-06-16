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

public class LevelScreen implements Screen{

    private static final int MAP_WIDTH = 400;
    private static final int MAP_HEIGHT = 400;

    public static final int FONT_SIZE = 50;

    private OrthographicCamera cam;
    private ShapeRenderer sr;
    private Vector3 pos;
    Main game;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    //Maps - textures
    Texture map1;
    Texture map2;
    Texture map3;

    public LevelScreen(Main game) {
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

        //Maps - images
        map1 = new Texture("map_images/map1.jpg");
        map2 = new Texture("map_images/map2.jpg");
        map3 = new Texture("map_images/map1.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(cam.combined);

        String goBack = "<--";

        GlyphLayout text01 = new GlyphLayout();
        text01.setText(font,"Maps");
        float w2 = text01.width;

        GlyphLayout text02 = new GlyphLayout();
        text02.setText(font,goBack);
        float w1 = text02.width;

        game.batch.begin();
        if (Gdx.input.getX() < 50 + w1 && Gdx.input.getX() > w1 && Main.HEIGHT - Gdx.input.getY() < Gdx.graphics.getHeight()-50 + text01.height && Main.HEIGHT - Gdx.input.getY() > text01.height) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                game.setScreen(new MainMenuScreen(game));
            }
        }

        //Map 1 choose
        game.batch.draw(map1, 200, 500, MAP_WIDTH, MAP_HEIGHT);
        if (Gdx.input.getX() < 200 + MAP_WIDTH && Gdx.input.getX() > 200 && Main.HEIGHT - Gdx.input.getY() < 500 + MAP_HEIGHT && Main.HEIGHT - Gdx.input.getY() > 500) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                game.setScreen(new GameScreen(game));
            }
        }

        //Map 2 choose
        game.batch.draw(map2, 800, 500, MAP_WIDTH, MAP_HEIGHT);
        if (Gdx.input.getX() < 800 + MAP_WIDTH && Gdx.input.getX() > 800 && Main.HEIGHT - Gdx.input.getY() < 500 + MAP_HEIGHT && Main.HEIGHT - Gdx.input.getY() > 500) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                game.setScreen(new GameScreen2(game));
            }
        }

        //Map 3 choose
        game.batch.draw(map3, 1400, 500, MAP_WIDTH, MAP_HEIGHT);
        if (Gdx.input.getX() < 1400 + MAP_WIDTH && Gdx.input.getX() > 1400 && Main.HEIGHT - Gdx.input.getY() < 500 + MAP_HEIGHT && Main.HEIGHT - Gdx.input.getY() > 500) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                game.setScreen(new GameScreen(game));
            }
        }

        //if (Gdx.input.getX() < 50 + w1 && Gdx.input.getX() > w1 && Main.HEIGHT - Gdx.input.getY() < Gdx.graphics.getHeight()-50 + text01.height && Main.HEIGHT - Gdx.input.getY() > text01.height) {
        //    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
        //        game.setScreen(new MainMenuScreen(game));
        //    }
        //}

        font.draw(game.batch, text01,(Gdx.graphics.getWidth()-w2)/2,Gdx.graphics.getHeight()-50);
        font.draw(game.batch, goBack,50,Gdx.graphics.getHeight()-50);
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
