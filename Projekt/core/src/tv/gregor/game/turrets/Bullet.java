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
    private Texture image;
    private Vector2 direction;


    private int times;
    public Bullet(Vector2 pos, Vector2 endPos) {
        this.image = new Texture("Projectile01.png");
        this.speed = 1000;
        this.pos = pos;
        this.startPos = pos.cpy();
        this.direction = endPos.cpy().sub(pos);

        this.endPos = endPos.cpy();
        times = 0;
    }

    public void render(SpriteBatch batch){
        pos.add(direction.x/100*Gdx.graphics.getDeltaTime()*speed, Gdx.graphics.getDeltaTime()*direction.y/100*speed);
        times++;
        if(endPos.dst(startPos) < pos.dst(startPos)){

            pos = endPos;
        }
        batch.draw(image,pos.x,pos.y,50,50);
    }

    public boolean isOnEndPosition(){
        return this.pos == endPos;
    }

}
