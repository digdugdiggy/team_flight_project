package edu.uhcl.team_drone.input.hardware;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DroneDriver {
    OutputStreamWriter outputWriter;

    
    public DroneDriver() throws IOException {
        int maxSleep = 1000;            //1000 = 1 second
        int sleepCount = 0;
        int secondCount = (maxSleep / 1000);
        int port = 6500;


        try{
            System.out.println("Starting JS Server...");
            System.out.println("Loading Command Sender in " + secondCount +" Second(s)...");
            
            //Starts command prompt minimized or not (game mode vs debugging)
            Runtime.getRuntime().exec("cmd /c start /min startServer.bat");
            //Runtime.getRuntime().exec("cmd /c start startServer.bat");
            
            while (sleepCount < maxSleep){
                Thread.sleep(1000);
                sleepCount = sleepCount + 1000;
                System.out.println("Waiting, " + secondCount + " Second(s)...");
                secondCount--;
            }
        }
        catch(Exception e){
            System.out.println("Unable to start server...");
        }
        
        try{
            Socket tcpClient = new Socket("localhost", port); 
            outputWriter = new OutputStreamWriter(tcpClient.getOutputStream());
        }
        catch(Exception e){
            System.out.println("Unable to establish connection...");
            System.out.println("Make sure JS server is started first");
        }
    }
    
    public void sendCommand(String simCommand) throws IOException{
        try{
            outputWriter.write(simCommand);
            outputWriter.flush();
            System.out.println("Command to Drone: " + simCommand);
        }
        catch(Exception e){
            System.out.println("Error sending command: ");
            System.out.println("     " + e);
        }
    }
    
    public void stopServer() throws IOException{
        outputWriter.write("closeConnecton");
        outputWriter.flush();
        outputWriter.close();
        System.out.println("Connection to JS Server Closed...");
    }
}


