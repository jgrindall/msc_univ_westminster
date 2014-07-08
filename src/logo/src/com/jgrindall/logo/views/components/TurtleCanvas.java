/**
 * TurtleCanvas.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * swing component for drawing logo on screen.
 * http://leepoint.net/notes-java/GUI-lowlevel/graphics/43buffimage.html
 */
package com.jgrindall.logo.views.components;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.jgrindall.logo.comms.*;
import java.awt.event.*;
import com.jgrindall.logo.math.DoublePoint;

public class TurtleCanvas extends JPanel{

    // size of the available canvas.
    public static final int MAX_WIDTH = 2000;
    public static final int MAX_HEIGHT = 1000;

    // the middle/origin
    private DoublePoint startPoint= new DoublePoint(MAX_WIDTH/2,MAX_HEIGHT/2);

    // a grid of feint lines to help judge distances
    public static final int GRID_SIZE = 100;

    // heading of the turtle
    private double rot=0;
    // position of the turtle
    private DoublePoint currentPoint;

    // for drawing the turtle (a green triangle)
    public static final int CROSS_SIZE=3;
    public static final int TRI_HEIGHT=14;
    public static final int TRI_DX=8;
    public static final int TRI_DY=10;

    //colours:
    
    //light and dark green are for the turtle.
    public static final Color LIGHT_GREEN = new Color(102,153,102,60);
    public static final Color DARK_GREEN = new Color(102,153,102,100);
    // light and med red are for the lines.
    public static final Color LIGHT_RED = new Color(255,0,0,8);
    public static final Color MED_RED = new Color(255,0,0,30);
    // dark red is for the cross.
    public static final Color DARK_RED = Color.RED;
    public static final Color LINE_COLOUR = Color.BLACK;

    /* all gfx are written into a buffered image rather than just into the graphics of the component.
    // this way when the canvas is resized we can refresh the display and maintain all gfx rather than losing
     * those that stick out of the available canvas size.
     * 
     */

