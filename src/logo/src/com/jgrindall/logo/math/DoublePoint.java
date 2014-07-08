/**
 * DoublePoint.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * To draw the logo accurately on screen we cannot round off to integers.
 * This class just stores x,y coordinates as doubles.
 */

package com.jgrindall.logo.math;
public class DoublePoint{
    private double x = 0;
    private double y = 0;

    public DoublePoint(double newx, double newy){
        x=newx;
        y=newy;
    }
    public void translate(double dx, double dy){
        x+=dx;
        y+=dy;
    }
    public void translate(DoublePoint d){
        x+=d.getX();
        y+=d.getY();
    }
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
    public void rotate(double theta){
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        double newx = cos*x - sin*y;
        double newy = sin*x + cos*y;

        x = newx;
        y = newy;
    }
    @Override
    public DoublePoint clone(){
        return new DoublePoint(x,y);
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}