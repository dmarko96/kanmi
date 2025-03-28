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
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class CoinJoker extends AbstractCoin {
    public CoinJoker(Point2D pos){
        super(pos);
        Cylinder cylinder=new Cylinder(2,10);
        cylinder.setMaterial(new PhongMaterial(Color.YELLOW));
        Sphere sphere=new Sphere(2);
        sphere.setMaterial(new PhongMaterial(Color.YELLOW));
        cylinder.getTransforms().addAll(new Translate(0,-4.0,0));
        sphere.getTransforms().addAll(new Translate(0,4.0,0));


        getChildren().addAll(cylinder,sphere);
    }
}
