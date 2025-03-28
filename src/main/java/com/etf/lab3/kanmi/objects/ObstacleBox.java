package com.etf.lab3.kanmi.objects;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class ObstacleBox extends Group {
    public ObstacleBox(Point2D pos){

        Box box=new Box(15,30,15);
        PhongMaterial phongMaterial=new PhongMaterial();
        phongMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/com/etf/lab3/kanmi/brick.jpg")));
        box.setMaterial(phongMaterial);
        setTranslateX(pos.getX());
        setTranslateZ(pos.getY());
        setTranslateY(5.0);
        getChildren().addAll(box);

    }
}
