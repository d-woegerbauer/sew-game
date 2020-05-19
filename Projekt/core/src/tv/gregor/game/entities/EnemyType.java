package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface EnemyType {

    public void render(SpriteBatch batch);

    public float getHealth();

    public float getDamage();

    public float getSpeed();

    public void setHealth(float health);

    public void setSpeed(float speed);

    public boolean isDead();

    public void setPos(Vector2 pos);

    public void changePos(float x, float y);

    public Vector2 getPos();
}