    private BufferedImage myBufferedImage;

    
    public TurtleCanvas(){
        super();
        reset();
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.addComponentListener(new ComponentListener(){
            @Override
            public void componentResized(ComponentEvent e){
                onComponentResized();
            }
            public void componentHidden(ComponentEvent e){

            }
            public void componentMoved(ComponentEvent e){

            }
            public void componentShown(ComponentEvent e){

            }
        });
    }
    public void reset(){
        myBufferedImage=(BufferedImage)this.createImage(MAX_WIDTH,MAX_HEIGHT);
        // new buffer for gfx
        currentPoint = startPoint.clone();
        // move to the origin.
        rot=0;
        // heading north
        repaint();
    }
    private void onComponentResized(){
        if(myBufferedImage==null){
            // this happens right at the start, build a new buferredImage
            reset();
        }
        int widthNow = this.getWidth();
        int heightNow = this.getHeight();

        int widthOld = myBufferedImage.getWidth();
        int heightOld = myBufferedImage.getHeight();

        int maxWidth = Math.max(widthNow,widthOld);
        int maxHeight =  Math.max(heightNow,heightOld);

        // maximum of the old and new is needed to get all the graphics we need
        //(trying to improve speed by not copying all pixels)

        BufferedImage replacementBI =(BufferedImage)this.createImage(MAX_WIDTH,MAX_HEIGHT);
        Graphics g = replacementBI.getGraphics();
        g.setClip(0,0,maxWidth,maxHeight );
        g.drawImage(myBufferedImage,0,0,this);
        myBufferedImage = replacementBI;
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g){

        //TODO:  this is probably inefficient drawing everything all the time!
        
        super.paintComponent(g);
        g.drawImage(myBufferedImage,0,0,this);
        makeGrid(g);
        makeCross(g);
        makeTri(g);
    }
    public void draw(LogoFdObject lo){
        drawForward(lo.getData());
        this.repaint();
    }
    public void draw(LogoRtObject lo){
        drawRight(lo.getData());
        this.repaint();
    }
    private void drawForward(double d){
        /* in normal trig, rot=0 would be along the +ve x axis,
        // but I use north as zero.

        // rot = 0          ->      north
        // rot = pi/2       ->      east
        // rot = pi         ->      south
        // rot = 3pi/2      ->      west

        // -rot because trig measures antiCW and I am using CW
        // + pi/2 to correct the above directions
        */
        double movex = (d * Math.cos(-rot+Math.PI/2));
        double movey = (d * Math.sin(-rot+Math.PI/2));

        DoublePoint newp = new DoublePoint( currentPoint.getX()+movex,  currentPoint.getY()-movey  ) ;
        Graphics2D gc = myBufferedImage.createGraphics();
        gc.setColor(TurtleCanvas.LINE_COLOUR);
        gc.drawLine(  (int)currentPoint.getX(),(int)currentPoint.getY(), (int)newp.getX(), (int)newp.getY()  );
        currentPoint=new DoublePoint(newp.getX(), newp.getY());
    }
    private void drawRight(double d){
         // d is what the student has typed so it is in degrees
         // rot is in radians
         rot+=d*Math.PI/180;
    }
    private void makeCross(Graphics g){
        // draw the origin
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(TurtleCanvas.DARK_RED);
        g2d.drawLine(MAX_WIDTH/2-CROSS_SIZE, MAX_HEIGHT/2-CROSS_SIZE, MAX_WIDTH/2+CROSS_SIZE, MAX_HEIGHT/2+CROSS_SIZE);
        g2d.drawLine(MAX_WIDTH/2-CROSS_SIZE, MAX_HEIGHT/2+CROSS_SIZE, MAX_WIDTH/2+CROSS_SIZE, MAX_HEIGHT/2-CROSS_SIZE);
    }
    private void makeGrid(Graphics g){
        //draw the grid
        int w = TurtleCanvas.MAX_WIDTH;
        int h = TurtleCanvas.MAX_HEIGHT;
        
        int maxX = (int)Math.floor(w/GRID_SIZE);
        for(int x = -maxX; x<=maxX; x++){
            if(x==0){
                g.setColor(TurtleCanvas.MED_RED);
            }
            else{
                g.setColor(TurtleCanvas.LIGHT_RED);
            }
             g.drawLine(w/2  +  x*GRID_SIZE , 0, w/2  +  x*GRID_SIZE, h);
        }
        int maxY = (int)Math.floor(h/GRID_SIZE);
        for(int y = -maxY; y<=maxY; y++){
             if(y==0){
                g.setColor(TurtleCanvas.MED_RED);
            }
            else{
                g.setColor(TurtleCanvas.LIGHT_RED);
            }
             g.drawLine(0,  h/2  +  y*GRID_SIZE , w,  h/2  +  y*GRID_SIZE);
        }
        
    }
    private void makeTri(Graphics g){
        // draw our turtle

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(TurtleCanvas.LIGHT_GREEN);
        
        DoublePoint v0 = new DoublePoint(0,-TRI_HEIGHT);
        DoublePoint v1 = new DoublePoint(TRI_DX,TRI_DY);
        DoublePoint v2 = new DoublePoint(-TRI_DX,TRI_DY);

        v0.rotate(rot);
        v1.rotate(rot);
        v2.rotate(rot);
      
        v0.translate(currentPoint);
        v1.translate(currentPoint);
        v2.translate(currentPoint);

        Polygon polyTri = new Polygon();

        polyTri.addPoint((int)v0.getX(), (int)v0.getY());
        polyTri.addPoint((int)v1.getX(), (int)v1.getY());
        polyTri.addPoint((int)v2.getX(), (int)v2.getY());

        g2d.fillPolygon(polyTri);
        
        g2d.setColor(TurtleCanvas.DARK_GREEN);
        g2d.drawLine((int)v0.getX(), (int)v0.getY(), (int)v1.getX(), (int)v1.getY());
        g2d.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
        g2d.drawLine((int)v2.getX(), (int)v2.getY(), (int)v0.getX(), (int)v0.getY());
        
    }
}