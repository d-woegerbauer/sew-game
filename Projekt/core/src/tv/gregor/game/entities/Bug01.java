package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bug01 implements EnemyType {



    Vector2 pos;
    float health = 13;
    float damage = 10;
    float width = 30;
    float height = 30;
    Texture image;

    float speed = 200;


    public Bug01(float x, float y, float healthFactor) {
        this.image = new Texture("bug01.png");
        this.health *= healthFactor;

        pos = new Vector2(x, y);
    }


    public Vector2 getPos() {
        return pos;
    }

    public void changePos(float x, float y) {
        this.pos.add(x,y);
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public void setHealth(float damage) {
        this.health -= damage;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public boolean isDead() {
        if (this.health == 0) return true;
        else return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, this.width, this.height);
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public float getSpeed() {
        return speed;
    }


}
