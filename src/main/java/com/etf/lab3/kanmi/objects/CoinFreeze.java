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
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CoinFreeze extends AbstractCoin {
    public CoinFreeze(Point2D pos){
        super(pos);
        PhongMaterial material=new PhongMaterial(Color.CYAN);
        Box box1=new Box(1,15,1);
        box1.setMaterial(material);
        Rotate rotateX1=new Rotate(30,Rotate.Z_AXIS);
        box1.getTransforms().addAll(rotateX1);

        Box box2=new Box(1,15,1);
        box2.setMaterial(material);
        Rotate rotateX2=new Rotate(90,Rotate.Z_AXIS);
        box2.getTransforms().addAll(rotateX2);

        Box box3=new Box(1,15,1);
        box3.setMaterial(material);
        Rotate rotateX3=new Rotate(150,Rotate.Z_AXIS);
        box3.getTransforms().addAll(rotateX3);
        getChildren().addAll(box1,box2,box3);
    }
}
