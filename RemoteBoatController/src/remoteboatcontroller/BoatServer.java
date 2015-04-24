/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteboatcontroller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import remoteboatcontroller.ClientThread;

/**
 *
 * @author alexander
 */
public class BoatServer  extends Thread {
   private ServerSocket mServerSocket;
   private Map  mThreadMap;
   private boolean running;
   private int mServerPort;
   public BoatServer(int port) throws IOException {
      mServerPort = port;
      mServerSocket = new ServerSocket(mServerPort);
      mServerSocket.setSoTimeout(10000);
      mThreadMap = new HashMap<ClientThread,UUID>();
      running = true;
   }
   @Override
    public void run(){
        while(running){
            Socket clientSocket = null;
            try {
                clientSocket = this.mServerSocket.accept();
                
                
            } catch (IOException e) {
                if(!running) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
        }
        System.out.println("Server Stopped.") ;
    }
};
