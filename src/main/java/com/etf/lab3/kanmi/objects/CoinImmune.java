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

public class CoinImmune extends AbstractCoin {
    public CoinImmune(Point2D pos){
        super(pos);
        double h=4;
        PhongMaterial redMat=new PhongMaterial(Color.RED);
        PhongMaterial blueMat=new PhongMaterial(Color.BLUE);
        Cylinder red1=new Cylinder(2,h);
        Cylinder blue1=new Cylinder(2,h);
        Sphere red2=new Sphere(2);
        Sphere blue2=new Sphere(2);
        red1.setMaterial(redMat);
        red2.setMaterial(redMat);
        blue1.setMaterial(blueMat);
        blue2.setMaterial(blueMat);
        Rotate rotate=new Rotate(90,Rotate.Z_AXIS);
        red1.getTransforms().addAll(new Translate(h/2,0,0),rotate);
        blue1.getTransforms().addAll(new Translate(-h/2,0,0),rotate);
        red2.getTransforms().addAll(new Translate(h,0,0));
        blue2.getTransforms().addAll(new Translate(-h,0,0));

        getChildren().addAll(red1,red2,blue1,blue2);
    }
}
