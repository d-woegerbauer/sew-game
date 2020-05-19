package tv.gregor.game.turrets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface TurretType {


    public void render(SpriteBatch batch);

    public float getDamage();

    public float setRotation();

    public boolean isInside(float x,float y, float width, float height);
}
