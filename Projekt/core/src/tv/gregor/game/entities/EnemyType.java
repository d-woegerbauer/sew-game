package tv.gregor.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EnemyType {

    public void render(SpriteBatch batch);

    public float getHealth();

    public float getDamage();

    public float getSpeed();

    public void setHealth(float health);

    public void setSpeed(float speed);

    public boolean isDead();
}
