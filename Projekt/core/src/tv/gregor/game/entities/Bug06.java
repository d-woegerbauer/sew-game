package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bug06 implements EnemyType{

    Vector2 pos;
    float health = 125;
    float damage = 50;
    float width = 50;
    float height = 50;
    Texture image;

    float speed = 250;


    public Bug06(float x, float y, float healthFactor) {
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
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, this.width, this.height);
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
