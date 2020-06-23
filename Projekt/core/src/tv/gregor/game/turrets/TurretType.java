package tv.gregor.game.turrets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tv.gregor.game.entities.EnemyType;

public interface TurretType {


     void render(SpriteBatch batch);

    float getDamage();

    float setRotation();

    boolean isInside(float x, float y, float width, float height);

    Vector2 getPos();

    boolean hasEnemy();

    void setEnemy(EnemyType enemy);

    float getRange();
}
