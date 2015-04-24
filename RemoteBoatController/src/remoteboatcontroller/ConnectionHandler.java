/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteboatcontroller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class ConnectionHandler extends Thread{
    private Socket mSocket;
    private  DataOutputStream mDataOut;
    private DataInputStream mDataIn;
    private byte mMap;
    private Map  mThreadMap;
   public ConnectionHandler(Socket sock, byte map, Map thrMap){
       mSocket = sock;
       mThreadMap = thrMap;
       mMap = map;
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
}
