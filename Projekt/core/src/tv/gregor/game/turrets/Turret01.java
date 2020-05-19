package tv.gregor.game.turrets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import tv.gregor.game.entities.Bug01;

public class Turret01 implements TurretType{

    Vector2 pos;
    float damage = 10;
    float width = 50;
    float height = 50;
    Texture image;
    float rotation;
    boolean hasEnemy;
    Bug01 enemy;

    public Turret01(float x , float y, float rotation) {
        hasEnemy = false;
        this.image = new Texture("turret01.png");
        this.rotation = rotation;
        pos = new Vector2(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(new TextureRegion(image), pos.x, pos.y,this.width/2,this.height/2, this.width, this.height,1,1,rotation);
    }


    public Vector2 getPos() {
        return pos;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public float setRotation() {
        return rotation;
    }

    @Override
    public boolean isInside(float x, float y, float width, float height) {
        return false;
    }
}
