package com.etf.lab3.kanmi.objects;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.util.Duration;

public class AbstractCoin extends Group {
    public AbstractCoin(Point2D pos){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), this);
        rotateTransition.setAxis(new Point3D(0, 1, 0));
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Integer.MAX_VALUE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();

        setTranslateX(pos.getX());
        setTranslateZ(pos.getY());

        Box boundsBox=new Box(15,15,15);
        boundsBox.setDepthTest(DepthTest.DISABLE);
        PhongMaterial transparentMat=new PhongMaterial();
        transparentMat.setDiffuseColor(Color.rgb(0,0,0,0));
        boundsBox.setMaterial(transparentMat);
        getChildren().addAll(boundsBox);
    }
}
