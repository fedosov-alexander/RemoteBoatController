/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteboatcontroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class ClientThread extends Thread{
    private Boolean running;
    private Socket mSocket;
    private  DataOutputStream mDataOut;
    private DataInputStream mDataIn;
    private float mCurrentX;
    private float mCurrentY;
    private float mCurrentAngle;
    public ClientThread(Socket sock, float x, float y, float angle){
        mSocket = sock;
        mCurrentX = x;
        mCurrentY = y;
        mCurrentAngle = angle;
        try { 
            mDataIn=new DataInputStream(mSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {        
            mDataOut=new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setRunning(Boolean r){
        running = r;
    }
    public float getX(){
    return mCurrentX;
    }
    public float getY(){
    return mCurrentY;
    }
    public float getAngle(){
    return mCurrentAngle;
    }
    public void setX(float x){
        mCurrentX = x;
        
    }
    public void setY(float y){
        mCurrentY = y;
    }
      public void changeAngle(float ang){
          if(ang+mCurrentAngle>=360.0){
           mCurrentAngle+=ang-360;
           return;
          }
          if(ang+mCurrentAngle<=0.0){
             mCurrentAngle+=360-Math.abs(ang);
              return;
          }
          mCurrentAngle+= ang;
    }
    public void sendCoordinates(int[] coords){
        try {
            mDataOut.writeByte(128);//first byte is the signal that corresponds to sending ship coordinates (this signal is 128)
            mDataOut.writeInt(coords.length+2);
            for(int i: coords)
            {
            // write int to data output stream
            mDataOut.writeInt(i);
            }
            mDataOut.writeInt((int)this.mCurrentX);
            mDataOut.writeInt((int)this.mCurrentY);
            mDataOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendMap(byte[] map, int width, int height){
        try {
            mDataOut.writeByte(255);//first byte is the signal that corresponds to sending map(this signal is 255)
            mDataOut.writeInt(width);
            mDataOut.writeInt(height);
            mDataOut.write(map);
            mDataOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void sendDisconnectionMessage(){
        try {
            mDataOut.writeByte(0);//first byte is the signal that corresponds to shutting down the server (this signal is 0)
            mDataOut.writeBytes("Server shut down");
            mDataOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @Override
    public void run(){
        while(running){
            try {
                mCurrentAngle = mDataIn.readFloat();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
}
