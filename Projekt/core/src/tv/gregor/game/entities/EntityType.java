package tv.gregor.game.entities;

public enum EntityType {

    PLAYER("player",14,32,40);

    private String id;
    private int width, height;
    private float health;

    EntityType(String id, int width, int height, float health) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.health = health;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getHealth() {
        return health;
    }
}
