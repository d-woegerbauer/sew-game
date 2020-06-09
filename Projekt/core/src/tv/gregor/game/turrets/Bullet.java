package tv.gregor.game.turrets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private float speed;
    private Vector2 pos;
    private Vector2 endPos;
    private Vector2 startPos;
    private float gradient;
    private Texture image;
    private Vector2 direction;


    private int times;
    public Bullet(Vector2 pos, Vector2 endPos) {
        this.image = new Texture("Projectile01.png");
        this.speed = 1000;
        this.pos = pos;
        this.startPos = pos.cpy();
        this.direction = endPos.cpy().sub(pos);
        /*if(endPos.x > startPos.x){
            directionX = 1;
        }else directionX = -1;

        if(endPos.y > startPos.y){
            directionY = 1;
        }else directionY = -1;
*/
        this.endPos = endPos.cpy();
        times = 0;
    }

    public void render(SpriteBatch batch){
        pos.add(direction.x/100*Gdx.graphics.getDeltaTime()*speed, Gdx.graphics.getDeltaTime()*direction.y/100*speed);
        times++;
        System.out.println(startPos);
        System.out.println(endPos);
        if(endPos.dst(startPos) < pos.dst(startPos)){

            pos = endPos;
        }
        batch.draw(image,pos.x,pos.y,50,50);
    }

    public boolean isOnEndPosition(){
        return this.pos == endPos;
    }

}
