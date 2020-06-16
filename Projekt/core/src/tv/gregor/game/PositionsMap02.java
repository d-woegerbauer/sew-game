package tv.gregor.game;

import tv.gregor.game.entities.Bug01;
import tv.gregor.game.entities.Bug02;
import tv.gregor.game.entities.EnemyType;

public class PositionsMap02 {
    boolean position1 = false,position2 = false,  positionEnd = false;

    EnemyType enemy;

    public PositionsMap02(EnemyType enemy) {
        this.enemy = enemy;
    }

    public boolean isPosition1() {
        return position1;
    }

    public void setPosition1(boolean position1) {
        this.position1 = position1;
    }

    public boolean isPosition2() {
        return position2;
    }

    public void setPosition2(boolean position2) {
        this.position2 = position2;
    }

    public boolean isPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(boolean positionEnd) {
        this.positionEnd = positionEnd;
    }

    public EnemyType getEnemyType() {
        return enemy;
    }
}
