package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bug10 implements EnemyType{

    Vector2 pos;
    float health = 1500;
    float damage = 250;
    float width = 100;
    float height = 100;
    Texture image;

    float speed = 100;


    public Bug10(float x, float y, float healthFactor) {
        this.image = new Texture("bug10.png");
        this.health *= healthFactor;

        pos = new Vector2(x, y);
    }


    @Override
    public Vector2 getPos() {
        return pos;
    }

    @Override
    public void changePos(float x, float y) {
        this.pos.add(x,y);
    }

    @Override
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
        return this.health <= 0;
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
