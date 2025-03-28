package com.etf.lab3.kanmi;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Player extends Group implements EventHandler<Event>
{
    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;
    public static final double PLAYER_SPEED = 0.8;
    public static final double PLAYER_RADIUS = 20;
    public static final double PLAYER_ROTATION_SPEED=5.0;

    private final PerspectiveCamera camera;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean isGameActive = true;
    private double rotationValue=0;
    private boolean isJumping=false;
    private static final double JUMP_HEIGHT=10.0;

    public Player()
    {
        Sphere shape = new Sphere(PLAYER_RADIUS);
        shape.setVisible(false);

        camera = new PerspectiveCamera(true);
        camera.setNearClip(NEAR_CLIP);
        camera.setFarClip(FAR_CLIP);
        camera.setFieldOfView(FIELD_OF_VIEW);

        setRotationAxis(new Point3D(0, 1, 0));
        this.getChildren().addAll(shape, camera);
    }

    private boolean ignoreMouseEvent=false;
    private Robot mouseMover=new Robot();

    @Override
    public void handle(Event event)
    {
        if(getTranslateY()<=0)isJumping=false;
        if (event instanceof KeyEvent keyEvent)
        {
            if (keyEvent.getCode() == KeyCode.ESCAPE && keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                System.exit(0);
            else if(keyEvent.getCode()==KeyCode.SPACE){
                jump();
            }
            else
            {
                if (!isGameActive)
                {
                    upPressed = false;
                    downPressed = false;
                    rightPressed = false;
                    leftPressed = false;
                    return;
                }

                if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP)
                {
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                        upPressed = true;
                    else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                        upPressed = false;
                }
                else if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN)
                {
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                        downPressed = true;
                    else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                        downPressed = false;
                }
                else if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT)
                {
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                        leftPressed = true;
                    else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                        leftPressed = false;
                }
                else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT)
                {
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                        rightPressed = true;
                    else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                        rightPressed = false;
                }
            }
        }
        else if (event instanceof MouseEvent mouseEvent)
        {
            if(!isGameActive)return;
            if (MouseEvent.MOUSE_MOVED.equals(mouseEvent.getEventType())){
                if(ignoreMouseEvent){
                    ignoreMouseEvent=false;
                    return;
                }
                rotationValue+=(mouseEvent.getScreenX()-(getScene().getWindow().getX()+getScene().getWindow().getWidth()/2.0))/PLAYER_ROTATION_SPEED;
                setRotate(rotationValue);

//                setRotate(mouseEvent.getSceneX() * 390. / getScene().getWidth() - 195.);
                ignoreMouseEvent=true;
                mouseMover.mouseMove( getScene().getWindow().getX()+getScene().getWindow().getWidth()/2.0,getScene().getWindow().getY()+getScene().getWindow().getHeight()/2.0);
            }

        }
    }

    public Camera getCamera()
    {
        return camera;
    }
    public void jump(){
        if(isJumping)return;
        Timeline timeline=new Timeline();
        double skok=0;
        setTranslateY(skok);
        KeyFrame kf2=new KeyFrame(Duration.seconds(0.3),new KeyValue(translateYProperty(),-JUMP_HEIGHT));
        KeyFrame kf3=new KeyFrame(Duration.seconds(1),new KeyValue(translateYProperty(),0));
        timeline.getKeyFrames().addAll(kf2,kf3);
        timeline.setCycleCount(1);
        timeline.play();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    public double getRotationValue(){
        return rotationValue;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }
}
