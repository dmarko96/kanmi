package com.etf.lab3.kanmi;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Cannon extends Group {
    public static final double BARREL_WIDTH=4.0;
    private static final double BARREL_HEIGHT=20.0;
    private Point2D direction;
    public Cannon(double x,double z,int facing,Point2D dir){
        Cylinder barrel=new Cylinder(BARREL_WIDTH,BARREL_HEIGHT+2.0);
        barrel.setMaterial(new PhongMaterial(Color.TRANSPARENT));
        Cylinder barrelOuter=new Cylinder(BARREL_WIDTH+1.0,BARREL_HEIGHT);
        barrelOuter.setMaterial(new PhongMaterial(Color.rgb(50,50,50)));

        getTransforms().add(new Rotate(90,Rotate.Z_AXIS));


        direction=dir;
        if(facing==1){
            setRotationAxis(Rotate.Y_AXIS);
            setRotate(90);
        }
        setTranslateX(x);
        setTranslateZ(z);
        getChildren().addAll(barrelOuter,barrel);

    }
    public Point2D getPosition(){
        return new Point2D(getTranslateX(),getTranslateZ());
    }
    public Point2D getDirection(){
        return direction;
    }
}
