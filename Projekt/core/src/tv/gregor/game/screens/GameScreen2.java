package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import tv.gregor.game.Main;
import tv.gregor.game.PositionsMap02;
import tv.gregor.game.entities.*;
import tv.gregor.game.pathhelper.PathArea;
import tv.gregor.game.turrets.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen2 implements Screen {

    private Vector2[] positions;

    private OrthographicCamera cam;

    private float BaseHealth = 100;

    private long lastTimeCountedFPS;
    private long lastTimeCountedRound;

    private boolean showShop;
    private int turretToPlace;
    private float sinceChangeFPS;
    private float sinceChangeTime;
    private float frameRate;
    private boolean isDone;
    private BitmapFont font;

    private boolean showTime;

    private float round;
    private float money;

    private boolean moneyAdded;
    private ArrayList<PathArea> path;

    private boolean isTurretChosen;

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private ArrayList<PositionsMap02> enemies;

    private FreeTypeFontGenerator generator;

    private ShapeRenderer shape;

    private ArrayList<TurretType> turrets;

    private Main game;

    /**
     * Constructor for the Game Screen/ Level 1
     * @param game
     */

    GameScreen2(Main game) {

        round = 0;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Plumpfull.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 28;
        parameter.color = Color.BLACK;
        moneyAdded =false;

        showShop = true;
        this.game = game;
        isDone = true;
        lastTimeCountedFPS = TimeUtils.millis();
        lastTimeCountedRound = TimeUtils.millis();
        sinceChangeFPS = 0;
        sinceChangeTime = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = generator.generateFont(parameter);

        path = new ArrayList<>();
        path.add(new PathArea(0*16f,16*16f,43*16f,6*16f));
        path.add(new PathArea(36*16f,16*16f,6*16f,33*16));
        path.add(new PathArea(36*16f,44*16f,76*16f,6*16f));

        isTurretChosen = false;

        positions = new Vector2[]{
                new Vector2(-10 * 16f, (float) (18.5 * 16f)),
                new Vector2((float) (38.5 * 16f), (float) (18.5 * 16f)),
                new Vector2((float) (38.5 * 16f), (float) (46.5 * 16f)),
                new Vector2(Gdx.graphics.getWidth(), (float) (46.5 * 16f))
        };

        shape = new ShapeRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        path.add(new PathArea(8*16f,13*16f,16*6f,43*16f));

        map = new TmxMapLoader().load("map02.tmx");
        cam = new OrthographicCamera(w, h);
        renderer = new OrthogonalTiledMapRenderer(map);

        enemies = new ArrayList<>();

        turrets = new ArrayList<>();


        cam.setToOrtho(false, w, h);
        cam.update();
        showTime = false;

    }

    @Override
    public void show() {

    }

    /**
     * Method where the everything gets rendered
     * @param delta
     */
    @Override
    public void render(float delta) {

        long time = TimeUtils.timeSinceMillis(lastTimeCountedRound);
        lastTimeCountedRound = TimeUtils.millis();
        // Time since last round for spawning enemies
        sinceChangeTime += time;

        //Standard settings
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();
        cam.update();

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        // show shop if b is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            showShop = !showShop;
        }
        // if enemies are gone the round is over
        if (enemies.isEmpty()) {
            isDone = true;
            showTime = true;
            if(!moneyAdded){
            money += 180;
            }
        }

        //removing enemies
        for (Iterator<PositionsMap02> iter = enemies.iterator(); iter.hasNext(); ) {
            PositionsMap02 it = iter.next();
            if (it.isPositionEnd() || it.getEnemyType().isDead()) {

                iter.remove();
            }
        }
        //show turrets
        for (TurretType turretType: turrets) {
            showTurrets(turretType);
        }

        // After the round
        if (!isDone) {
            for (PositionsMap02 enemy : enemies) {
                showEnemy(enemy);
                sinceChangeTime = 0;
            }
        } else {
            if(sinceChangeTime > 1){
                moneyAdded = true;
            }
            if (sinceChangeTime > 10000) {
                round++;
                sinceChangeTime = 0;
                isDone = false;
                showTime = false;
                createNewEnemies((int) (round*50));
                moneyAdded = false;
            }
        }
        // if turret is chosen
        if(isTurretChosen){
            hoverTurret();
        }
        //showing fps
        showFPS();
        //shows fps
        font.draw(game.batch, (int) frameRate + " fps", 3, Gdx.graphics.getHeight() - 10);
        //shows HP
        font.draw(game.batch, BaseHealth + " hp", 160, Gdx.graphics.getHeight() - 10);
        if(showTime){
            font.draw(game.batch, Math.round(sinceChangeTime/1000) + " s", Gdx.graphics.getWidth()-300, Gdx.graphics.getHeight() - 10);
        }
        game.batch.end();

        // showing Shop if enabled
        if(showShop){
            showShop();
        }


    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        generator.dispose();
    }

    private void showFPS() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCountedFPS);
        lastTimeCountedFPS = TimeUtils.millis();

        sinceChangeFPS += delta;
        if (sinceChangeFPS >= 1000) {
            sinceChangeFPS = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    private void createNewEnemies(int count) {
        int random;
        for (int i = 0; i < count; i++) {
            random = (int)(Math.random()*this.round)+1;
            System.out.println(random);
            switch (random){
                case 1:
                    enemies.add(new PositionsMap02(new Bug01(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 2:
                    enemies.add(new PositionsMap02(new Bug02(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 3:
                    enemies.add(new PositionsMap02(new Bug03(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 4:
                    enemies.add(new PositionsMap02(new Bug04(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 5:
                    enemies.add(new PositionsMap02(new Bug05(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 6:
                    enemies.add(new PositionsMap02(new Bug06(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 7:
                    enemies.add(new PositionsMap02(new Bug07(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 8:
                    enemies.add(new PositionsMap02(new Bug08(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 9:
                    enemies.add(new PositionsMap02(new Bug09(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
                case 10:
                    enemies.add(new PositionsMap02(new Bug10(positions[0].x - i * 40, positions[0].y, 1)));
                    break;
            }
        }
    }

    private void hoverTurret(){
        switch (turretToPlace){
            case 1:
                game.batch.draw(new TextureRegion(new Texture("turret01.png")), Gdx.input.getX()-25, Gdx.graphics.getHeight()-Gdx.input.getY()-25,50,50);
                break;
            case 2:
                game.batch.draw(new TextureRegion(new Texture("turret02.png")), Gdx.input.getX()-25, Gdx.graphics.getHeight()-Gdx.input.getY()-25,50,50);
                break;
            case 3:
                game.batch.draw(new TextureRegion(new Texture("turret03.png")), Gdx.input.getX()-25, Gdx.graphics.getHeight()-Gdx.input.getY()-25,50,50);
                break;
            case 4:
                game.batch.draw(new TextureRegion(new Texture("turret04.png")), Gdx.input.getX()-25, Gdx.graphics.getHeight()-Gdx.input.getY()-25,50,50);
                break;
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            isTurretChosen = false;
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            boolean isAllowed = true;
            for (TurretType turret : turrets) {
                    if (turret.isInside(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25, 50, 50))
                        isAllowed = false;
                }

            for (PathArea pathArea : path) {
                if (pathArea.isInside(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25, 50, 50))
                    isAllowed = false;
            }
            if (isAllowed){
                switch (turretToPlace){
                    case 1:
                        turrets.add(new Turret01(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25));
                        break;
                    case 2:
                        turrets.add(new Turret02(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25));
                        break;
                    case 3:
                        turrets.add(new Turret03(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25));
                        break;
                    case 4:
                        turrets.add(new Turret04(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25));
                        break;
                }
                isTurretChosen = false;
                money -= 50;
            }
        }
    }

    private void showEnemy(PositionsMap02 enemy) {
        if (!enemy.isPositionEnd()) {

            if (enemy.isPosition2()) {
                enemy.getEnemyType().changePos(enemy.getEnemyType().getSpeed() * Gdx.graphics.getDeltaTime(), 0);
                if (positions[3].x <= enemy.getEnemyType().getPos().x) {
                    enemy.getEnemyType().setPos(new Vector2(positions[3].x, positions[3].y));
                    enemy.setPositionEnd(true);
                    this.BaseHealth -= enemy.getEnemyType().getDamage();
                    if (this.BaseHealth < 0) {
                        this.BaseHealth = 0;
                        game.setScreen(new GameOverScreen(game));
                    }
                }

            } else {
                if (enemy.isPosition1()) {
                    enemy.getEnemyType().changePos(0, enemy.getEnemyType().getSpeed() * Gdx.graphics.getDeltaTime());
                    if (positions[2].y <= enemy.getEnemyType().getPos().y) {
                        enemy.getEnemyType().setPos(new Vector2(positions[2].x, positions[2].y));
                        enemy.setPosition2(true);
                    }
                } else {
                    enemy.getEnemyType().changePos(enemy.getEnemyType().getSpeed() * Gdx.graphics.getDeltaTime(), 0);
                    if (positions[1].x <= enemy.getEnemyType().getPos().x) {
                        enemy.getEnemyType().setPos(new Vector2(positions[1].x, positions[1].y));
                        enemy.setPosition1(true);
                    }
                }
            }
            enemy.getEnemyType().render(game.batch);
        }

        }


    private void showShop(){

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLUE);
        shape.rect(400, 0, Gdx.graphics.getWidth() - 800, 100);
        shape.end();

        game.batch.begin();
        game.batch.draw(new TextureRegion(new Texture("turret01.png")), 450, (100 - 64) / 2, 64, 64);
        game.batch.draw(new TextureRegion(new Texture("turret02.png")), 750, (100 - 64) / 2, 64, 64);
        game.batch.draw(new TextureRegion(new Texture("turret03.png")), 1050, (100 - 64) / 2, 64, 64);
        game.batch.draw(new TextureRegion(new Texture("turret04.png")), 1350, (100 - 64) / 2, 64, 64);

        if (450 < Gdx.input.getX() && 450 + 64 > Gdx.input.getX() && (100 - 64) / 2 < Gdx.graphics.getHeight() - Gdx.input.getY() && 25 + 64 > Gdx.graphics.getHeight() - Gdx.input.getY()) {
            if (Gdx.input.justTouched()) {
                isTurretChosen = true;
                turretToPlace = 1;
            }
        }
        if (750 < Gdx.input.getX() && 750 + 64 > Gdx.input.getX() && (100 - 64) / 2 < Gdx.graphics.getHeight() - Gdx.input.getY() && 25 + 64 > Gdx.graphics.getHeight() - Gdx.input.getY()) {
            if (Gdx.input.justTouched()) {
                isTurretChosen = true;
                turretToPlace = 2;
            }
        }
        if (1050 < Gdx.input.getX() && 1050 + 64 > Gdx.input.getX() && (100 - 64) / 2 < Gdx.graphics.getHeight() - Gdx.input.getY() && 25 + 64 > Gdx.graphics.getHeight() - Gdx.input.getY()) {
            if (Gdx.input.justTouched()) {
                isTurretChosen = true;
                turretToPlace = 3;
            }
        }
        if (1350 < Gdx.input.getX() && 1350 + 64 > Gdx.input.getX() && (100 - 64) / 2 < Gdx.graphics.getHeight() - Gdx.input.getY() && 25 + 64 > Gdx.graphics.getHeight() - Gdx.input.getY()) {
            if (Gdx.input.justTouched()) {
                isTurretChosen = true;
                turretToPlace = 4;
            }
        }

        game.batch.end();

    }

    private void showTurrets(TurretType turretType){
        if (!turretType.hasEnemy()) {
            for (PositionsMap02 enemy : enemies) {

                if (turretType.getPos().dst(enemy.getEnemyType().getPos()) < turretType.getRange()) {
                    turretType.setEnemy(enemy.getEnemyType());
                }

            }
        }
        turretType.render(game.batch);

    }

}
