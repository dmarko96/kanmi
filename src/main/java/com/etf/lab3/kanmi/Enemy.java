package com.etf.lab3.kanmi;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Enemy extends Group {

    private Point2D direction;
    private static final double speed=0.3;
    public Enemy(Point2D pos){
        this(pos,Color.DARKSLATEGREY);
    }
    public Enemy(Point2D pos,Color color){
        PhongMaterial enemyMaterial=new PhongMaterial(color);
        Cylinder enemyBody=new Cylinder(4,10);
        enemyBody.setMaterial(enemyMaterial);
        Sphere enemyHead=new Sphere(4);
        enemyHead.setMaterial(enemyMaterial );
        enemyHead.setTranslateY(0);
        enemyBody.setTranslateY(5);


        getChildren().addAll(enemyBody,enemyHead);

        float tSize=2;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 1; i++) {
//                Cylinder spike=new Cylinder(0.3,15);
                //create tetrahedron
                float[] points = new float[]{
                        0, tSize*4, 0,   // Vertex A (top)
                        -tSize, 0, -tSize, // Vertex B (bottom left)
                        tSize, 0, -tSize,  // Vertex C (bottom right)
                        0, 0, tSize    // Vertex D (bottom center)
                };
                // Define the faces of the tetrahedron
                int[] faces = new int[]{
                        0, 0, 1, 0, 2, 0, // Face A-B-C
                        0, 0, 2, 0, 3, 0, // Face A-C-D
                        0, 0, 3, 0, 1, 0, // Face A-D-B
                        1, 0, 2, 0, 3, 0  // Face B-C-D
                };
                // Create the TriangleMesh
                TriangleMesh spikeMesh = new TriangleMesh();
                spikeMesh.getPoints().addAll(points);
                spikeMesh.getTexCoords().addAll(0, 0); // No texture
                spikeMesh.getFaces().addAll(faces);
                // Create the MeshView
                MeshView spike = new MeshView(spikeMesh);

                //
                spike.setMaterial(enemyMaterial);
                spike.setTranslateY(0);
//                spike.getTransforms().addAll(new Rotate(135,Rotate.X_AXIS));
//                Rotate rotateX=new Rotate(135,Rotate.X_AXIS);
//                Rotate rotateZ=new Rotate(90*j,Rotate.Z_AXIS);
//                spike.getTransforms().addAll(rotateX,rotateZ);
                switch (j){
                    case 0:
                        spike.getTransforms().add(new Rotate(135,Rotate.X_AXIS));
                        break;
                    case 1:
                        spike.getTransforms().add(new Rotate(-135,Rotate.X_AXIS));
                        break;
                    case 2:
                        spike.getTransforms().add(new Rotate(135,Rotate.Z_AXIS));
                        break;
                    case 3:
                        spike.getTransforms().add(new Rotate(-135,Rotate.Z_AXIS));
                        break;
                    case 4:
                        spike.getTransforms().add(new Rotate(180,Rotate.X_AXIS));
                }
                getChildren().addAll(spike);
            }
        }
        setTranslateX(pos.getX());
        setTranslateZ(pos.getY());
        setTranslateY(5);

        randomizeDirection();
    }

    public void randomizeDirection(){
        direction=new Point2D(Math.random()*2-1.0,Math.random()*2-1.0);
        direction=direction.normalize();
    }
    public void randomizeDirection(Point2D player){
        return;
    }
    public double getSpeed(){
        return speed;
    }
    public Point2D getDirection(){
        return direction;
    }
    public void setDirection(Point2D dir){
        direction=dir;
    }
}
