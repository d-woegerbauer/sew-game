package tv.gregor.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import tv.gregor.game.GameMaps.GameMap;
import tv.gregor.game.Main;
import tv.gregor.game.PositionsMap01;
import tv.gregor.game.entities.Bug01;
import tv.gregor.game.pathhelper.PathArea;
import tv.gregor.game.turrets.Turret01;
import tv.gregor.game.turrets.TurretType;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {

    public static final int CHAR_WIDTH_PIXEL = 17;
    public static final int CHAR_HEIGHT_PIXEL = 32;


    public static final int CHAR_WIDTH = CHAR_WIDTH_PIXEL * 3;
    public static final int CHAR_HEIGHT = CHAR_HEIGHT_PIXEL * 3;

    Animation<TextureRegion>[] rolls;

    Vector2[] positions;

    GameMap gameMap;

    OrthographicCamera cam;

    private float BaseHealth = 100;

    long lastTimeCountedFPS;
    long lastTimeCountedRound;

    boolean showShop;

    private float sinceChangeFPS;
    private float sinceChangeTime;
    private float frameRate;
    private boolean isDone;
    private BitmapFont font;

    float charX, charY;
    int roll;
    float stateTime;
    float rollTimer;

    boolean isTurretChosen;
    ArrayList<PathArea> path;
    OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    ArrayList<PositionsMap01> enemies;

    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator generator;

    ShapeRenderer shape;

    ArrayList<TurretType> turrets;

    Main game;

    public GameScreen(Main game) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Plumpfull.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 28;
        parameter.color = Color.BLACK;

        showShop = true;
        this.game = game;
        isDone = false;
        lastTimeCountedFPS = TimeUtils.millis();
        lastTimeCountedRound = TimeUtils.millis();
        sinceChangeFPS = 0;
        sinceChangeTime = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = generator.generateFont(parameter);

        isTurretChosen = false;
        path = new ArrayList<>();
        path.add(new PathArea(41*16f,11*16f,6*16f,54*16));
        path.add(new PathArea(47*16f,11*16f,71*16f,6*16f));

        charY = Main.HEIGHT / 2 - CHAR_HEIGHT / 2;
        charX = Main.WIDTH / 2 - CHAR_WIDTH / 2;

        positions = new Vector2[]{
                new Vector2(Gdx.graphics.getWidth(), 14 * 16f),
                new Vector2(44 * 16f, 14 * 16f),
                new Vector2(44 * 16f, Gdx.graphics.getHeight())
        };


        roll = 2;
        rollTimer = 0;
        rolls = new Animation[5];
        shape = new ShapeRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        map = new TmxMapLoader().load("map01.tmx");
        cam = new OrthographicCamera(w, h);
        renderer = new OrthogonalTiledMapRenderer(map);

        enemies = new ArrayList<>();
        createNewEnemies(50);

        turrets = new ArrayList<>();


        cam.setToOrtho(false, w, h);
        cam.update();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        long time = TimeUtils.timeSinceMillis(lastTimeCountedRound);
        lastTimeCountedRound = TimeUtils.millis();




        sinceChangeTime += time;

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(cam);
        renderer.render();
        cam.update();

        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        if(this.BaseHealth == 0){
            System.out.println("Your are dead");
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
            showShop = !showShop;
        }

        if (enemies.isEmpty()) {
            isDone = true;
        }

        for (Iterator<PositionsMap01> iter = enemies.iterator(); iter.hasNext(); ) {
            PositionsMap01 it = iter.next();
            if (it.isPositionEnd() || it.getEnemyType().isDead()) {

                iter.remove();
            }
        }
        for (TurretType turretType: turrets) {
            showTurrets(turretType);
        }


        if (!isDone) {
            for (PositionsMap01 enemy : enemies) {
                showEnemy(enemy);
            }
        } else {
            if (sinceChangeTime > 1000) {
                sinceChangeTime = 0;
                isDone = false;
                createNewEnemies(50);
            }
        }
        if(isTurretChosen){
            hoverTurret();
        }

        showFPS();
        font.draw(game.batch, (int) frameRate + " fps", 3, Gdx.graphics.getHeight() - 10);
        font.draw(game.batch, BaseHealth + " hp", 160, Gdx.graphics.getHeight() - 10);
        game.batch.end();

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
        gameMap.dispose();
        generator.dispose();
    }

    public void showFPS() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCountedFPS);
        lastTimeCountedFPS = TimeUtils.millis();

        sinceChangeFPS += delta;
        if (sinceChangeFPS >= 1000) {
            sinceChangeFPS = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public void createNewEnemies(int count) {
        for (int i = 0; i < count; i++) {
            enemies.add(new PositionsMap01(new Bug01(positions[0].x + i * 40, positions[0].y, 1)));
        }
    }

    public void hoverTurret(){
        game.batch.draw(new TextureRegion(new Texture("turret01.png")), Gdx.input.getX()-25, Gdx.graphics.getHeight()-Gdx.input.getY()-25,50,50);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            isTurretChosen = false;
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            boolean isAllowed = true;
            for (TurretType turret : turrets) {
                if (turret instanceof Turret01) {
                    Turret01 turret01 = (Turret01) turret;
                    if (turret01.isInside(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25, 50, 50))
                        isAllowed = false;
                }
            }
            for (PathArea pathArea : path) {
                if (pathArea.isInside(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25, 50, 50))
                    isAllowed = false;
            }
            if (isAllowed){
                turrets.add(new Turret01(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25));
                isTurretChosen = false;
            }
        }
    }

    public void showEnemy(PositionsMap01 enemy) {
        if (enemy.getEnemyType() instanceof Bug01) {

            Bug01 bug01 = (Bug01) enemy.getEnemyType();

            if (!enemy.isPositionEnd()) {

                if (enemy.isPosition1()) {
                    bug01.changePos(0, bug01.getSpeed() * Gdx.graphics.getDeltaTime());
                    if (bug01.getPos().y >= positions[2].y) {
                        bug01.setPos(new Vector2(positions[2].x, positions[2].y));
                        enemy.setPositionEnd(true);
                        this.BaseHealth -= bug01.getDamage();
                        if (this.BaseHealth < 0)
                            this.BaseHealth = 0;
                    }

                } else {
                    bug01.changePos(-bug01.getSpeed() * Gdx.graphics.getDeltaTime(), 0);
                    if (bug01.getPos().x <= positions[1].x) {
                        bug01.setPos(new Vector2(positions[1].x, positions[1].y));
                        enemy.setPosition1(true);
                    }
                }

                bug01.render(game.batch);
            }

        }
    }

    public void showShop(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLUE);
        shape.rect(400,0,Gdx.graphics.getWidth()-800,100);
        shape.end();

        game.batch.begin();
        game.batch.draw(new TextureRegion(new Texture("turret01.png")), 450, (100-64)/2,64,64);

        if(450 < Gdx.input.getX()&& 450+64 > Gdx.input.getX()&& (100-64)/2 < Gdx.graphics.getHeight()- Gdx.input.getY()&& 25+64 > Gdx.graphics.getHeight()- Gdx.input.getY()){
            if(Gdx.input.justTouched()){
                isTurretChosen = true;
            }
        }

        game.batch.end();
    }

    public void showTurrets(TurretType turretType){
        if(turretType instanceof Turret01){
            Turret01 turret01 = (Turret01) turretType;
            if(!turret01.hasEnemy()){
                for (PositionsMap01 enemy : enemies) {
                    if(enemy.getEnemyType() instanceof Bug01){
                        Bug01 bug01 = (Bug01) enemy.getEnemyType();
                        if(turret01.getPos().dst(bug01.getPos())<turret01.getRange()){
                            turret01.setEnemy(bug01);
                        }

                    }
                }
            }
            turret01.render(game.batch);
        }
    }

}
