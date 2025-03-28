package com.etf.lab3.kanmi.objects;

import com.etf.lab3.kanmi.Cannon;
import com.etf.lab3.kanmi.World;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
public class Projectile extends Sphere {
    private Point2D direction;
    private Point2D position;
    public static final double BULLET_SPEED=1.5;
    public Projectile(double x,double z,Point2D dir){
        super(Cannon.BARREL_WIDTH);
        this.setMaterial(new PhongMaterial(Color.ORANGERED));
        this.setTranslateX(x);
        this.setTranslateZ(z);
        position=new Point2D(x,z);
        direction=dir;
    }
    public boolean move(){
        this.setTranslateX( getTranslateX()+direction.getX()*BULLET_SPEED );
        this.setTranslateZ( getTranslateZ()+direction.getY()*BULLET_SPEED );
        double terrainWidth=World.GROUND_WIDTH/2+1;
        if( getTranslateX()> terrainWidth || getTranslateX()< -terrainWidth || getTranslateZ()>terrainWidth || getTranslateZ()<-terrainWidth){
            return true;
        }
        return false;
    }
}
