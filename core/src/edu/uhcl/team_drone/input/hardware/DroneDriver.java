/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: MICHAEL TRUNCALE
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class handles node.js and interfacing with the hardware drone.
*
 * * * * * * * * * * * * * * * * * */
package edu.uhcl.team_drone.input.hardware;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.RandomAccessFile;
import java.net.URI;



public class DroneDriver {
    String path = System.getProperty("user.dir");  
    String fullpath = path + "\\droneHardware\\node_modules\\drone\\droneBatteryData.txt";  
    String batteryData = "Unknown";
   
    RandomAccessFile inputReader = new RandomAccessFile(fullpath, "r");
    OutputStreamWriter outputWriter;	
						
    
    public DroneDriver() throws IOException {
        int maxSleep = 1000;                   //1000 = 1 second
        int sleepCount = 0;
        int secondCount = (maxSleep / 1000);
        int port = 6500;


        try{
            System.out.println("Starting Drone Node.JS Server");
            System.out.println("Loading Command Sender in " + secondCount +" Second(s)");
            
            //Starts command prompt minimized or not (game mode vs debugging)
            Runtime.getRuntime().exec("cmd /c start /min startServer.bat");
            //Runtime.getRuntime().exec("cmd /c start startServer.bat");
            
            //Delays server start to address potential latency problems
            while (sleepCount < maxSleep){
                Thread.sleep(1000);
                sleepCount = sleepCount + 1000;
                System.out.println("Waiting, " + secondCount + " Second(s)...");
                secondCount--;
            }
            System.out.println("");
        }
        catch(Exception e){
            System.out.println("Unable to start server");
            System.out.println("     " + e);
        }
        
        try{
            Socket tcpClient = new Socket("localhost", port); 
            outputWriter = new OutputStreamWriter(tcpClient.getOutputStream());
        }
        catch(Exception e){
            System.out.println("Unable to establish connection");
            System.out.println("Make sure node.JS server is started first");
            System.out.println("Press F12 to close connection and F11 to re-establish");
            System.out.println("     " + e + "\n");
        }
        
        loadVideo();
    }
    
    
    public void sendCommand(String simCommand) throws IOException{
        try{
            outputWriter.write(simCommand);
            outputWriter.flush();
            System.out.println("Command to Drone: " + simCommand);
        }
        catch(Exception e){
            System.out.println("Error sending command");
            System.out.println("Make sure node.JS server is started first");
            System.out.println("Press F12 to reset connection and F11 to re-establish");
            System.out.println("     " + e + "\n");
        }
    }
    
    
    public void readDroneData() throws IOException{
        String holder = "";
        
        try{
            holder = inputReader.readLine();
            if (holder != null){
                batteryData = holder;
                System.out.println("Battery Charge: " + batteryData);
            }
        }
        catch (Exception e){
            System.out.println("Error reading drone data: ");
            System.out.println("     " + e + "\n");
        }
    }

        
    public void loadVideo(){
        String path = System.getProperty("user.dir");  
        path = path.replaceAll("\\\\", "/");  
        path +=  "/droneHardware/node_modules/drone/DroneVideo.html";  
        path = "file:///" + path;

        try{
            Desktop.getDesktop().browse(new URI(path));
        }
        catch (Exception e){
        }
    }
    
    
    public void stopServer() throws IOException{
        outputWriter.write("closeConnecton");
        outputWriter.flush();
        outputWriter.close();
        System.out.println("Connection to JS Server Closed");
    }
}


