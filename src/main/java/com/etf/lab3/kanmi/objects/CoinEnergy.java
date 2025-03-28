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
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class CoinEnergy extends AbstractCoin {
    public CoinEnergy(Point2D pos){
        super(pos);
        PhongMaterial mat=new PhongMaterial(Color.WHITE);
        Box box1=new Box(1,6,1);
        Box box2=new Box(1,6,1);
        Box box3=new Box(1,6,1);
        box1.setMaterial(mat);
        box2.setMaterial(mat);
        box3.setMaterial(mat);

        Rotate rotate1=new Rotate(90,Rotate.Z_AXIS);
        Rotate rotate2=new Rotate(45,Rotate.Z_AXIS);
        Rotate rotate3=new Rotate(45,Rotate.Z_AXIS);
        Translate t2=new Translate(1.8,1.2,0);
        Translate t3=new Translate(-1.8,-1.2,0);
        box1.getTransforms().addAll(rotate1);
        box2.getTransforms().addAll(rotate2,t2);
        box3.getTransforms().addAll(rotate3,t3);
        getChildren().addAll(box1,box2,box3);
    }
}
