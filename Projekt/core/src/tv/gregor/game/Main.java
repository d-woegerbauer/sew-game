package tv.gregor.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tv.gregor.game.screens.MainMenuScreen;

public class Main extends Game {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public SpriteBatch batch;
    public static final int TILE_SIZE = 16;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
