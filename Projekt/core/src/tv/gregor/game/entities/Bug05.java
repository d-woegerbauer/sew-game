package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bug05 implements EnemyType{

    Vector2 pos;
    float health = 65;
    float damage = 25;
    float width = 50;
    float height = 50;
    Texture image;

    float speed = 150;


    public Bug05(float x, float y, float healthFactor) {
        this.image = new Texture("bug01.png");
        this.health *= healthFactor;

        pos = new Vector2(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public void setSpeed(float speed) {

    }

    @Override
    public boolean isDead() {
        return false;
    }
}
