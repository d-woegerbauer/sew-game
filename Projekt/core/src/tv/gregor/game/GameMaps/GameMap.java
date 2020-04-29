package tv.gregor.game.GameMaps;

import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class GameMap {

    public abstract void render(OrthographicCamera camera);

    public abstract void update(float delta);

    public abstract void dispose();

    /**
     * Get a tile by pixle position within the game wolrd at a specified layer.
     *
     * @param layer
     * @param x
     * @param y
     * @return
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordiante(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }

    /**
     * Gets a tile at its coordinate wtihin the map at a specified layer
     *
     * @param layer
     * @param col
     * @param row
     * @return
     */
    public abstract TileType getTileTypeByCoordiante(int layer, int col, int row);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getLayers();

}
