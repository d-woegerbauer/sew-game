package tv.gregor.game;
import com.badlogic.gdx.ApplicationAdapter;
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

import java.util.Vector;


public class Main extends ApplicationAdapter {
	OrthographicCamera cam;
	SpriteBatch batch;
	Texture img;
	GameMap gameMap;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();

		gameMap = new CustomGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(Gdx.input.isTouched()){
			cam.translate(-Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
			cam.update();
		}

		if (Gdx.input.justTouched()){
			Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			TileType type = gameMap.getTileTypeByLocation(1,pos.x,pos.y);

			if(type != null){
				System.out.println("You clicked on tile with id "+ type.getId() + " "+ type.getName() + " "+ type.isCollidable()+" " + type.getDamage());
			}
		}

		gameMap.render(cam);

		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
