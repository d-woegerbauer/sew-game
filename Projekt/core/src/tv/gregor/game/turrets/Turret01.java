package tv.gregor.game.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import tv.gregor.game.entities.Bug01;
import tv.gregor.game.entities.EnemyType;

public class Turret01 implements TurretType{

    Vector2 pos;
    float damage = 10;
    float width = 50;
    float height = 50;
    Texture image;
    float rotation;
    Vector2 direction;
    public boolean rotateClockwise;
    boolean hasEnemy;
    EnemyType enemy;
    float range = 200;
    float timePassed;
    Bullet bullet;
    private boolean bulletIsMoving;

    public Turret01(float x , float y) {
        hasEnemy = false;
        this.image = new Texture("turret01.png");
        this.rotation = 90;
        pos = new Vector2(x, y);
        timePassed = 0;
        direction = new Vector2(pos.x,pos.y+10);
        rotateClockwise = false;
        bulletIsMoving = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        timePassed += Gdx.graphics.getDeltaTime()*1000;
        //if(timePassed >= 1000) {
            //rotation = 90;
            //rotateClockwise = false;
        //}
        if(hasEnemy){
            if(enemy.isDead()){
                hasEnemy = false;

            }else {
                rotateClockwise = true;
                rotation = (float) Math.toDegrees(Math.atan2(enemy.getPos().y-direction.y , enemy.getPos().x-direction.x));

                if(timePassed >= 200 && !bulletIsMoving) {
                    bulletIsMoving = true;
                    bullet = new Bullet(pos.cpy(),enemy.getPos().cpy());

                    timePassed = 0;
                }
                if(bulletIsMoving){

                    bullet.render(batch);
                    if(bullet.isOnEndPosition()){

                        enemy.setHealth(this.damage);
                        bullet = null;

                        bulletIsMoving = false;
                    }
                }
            }
        }
        batch.draw(new TextureRegion(image), pos.x, pos.y,this.width/2,this.height/2, this.width, this.height,1,1,rotation,rotateClockwise);
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
        return x - width / 2 < this.pos.x + this.width / 2 &&
                x + width / 2 > this.pos.x - this.width / 2 &&
                y - height / 2 < this.pos.y + this.height / 2 &&
                y + height / 2 > this.pos.y - this.height / 2;
    }

    public boolean hasEnemy() {
        return hasEnemy;
    }

    public void setEnemy(EnemyType enemy) {
        this.hasEnemy = true;
        this.enemy = enemy;

    }

    public float getRange() {
        return range;
    }

}
