package tv.gregor.game.pathhelper;

public class PathArea {
    float x,y,width,height;

    public PathArea(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(float x, float y, float width, float height){
        if (x-width/2 < this.x+this.width &&
                x+width/2 > this.x &&
                y-height/2 < this.y+this.height &&
                y+height/2 > this.y) {
            return true;
        }
        return false;
    }
}
