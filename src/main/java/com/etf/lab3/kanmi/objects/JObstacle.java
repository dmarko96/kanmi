package com.etf.lab3.kanmi.objects;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class JObstacle extends Group {
    private int type=0;
    public JObstacle(Point2D pos, int t){
        type=t;
        PhongMaterial bMat=new PhongMaterial(Color.ROSYBROWN);
        if(t==0){
            //L
            Box b1=new Box(2,10,2);
            Box b2=new Box(6,2,2);
            b1.setMaterial(bMat);
            b2.setMaterial(bMat);
            b2.getTransforms().add(new Translate(3,3.5,0));
            getChildren().addAll(b1,b2);
        }
        else{
            //P
            Box b1=new Box(2,10,2);
            b1.setMaterial(bMat);
            Box b2=new Box(10,2,2);
            b2.setMaterial(bMat);
            Box b3=new Box(2,10,2);
            b3.setMaterial(bMat);

            b2.getTransforms().add(new Translate(4,-5,0));
            b3.getTransforms().add(new Translate(8,0,0));
            getChildren().addAll(b1,b2,b3);
        }
        setTranslateX(pos.getX());
        setTranslateZ(pos.getY());
        setTranslateY(5.0);
    }
}
