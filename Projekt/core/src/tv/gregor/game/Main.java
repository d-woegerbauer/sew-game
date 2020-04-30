package tv.gregor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import tv.gregor.game.GameMaps.CustomGameMap;
import tv.gregor.game.GameMaps.GameMap;
import tv.gregor.game.GameMaps.TileType;
import tv.gregor.game.GameMaps.TiledGameMap;
import tv.gregor.game.screens.GameScreen;
import tv.gregor.game.screens.MainMenuScreen;


public class Main extends Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public SpriteBatch batch;

	OrthographicCamera cam;

    GameMap gameMap;


    @Override
    public void create() {
        batch = new SpriteBatch();

        this.setScreen(new MainMenuScreen(this));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, w, h);
        cam.update();

        gameMap = new CustomGameMap();
    }

    @Override
    public void render() {
    	super.render();


    }

    @Override
    public void dispose() {
        batch.dispose();
        gameMap.dispose();
    }
}
