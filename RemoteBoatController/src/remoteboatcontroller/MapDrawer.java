/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteboatcontroller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author alexander
 */
public class MapDrawer {
    private String mWaterTextureFile;
    private String mBoatTextureFile;
    private String mGroundTextureFile;
    private BufferedImage mCurretScenery;
    private BufferedImage mWaterTexture;
    private BufferedImage mBoatTexture;
    private BufferedImage mGroundTexture;
    private int mWaterTextureWidth;
    private int mWaterTextureHeight;
    private int mBoatTextureWidth;
    private int mBoatTextureHeight;
    private int mGroundTextureWidth;
    private int mGroundTextureHeight;
    
    public MapDrawer(String waterTexture, String boatTexture, String groundTexture,
            int width, int height) {
        this.setPathToWaterTexture(waterTexture);
        this.setPathToBoatTexture(boatTexture);
        this.setPathToGroundTexture(groundTexture);
        mCurretScenery = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    public void setPathToWaterTexture(String path) {
        mWaterTextureFile = path;
        try {
            mWaterTexture = ImageIO.read(new File(mWaterTextureFile));
        } catch (IOException e) {}
    }
    
    public void setPathToBoatTexture(String path ) {
        mBoatTextureFile = path;
         try {
            mBoatTexture = ImageIO.read(new File(mBoatTextureFile));
        } catch (IOException e) {}
    }
    
    public void setPathToGroundTexture(String path) {
        mGroundTextureFile = path;
         try {
            mGroundTexture = ImageIO.read(new File(mGroundTextureFile));
        } catch (IOException e) {}
    }
    /**
     * prints the contents of buff2 on buff1 with the given opaque value.
     *
     * @param buff1 buffer
     * @param buff2 buffer
     * @param opaque how opaque the second buffer should be drawn
     * @param x x position where the second buffer should be drawn
     * @param y y position where the second buffer should be drawn
     */
    private void addImage(BufferedImage buff1, BufferedImage buff2,
            float opaque, int x, int y) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(buff2, x, y, null);
        g2d.dispose();
    }
    public BufferedImage drawScenery() {
        
        
        return mCurretScenery;
    }
    
    
}
