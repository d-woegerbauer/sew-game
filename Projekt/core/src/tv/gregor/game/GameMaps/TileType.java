package tv.gregor.game.GameMaps;

import java.util.HashMap;

public enum TileType {
    ;

    public static final int TILE_SIZE = 16;

    private int id;

    TileType(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer,TileType>();
        for (TileType tileType: TileType.values()){
            tileMap.put(tileType.getId(),tileType);
        }
    }

    public static TileType getTileTypeById (int id){
        return tileMap.get(id);
    }
}
