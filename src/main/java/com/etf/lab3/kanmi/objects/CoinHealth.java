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

public class CoinHealth extends AbstractCoin {
    public CoinHealth(Point2D pos){
        super(pos);
        Box boxWhite=new Box(10,10,2);
        Box box1=new Box(8,2,3);
        Box box2=new Box(2,8,3);
        boxWhite.setMaterial(new PhongMaterial(Color.WHITE));
        box1.setMaterial(new PhongMaterial(Color.RED));
        box2.setMaterial(new PhongMaterial(Color.RED));


        getChildren().addAll(box1,box2,boxWhite);

    }
}
