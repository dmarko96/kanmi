package com.etf.lab3.kanmi;

import com.etf.lab3.kanmi.objects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application
{
    private static final double WINDOW_WIDTH = 800.0;
    private static final double WINDOW_HEIGHT = 600.0;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.CADETBLUE;

    private Group objects;
    private Scene scene;
    private Stage stage;
    private Player player;
    private World world;
    private int points=0;
    private Text gameTimeDisplay;
    private Text gamePoints;
    private Text freezeText;
    private Text immuneText;
    private Box healthBox;
    private Box energyBox;
    private double boxSize=70;
    private Group uiStackPane;
    public static final int PLAYER_ENERGY=5000;
    private int playerEnergy=PLAYER_ENERGY;

    private final UpdateTimer timer = new UpdateTimer();
    private List<Enemy> enemyList=new ArrayList<Enemy>();
    private long freezeTime=0;
    private double playerHealth=PLAYER_HEALTH;
    public static final double PLAYER_HEALTH=100.0;
    public static final double PLAYER_DAMAGE=0.1;
    private long immuneTime=0;
    private Box testBox;
    private boolean activeGame=true;
    private static final int JOBSTACLE_NUMBER=10;
    private static final double BULLET_SPEED=1.5;
    private List<Projectile> projectileList;
    private Cannon cannon1;
    private Cannon cannon2;

    private class UpdateTimer extends AnimationTimer
    {
        private long before=0;
        private long time=0;
        private long gameTime=0;
        private long enemyTime=0;
        private long energyCoin=0;
        private long healthTimer=0;
        private long jokerTime=0;
        private long cannonTime=0;
        @Override
        public void handle(long now)
        {
            if(!activeGame)return;
            if(before==0)before=now;
            long ds=(now-before)/1000000;
            time += ds;
            gameTime += ds;
            enemyTime += ds;
            energyCoin += ds;
            healthTimer += ds;
            jokerTime += ds;
            immuneTime -= ds;
            freezeTime -= ds;
            cannonTime+=ds;
            String min=Long.toString(gameTime/60000);
            String sec=Long.toString(gameTime/1000%60);
            min=String.format("%02d",Integer.parseInt(min));
            sec=String.format("%02d",Integer.parseInt(sec));
            gameTimeDisplay.setText(min+":"+sec);
            if(time>5000){
                spawnCoin();
                time=0;
            }
            if(energyCoin>12000){
                energyCoin=0;
                CoinEnergy coinEnergy=new CoinEnergy(getRandomEmptySpace());
                objects.getChildren().addAll(coinEnergy);
            }
            if(enemyTime>=2000){
                enemyTime=0;
                for (Enemy e:enemyList){
                    e.randomizeDirection();
                }
            }
            if(freezeTime<=0) {
                freezeTime=0;
                for (Enemy e : enemyList) {
                    if(e instanceof RedEnemy)e.randomizeDirection(new Point2D(player.getTranslateX(),player.getTranslateZ()));
                    updateEnemy(e);
                }
            }
            if(immuneTime<0){
                immuneText.setText("");
                immuneTime=0;
            }
            if(healthTimer>10000){
                healthTimer=0;
                if(Math.random()*100>=90) {
                    CoinImmune coinImmune = new CoinImmune(getRandomEmptySpace());
                    objects.getChildren().addAll(coinImmune);
                }
                if(Math.random()*100>=80) {
                    CoinHealth coinHealth = new CoinHealth(getRandomEmptySpace());
                    objects.getChildren().addAll(coinHealth);
                }
            }
            if(jokerTime>=15000){
                jokerTime=0;
                CoinJoker coinJoker=new CoinJoker(getRandomEmptySpace());
                objects.getChildren().addAll(coinJoker);
            }
            if(cannonTime>3000){
                cannonTime=0;
                shootCannon();
            }
            updatePlayer(now);
            updateBullet();
            updateUiText();
            before=now;
        }
    }
    private void updateUiText(){
        immuneText.setText(durationToString(immuneTime));
        freezeText.setText(durationToString(freezeTime));

    }
    private void shootCannon(){
        Point2D p1Point=cannon1.getPosition();
        Point2D p2Point=cannon2.getPosition();
        Projectile p1=new Projectile(p1Point.getX(),p1Point.getY(),cannon1.getDirection());
        Projectile p2=new Projectile(p2Point.getX(),p2Point.getY(),cannon2.getDirection());
        projectileList.add(p1);
        projectileList.add(p2);
        objects.getChildren().addAll(p1,p2);
    }
    private void updateBullet(){
        for (int i = 0; i < projectileList.size(); i++) {
            Projectile p=projectileList.get(i);
            if(p.move()){
                projectileList.remove(p);
                objects.getChildren().remove(p);
            }
            else if(p.getBoundsInParent().intersects(player.getBoundsInParent())){
                damagePlayer(15);
                projectileList.remove(p);
                objects.getChildren().remove(p);
            }
            else {
                for (int j = 0; j < objects.getChildren().size(); j++) {
                    Node node=objects.getChildren().get(j);
                    if(node instanceof ObstacleBox || node instanceof JObstacle){
                        if(node.getBoundsInParent().intersects(p.getBoundsInParent())){
                            projectileList.remove(p);
                            objects.getChildren().remove(p);
                        }
                    }
                }
            }
        }
    }
    private String durationToString(long dur){
        if(dur==0)return "0";
        return Long.toString(dur/1000+1);
    }

    private void spawnCoin(){
        //freezeCoin
        double randFreeze=Math.random()*100;
        if(randFreeze<=30){
            CoinFreeze cf=new CoinFreeze(getRandomEmptySpace());
            objects.getChildren().addAll(cf);
        }
        Point2D randPoint=getRandomEmptySpace();
        double coinRand=Math.random()*100;
        int coinType=0;
        if(coinRand>=80)coinType=2;
        else if(coinRand>=50)coinType=1;
        Coin coin=new Coin(getRandomEmptySpace(),coinType);
        objects.getChildren().addAll(coin);
    }
    private void setupScene()
    {
        Group root = new Group();
        objects = new Group();
        scene = new Scene(root,
                WINDOW_WIDTH,
                WINDOW_HEIGHT,
                true,
                SceneAntialiasing.DISABLED);

        scene.setFill(DEFAULT_BACKGROUND_COLOR);
        scene.setCursor(Cursor.NONE);

        player = new Player();
        scene.setCamera(player.getCamera());
        scene.setOnMouseMoved(player);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);

        //testbox
        testBox=new Box(20,20,20);
        testBox.setMaterial(new PhongMaterial(Color.TRANSPARENT));

        //ui
        uiStackPane=new Group();
        Font uiFont=new Font(10);
        uiStackPane.setDepthTest(DepthTest.DISABLE);


        //cannon
        int cannonWall=(int) (Math.random()*4);
        Point2D cannon1Pos=null;
        Point2D cannon2Pos=null;
        Point2D cannonDir=null;
        switch (cannonWall){
            case 0:
                cannon1Pos=new Point2D(-World.GROUND_WIDTH/2,-World.GROUND_WIDTH/6);
                cannon2Pos=new Point2D(-World.GROUND_WIDTH/2,World.GROUND_WIDTH/6);
                cannonDir=new Point2D(1,0);
                break;
            case 1:
                cannon1Pos=new Point2D(-World.GROUND_WIDTH/6,World.GROUND_WIDTH/2);
                cannon2Pos=new Point2D(World.GROUND_WIDTH/6,World.GROUND_WIDTH/2);
                cannonDir=new Point2D(0,-1);
                break;
            case 2:
                cannon1Pos=new Point2D(World.GROUND_WIDTH/2,-World.GROUND_WIDTH/6);
                cannon2Pos=new Point2D(World.GROUND_WIDTH/2,World.GROUND_WIDTH/6);
                cannonDir=new Point2D(-1,0);
                break;
            case 3:
                cannon1Pos=new Point2D(-World.GROUND_WIDTH/6,-World.GROUND_WIDTH/2);
                cannon2Pos=new Point2D(World.GROUND_WIDTH/6,-World.GROUND_WIDTH/2);
                cannonDir=new Point2D(0,1);
                break;
        }
        cannon1=new Cannon(cannon1Pos.getX(),cannon1Pos.getY(),cannonWall%2,cannonDir);
        cannon2=new Cannon(cannon2Pos.getX(),cannon2Pos.getY(),cannonWall%2,cannonDir);
        objects.getChildren().addAll(cannon1,cannon2);
        projectileList=new ArrayList<Projectile>();

        freezeText=new Text(Long.toString(freezeTime/1000));
        freezeText.setFont(uiFont);
        freezeText.setStroke(Color.BLUE);
        immuneText=new Text(Long.toString(immuneTime/1000));
        immuneText.setFont(uiFont);
        immuneText.setStroke(Color.PURPLE);

        healthBox=new Box(boxSize,1,1);
        healthBox.setMaterial(new PhongMaterial(Color.RED));
        energyBox=new Box(boxSize,1,1);
        energyBox.setMaterial(new PhongMaterial(Color.YELLOW));



        gameTimeDisplay =new Text("00:00");
        gameTimeDisplay.setFont(uiFont);
        gamePoints=new Text(Integer.toString(points));
        gamePoints.setFont(uiFont);

        uiStackPane.getChildren().addAll(gameTimeDisplay,gamePoints,freezeText,immuneText,healthBox,energyBox);
        root.getChildren().addAll(uiStackPane);

        Rotate rotUi=new Rotate(0,new Point3D(0,1,0));
        uiStackPane.getTransforms().add(rotUi);

        energyBox.getTransforms().addAll(new Translate(0,-45,0));
        healthBox.getTransforms().addAll(new Translate(0,-50,0));
        freezeText.getTransforms().addAll(new Translate(-65,-35,0));
        immuneText.getTransforms().addAll(new Translate(45,-35,0));
        gamePoints.getTransforms().addAll(new Translate(-65,-45,0));
        gameTimeDisplay.getTransforms().add(new Translate(45,-45,0));
        uiStackPane.getTransforms().add(new Translate(0,0,100));
        player.rotateProperty().addListener((obs,oldVal,newVal)->{
            rotUi.setAngle(newVal.doubleValue());
        });
        //ui


        world = new World();

        AmbientLight ambientLight = new AmbientLight(Color.DARKGRAY);
        ambientLight.setOpacity(0.2);
        ambientLight.setBlendMode(BlendMode.SOFT_LIGHT);
        PointLight pointLight = new PointLight(Color.WHITESMOKE);
        pointLight.setTranslateY(-100);

        for(int i=0;i<10;i++){
            ObstacleBox obstacleBox=new ObstacleBox(getRandomEmptySpace());
            objects.getChildren().addAll(obstacleBox);
        }
        for (int i = 0; i < 4; i++) {
            Enemy enemy;
            if(i<3) {
                enemy = new Enemy(getRandomEmptySpace());
            }
            else{
                enemy=new RedEnemy(getRandomEmptySpace());
            }
            objects.getChildren().addAll(enemy);
            enemyList.add(enemy);
        }
        for (int i = 0; i < JOBSTACLE_NUMBER; i++) {
            JObstacle jObstacle=new JObstacle(getRandomEmptySpace(),(int)(Math.random()*2));
            objects.getChildren().add(jObstacle);
        }

        root.getChildren().addAll(world, player, ambientLight, pointLight, objects);
        uiStackPane.toFront();
    }

    private void showStage()
    {
        stage.setTitle("Kanmi");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        timer.start();
    }

    private void updateEnemy(Enemy enemy){
        double oldX = enemy.getTranslateX();
        double oldZ = enemy.getTranslateZ();
        Point2D enemyDirection=enemy.getDirection();
        enemy.setTranslateX(enemy.getTranslateX()+enemy.getSpeed()*enemyDirection.getX());
        enemy.setTranslateZ(enemy.getTranslateZ()+enemy.getSpeed()*enemyDirection.getY());


        if (enemy.getBoundsInParent().intersects(player.getBoundsInParent())){
            //dodati game over screen
            damagePlayer(PLAYER_DAMAGE);
            if(playerHealth<=0){
                endGame();
            }
        }
        if (enemy.getTranslateX() + enemy.getBoundsInParent().getWidth() / 2 >= world.getBoundsInLocal().getMaxX() ||
                enemy.getTranslateX() - enemy.getBoundsInParent().getWidth() / 2 <= world.getBoundsInLocal().getMinX()){
            enemy.setTranslateX(oldX);
        }
        if (enemy.getTranslateZ() + enemy.getBoundsInParent().getWidth() / 2 >= world.getBoundsInLocal().getMaxZ() ||
                enemy.getTranslateZ() - enemy.getBoundsInParent().getWidth() / 2 <= world.getBoundsInLocal().getMinZ()) {
            enemy.setTranslateZ(oldZ);
        }
        for (int i = 0; i < objects.getChildren().size(); ++i)
        {
            Node node = objects.getChildren().get(i);
            if(node instanceof ObstacleBox || node instanceof JObstacle){
                if(node.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                    enemy.setTranslateX(oldX);
                    enemy.setTranslateZ(oldZ);
                }
            }
            if (node instanceof Coin || node instanceof CoinFreeze)
            {
                if (node.getBoundsInParent().intersects(enemy.getBoundsInParent()))
                {
                    objects.getChildren().remove(node);
                    --i;
                }
            }
        }

    }
    private void updateUiElements(double x,double y,double z){
        if(uiStackPane==null){
            return;
        }
        uiStackPane.setTranslateX(x);
        uiStackPane.setTranslateY(y);
        uiStackPane.setTranslateZ(z);
    }
    private void updatePlayer(long now)
    {
        double oldX = player.getTranslateX();
        double oldZ = player.getTranslateZ();

        double speedMod= ((double)playerEnergy/PLAYER_ENERGY)*1/2+1.0/2;
        if(playerEnergy>0) {
            if (player.isUpPressed()) {
                player.setTranslateX(player.getTranslateX() + speedMod*Player.PLAYER_SPEED * Math.cos(Math.toRadians(player.getRotate() - 90)));
                player.setTranslateZ(player.getTranslateZ() - speedMod*Player.PLAYER_SPEED * Math.sin(Math.toRadians(player.getRotate() - 90)));
            }
            if (player.isDownPressed()) {
                player.setTranslateX(player.getTranslateX() + speedMod*Player.PLAYER_SPEED * Math.cos(Math.toRadians(player.getRotate() + 90)));
                player.setTranslateZ(player.getTranslateZ() - speedMod*Player.PLAYER_SPEED * Math.sin(Math.toRadians(player.getRotate() + 90)));
            }
            if (player.isLeftPressed()) {
                player.setTranslateX(player.getTranslateX() - speedMod*Player.PLAYER_SPEED * Math.cos(Math.toRadians(player.getRotate())));
                player.setTranslateZ(player.getTranslateZ() + speedMod*Player.PLAYER_SPEED * Math.sin(Math.toRadians(player.getRotate())));
            }
            if (player.isRightPressed()) {
                player.setTranslateX(player.getTranslateX() + speedMod*Player.PLAYER_SPEED * Math.cos(Math.toRadians(player.getRotate())));
                player.setTranslateZ(player.getTranslateZ() - speedMod*Player.PLAYER_SPEED * Math.sin(Math.toRadians(player.getRotate())));
            }

            if (player.getTranslateX() + Player.PLAYER_RADIUS / 2 >= world.getBoundsInLocal().getMaxX() ||
                    player.getTranslateX() - Player.PLAYER_RADIUS / 2 <= world.getBoundsInLocal().getMinX()) {
                player.setTranslateX(oldX);
            }
            if (player.getTranslateZ() + Player.PLAYER_RADIUS / 2 >= world.getBoundsInLocal().getMaxZ() ||
                    player.getTranslateZ() - Player.PLAYER_RADIUS / 2 <= world.getBoundsInLocal().getMinZ()) {
                player.setTranslateZ(oldZ);
            }

            updateUiElements(player.getTranslateX(),player.getTranslateY(),player.getTranslateZ());
        }
        if(oldX!=player.getTranslateX() || oldZ!=player.getTranslateZ()){
            addEnergy(-2);
        }
        else{
            addEnergy(2);
        }
        for (int i = 0; i < objects.getChildren().size(); ++i)
        {
            Node node = objects.getChildren().get(i);
            if(node instanceof ObstacleBox || node instanceof JObstacle){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
                    player.setTranslateX(oldX);
                    player.setTranslateZ(oldZ);
                }
            }
            if (node instanceof Coin)
            {
                if (node.getBoundsInParent().intersects(player.getBoundsInParent()))
                {
                    addPoints(((Coin) node).getPoints());
                    objects.getChildren().remove(node);
                    --i;
                }
            }
            else if(node instanceof CoinFreeze){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
                    addFreeze();
                    objects.getChildren().remove(node);
                    --i;
                }
            }
            else if(node instanceof CoinEnergy){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
//                    playerEnergy+=(int)((double)PLAYER_ENERGY/3.0);
//                    playerEnergy=playerEnergy>PLAYER_ENERGY?PLAYER_ENERGY:playerEnergy;
                    addEnergy((int)((double)PLAYER_ENERGY/3.0));
                    objects.getChildren().remove(node);
                    --i;
                }
            }
            else if(node instanceof CoinHealth){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
//                    playerHealth=playerHealth+PLAYER_HEALTH*0.25;
//                    playerHealth= playerHealth>PLAYER_HEALTH?PLAYER_HEALTH:playerHealth;
                    addHealth(PLAYER_HEALTH*0.25);
                    objects.getChildren().remove(node);
                    --i;
                }
            }
            else if(node instanceof CoinImmune){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
                    addImmune();
                    objects.getChildren().remove(node);
                    --i;
                }
            }
            else if(node instanceof CoinJoker){
                if(node.getBoundsInParent().intersects(player.getBoundsInParent())){
                    double jokerRandom=Math.random()*100;
                    if(jokerRandom<=40){
                        int randPoint=(int) (Math.random()*10)+1;
                        addPoints(randPoint);
                    } else if(jokerRandom<=60){
                        addHealth(20);
                        addEnergy((int) (-20*PLAYER_ENERGY/100.0));
                    } else if (jokerRandom <= 80) {
                        addHealth(-20);
                        addEnergy((int) (20*PLAYER_ENERGY/100.0));
                    } else if(jokerRandom<=90){
                        addFreeze();
                    }else{
                        addImmune();
                    }
                    objects.getChildren().remove(node);
                    --i;
                }
            }
        }
    }

    public Point2D getRandomEmptySpace(){
        Point2D point2D=new Point2D(0,0);
        Random random=new Random();


        while(true){
            boolean spaceTaken=false;
            double randX=(random.nextDouble()*(World.GROUND_WIDTH)-World.GROUND_WIDTH/2.0)*0.95;
            double randZ=(random.nextDouble()*(World.GROUND_LENGTH)-World.GROUND_LENGTH/2.0)*0.95;
            Translate testTranslate=new Translate(randX,0,randZ);
            point2D=new Point2D(randX,randZ);
            for (int i = 0; i < objects.getChildren().size(); i++) {
                Node node=objects.getChildren().get(i);
                testBox.getTransforms().setAll(testTranslate);
                if(node.getBoundsInParent().intersects(testBox.getBoundsInParent())){
                    spaceTaken=true;
                    break;
                }
            }
            if(!spaceTaken)break;
        }
        return point2D;
    }
    private void damagePlayer(double damage){
        if(immuneTime>0)return;
        playerHealth-=damage;
        healthBox.setWidth(boxSize*((double)playerHealth/PLAYER_HEALTH));
        if(playerHealth<=0){
            playerHealth=0;
            endGame();
        }
    }
    public void endGame(){
        // kraj igre
        activeGame=false;
        player.setGameActive(false);
        Text gameOverText=new Text("GAME OVER");
        Text scoreText=new Text("SCORE "+points);
        scoreText.setFont(new Font(20));
        gameOverText.setFont(new Font(20));
        gameOverText.getTransforms().add(new Translate(-50,-5,0));
        scoreText.getTransforms().add(new Translate(-45,13,0));
        uiStackPane.getChildren().addAll(gameOverText,scoreText);
    }
    private void addHealth(double hp){
        playerHealth+=hp;
        playerHealth=playerHealth>PLAYER_HEALTH?PLAYER_HEALTH:playerHealth;
        healthBox.setWidth(boxSize*((double)playerHealth/PLAYER_HEALTH));
        if(playerHealth<=0){
            //gameover
        }
    }
    private void addEnergy(int en){
        playerEnergy+=en;
        playerEnergy=playerEnergy>PLAYER_ENERGY?PLAYER_ENERGY:playerEnergy;
        if(playerEnergy<0)playerEnergy=0;
        energyBox.setWidth(boxSize*((double)playerEnergy/PLAYER_ENERGY));
    }
    private void addFreeze(){
        freezeTime=10000;
    }
    private void addImmune(){
        immuneTime=10000;
    }
    private void addPoints(int p){
        points+=p;
        gamePoints.setText(Integer.toString(points));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;

        setupScene();
        showStage();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
