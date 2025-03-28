package com.etf.lab3.kanmi;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

public class RedEnemy extends Enemy{
    public RedEnemy(Point2D pos){
        super(pos,Color.RED);
    }
    @Override
    public void randomizeDirection(Point2D playerPos) {
        Point2D currPos=new Point2D(getTranslateX(),getTranslateZ());
        Point2D newPoint=playerPos.subtract(currPos);
        setDirection(newPoint.normalize());
    }
}
