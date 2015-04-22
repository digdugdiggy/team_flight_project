/* *****************************************************************************
* Programmer: Michael Truncale
* Course: CSCI 4388.01 - Senior Project
* Date: April 20, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.input.hardware;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.RandomAccessFile;
import java.net.URI;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 



public class DroneDriver {
    //Data from drone is sent to text file, this sets the path for the file reader
    //Path if fixed by grundle / install so should be Windows system independant
    String path = System.getProperty("user.dir");  
    String fullpathBattery = path + "\\droneHardware\\node_modules\\drone\\droneBatteryData.txt";  
    String fullpathAltitude = path + "\\droneHardware\\node_modules\\drone\\droneAltitudeData.txt";  
    String batteryData = "--";
    String altitudeData = "--";
   
    //File readers used to get drone data - additional ones can be built per data
    RandomAccessFile inputReaderBattery = new RandomAccessFile(fullpathBattery, "r");
    RandomAccessFile inputReaderAltitude = new RandomAccessFile(fullpathAltitude, "r");
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
        
        //Opens socket so commands can be sent to drone via Node.JS server
        try{
            Socket tcpClient = new Socket("localhost", port); 
            outputWriter = new OutputStreamWriter(tcpClient.getOutputStream());
        }
        catch(Exception e){
            System.out.println("Unable to establish connection");
            System.out.println("Make sure Node.JS server is started first");
            System.out.println("Press F12 to close connection and F11 to re-establish");
            System.out.println("     " + e + "\n");
        }
        
        //loadVideo();
    }
    
    
    //Sends command for drone to the Node.JS server as string, the node server
    //will take this string and further act on it to control drone
    public void sendCommand(String simCommand) throws IOException{
        try{
            outputWriter.write(simCommand);
            outputWriter.flush();
            //System.out.println("Command to Drone: " + simCommand);
        }
        catch(Exception e){
            System.out.println("Error sending command");
            System.out.println("Make sure Node.JS server is started first");
            System.out.println("Press F12 to reset connection and F11 to re-establish");
            System.out.println("     " + e + "\n");
        }
    }
    

    //Reads battery data from .txt file and returns value when called
    public String readBatteryData() throws IOException{
        String holder = "";
        
        try{
            holder = inputReaderBattery.readLine();
            if (holder != null){
                batteryData = holder;
                //System.out.println("Battery Charge: " + batteryData);
                return batteryData;
            }
        }
        catch (Exception e){
            System.out.println("Error reading battery data: ");
            System.out.println("     " + e + "\n");
        }
        return batteryData;
    }
    
    
    //Reads altitude data from .txt file and returns value when called
    public String readAltitudeData() throws IOException{
        String holder = "";
        
        try{
            holder = inputReaderAltitude.readLine();
            if (holder != null){
                altitudeData = holder;
                //System.out.println("Altitude: " + altitudeData);
                return altitudeData;
            }
        }
        catch (Exception e){
            System.out.println("Error reading altitude data: ");
            System.out.println("     " + e + "\n");
        }
        return altitudeData;
    }

        
    //NOT PLATFORM INDEPENDANT
    //Opens default browser and loads drone video - video is done on a webGL
    //canvas and also requires more Node.JS to be run on the webpage
    //Java Robot() then maximizes browser by keystroke
    public void loadVideo(){
        String path = System.getProperty("user.dir");  
        path = path.replaceAll("\\\\", "/");  
        path +=  "/droneHardware/node_modules/drone/DroneVideo.html";  
        path = "file:///" + path;

        try{
            Desktop.getDesktop().browse(new URI(path));
        }
        catch (Exception e){
            System.out.println("Error loading browser - video not supported: ");
            System.out.println("     " + e + "\n");
        }
        
        try{
            Robot robot = new Robot();
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_F11); 
            robot.keyRelease(KeyEvent.VK_F11);
        }
        catch (Exception e){
        }
    }
    
    
    //Closes browser under Windows system
    public void closeVideo(){
        try{
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        }
        catch(Exception e){
            System.out.println("Error closing browser: ");
            System.out.println("     " + e + "\n");
        }
    }
    
    
    //Closes Java socket and sends message to Node.JS to let it know to close
    public void stopServer() throws IOException{
        outputWriter.write("closeConnecton");
        outputWriter.flush();
        outputWriter.close();
        System.out.println("Connection to JS Server Closed");

        //Video closing can be automatically done here or via seperate command
        //as it is w/ this closeVideo() commented out
        //closeVideo();
    }
}


