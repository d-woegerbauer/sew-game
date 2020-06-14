package tv.gregor.game;

import tv.gregor.game.entities.Bug01;
import tv.gregor.game.entities.Bug02;
import tv.gregor.game.entities.EnemyType;

public class PositionsMap01 {
    boolean position1 = false, positionEnd = false;

    Bug01 bug01;
    Bug02 bug02;

    public PositionsMap01(EnemyType enemy) {
        if (enemy instanceof Bug01)
            this.bug01 = (Bug01) enemy;
        if (enemy instanceof Bug02)
            this.bug02 = (Bug02) enemy;
    }

    public boolean isPosition1() {
        return position1;
    }

    public void setPosition1(boolean position1) {
        this.position1 = position1;
    }

    public boolean isPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(boolean positionEnd) {
        this.positionEnd = positionEnd;
    }

    public EnemyType getEnemyType() {
        if (bug01 != null) {
            System.out.println("bug01");
            return bug01;
        }
        if (bug02 != null) {
            System.out.println("bug02");
            return bug02;

        }
        System.out.println("nothing");
        return null;
    }
}
