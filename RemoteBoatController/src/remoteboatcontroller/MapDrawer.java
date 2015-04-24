/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteboatcontroller;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import remoteboatcontroller.ClientThread;

/**
 *
 * @author alexander
 */
public class MapDrawer {
    private int mImageWidth;
    private int mImageHeight;
    private float mWindSpeed;
    private byte mMap[];
    
    public MapDrawer(int width, int height, float windSpeed) {
        mImageHeight = height;
        mImageWidth = width;
        mWindSpeed = windSpeed;
        mMap = new byte[height*width];
        Arrays.fill(mMap, (byte)0); 
    }
    public int[] computeScenery(HashMap boats) {
        ClientThread tmpClientThread;
        float xcoord,ycoord;
        float[] xCoords = new float[boats.size()];
        float[] yCoords = new float[boats.size()];
        int i = 0;
        int[] output = new int [2*boats.size()];
        for (Iterator it = boats.entrySet().iterator(); it.hasNext();) {
            Map.Entry<ClientThread,UUID> entry = (Map.Entry<ClientThread,UUID>) it.next();
            tmpClientThread = entry.getKey();
            xcoord = (float) (tmpClientThread.getX()+(tmpClientThread.getAngle()/abs(tmpClientThread.getAngle()))*
                    mWindSpeed*cos(tmpClientThread.getAngle())*cos(90-abs(tmpClientThread.getAngle())));
            ycoord = (float) (tmpClientThread.getY()+
                    mWindSpeed*cos(tmpClientThread.getAngle())*sin(90-abs(tmpClientThread.getAngle())));
            if(xcoord<0 || xcoord > mImageWidth){
              xCoords[i] =  tmpClientThread.getX(); 
              tmpClientThread.setAngle(0);
            } else{
                xCoords[i] = xcoord;
            }
            if(ycoord > mImageHeight){
              yCoords[i] =  mImageHeight; 
            } else{
                yCoords[i] = ycoord;
            }
            for(int k = 0; k < i; k++ ){
               if(mMap[((int)yCoords[k]*mImageWidth+(int)xCoords[k])]==(byte)255){
                    yCoords[i] = tmpClientThread.getY();
                    xCoords[i] = tmpClientThread.getX();
                    break;
                }
           }
            for(int k = 0; k < i; k++ ){
                if(((int)xCoords[k]==(int)xCoords[i])&&((int)yCoords[k]==(int)yCoords[i])){
                    yCoords[i] = tmpClientThread.getY();
                    xCoords[i] = tmpClientThread.getX();
                    break;
                }
            }
            i++;
        }
     
        i = 0;
          for (Iterator it = boats.entrySet().iterator(); it.hasNext();) {
               Map.Entry<ClientThread,UUID> entry = (Map.Entry<ClientThread,UUID>) it.next();
               tmpClientThread = entry.getKey();
               mMap[((int)tmpClientThread.getY()*mImageWidth+(int)tmpClientThread.getX())] = 0;
               tmpClientThread.setX(xCoords[i]);
               tmpClientThread.setY(yCoords[i]);
               mMap[((int)yCoords[i]*mImageWidth+(int)xCoords[i])] = (byte)128;
               output[2*i] = (int)xCoords[i];
               output[2*i+1] = (int)yCoords[i];
               i++;
           }
          return output;
    }
    
}
