package com.etf.lab3.kanmi;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class World extends Group
{
    public static final double GROUND_WIDTH = 600;
    public static final double GROUND_LENGTH = 600;
    public static final double WALL_HEIGHT = 20;

    private static final Color DEFAULT_GROUND_COLOR = Color.GRAY;
    private static final PhongMaterial GROUND_MATERIAL = new PhongMaterial(DEFAULT_GROUND_COLOR);
    private static final Color DEFAULT_WALL_COLOR = Color.FIREBRICK;
    private static final PhongMaterial WALL_MATERIAL = new PhongMaterial(DEFAULT_WALL_COLOR);

    public World()
    {
        PhongMaterial materialConcrete=new PhongMaterial();
        materialConcrete.setDiffuseMap(new Image(getClass().getResourceAsStream("concrete.jpg")));
        PhongMaterial materialBrick=new PhongMaterial();
        materialBrick.setDiffuseMap(new Image(getClass().getResourceAsStream("brick.jpg")));

        Box ground = new Box(GROUND_WIDTH, 1, GROUND_LENGTH);
        ground.setMaterial(materialConcrete);
        ground.setTranslateY(WALL_HEIGHT / 2);

        Box lWall = new Box(1, WALL_HEIGHT, GROUND_LENGTH);
        lWall.setMaterial(materialBrick);
        lWall.setTranslateX(-GROUND_WIDTH / 2);
        Box rWall = new Box(1, WALL_HEIGHT, GROUND_LENGTH);
        rWall.setMaterial(materialBrick);
        rWall.setTranslateX(GROUND_WIDTH / 2);
        Box fWall = new Box(GROUND_WIDTH, WALL_HEIGHT, 1);
        fWall.setMaterial(materialBrick);
        fWall.setTranslateZ(GROUND_LENGTH / 2);
        Box bWall = new Box(GROUND_WIDTH, WALL_HEIGHT, 1);
        bWall.setMaterial(materialBrick);
        bWall.setTranslateZ(-GROUND_LENGTH / 2);

        this.getChildren().addAll(ground, lWall, rWall, fWall, bWall);
    }
}
