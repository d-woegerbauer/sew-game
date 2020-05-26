package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;

    public Entity(float x, float y,EntityType type) {
        this.pos = new Vector2(x,y);
        this.type = type;
    }

    public void update(float deltaTime){

    }

    public abstract void render(SpriteBatch batch);

    public Vector2 getPos() {
        return pos;
    }

    public float getX(){
        return pos.x;
    }
    public float getY(){
        return pos.y;
    }

    public EntityType getType() {
        return type;
    }

    public int getWidth(){
        return type.getWidth();
    }


    public int getHeight(){
        return type.getHeight();
    }


    public float getHealth(){
        return type.getHealth();
    }
}
