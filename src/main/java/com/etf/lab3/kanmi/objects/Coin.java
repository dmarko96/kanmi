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
import javafx.util.Duration;

public class Coin extends AbstractCoin
{
    int coinType=0;
    public Coin(Point2D pos, int type)
    {
        super(pos);
        coinType=type;
        Cylinder outerCylinder = new Cylinder(4, 1);

        PhongMaterial outerPhongMaterial=new PhongMaterial();
        if(coinType==0) {
            outerPhongMaterial.setDiffuseColor(Color.GOLD);
        }
        else if(coinType==1){
            outerPhongMaterial.setDiffuseColor(Color.GREEN);
        }
        else{
            outerPhongMaterial.setDiffuseColor(Color.BLUE);
        }
        outerCylinder.setMaterial(outerPhongMaterial);
        outerCylinder.setRotate(90);

        Cylinder innerCylinder = new Cylinder(3, 1);
        innerCylinder.setMaterial(new PhongMaterial(Color.GOLDENROD));
        innerCylinder.setRotate(90);

        getChildren().addAll(outerCylinder, innerCylinder);
    }
    public int getPoints(){
        if(coinType==0)return 1;
        else if(coinType==1)return 3;
        else return 5;
    }
}
