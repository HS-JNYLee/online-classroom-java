package User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public interface UserInterface {

    // User Info
    int getId();
    String getName();
    Roles getRole();
    ImageIcon getProfileImage();

    // Room Info
    String getClassRoomAddr();
    int getClassRoomPort();
    String getTeamRoomAddr();
    int getTeamRoomPort();

    void sendMic();
    void receiveMic();
    void sendMessage();
    void receiveMessage();
    void sendSound();
    void receiveSound();
    void connectToClassRoom(String trAddr,int trPort) throws IOException;
    void connectToTeamRoom(String trAddr,int trPort) throws IOException;
}

abstract class User implements UserInterface{
    protected int id;
    protected String name;
    protected Roles role;
    protected ImageIcon profileImage;
    protected String classRoomAddr;
    protected int classRoomPort;
    protected String teamRoomAddr;
    protected int teamRoomPort;
    protected Socket socket;

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public Roles getRole(){
        return this.role;
    }

    public ImageIcon getProfileImage(){
        return this.profileImage;
    }

    public String getClassRoomAddr(){
        return this.classRoomAddr;
    }

    public int getClassRoomPort(){
        return this.classRoomPort;
    }

    public String getTeamRoomAddr(){
        return this.teamRoomAddr;
    }

    public int getTeamRoomPort(){
        return this.teamRoomPort;
    }

    public abstract void sendMic();
    public abstract void receiveMic();
    public abstract void sendMessage();
    public abstract void receiveMessage();
    public abstract void sendSound();
    public abstract void receiveSound();
    public abstract void connectToClassRoom(String crAddr,int crPort) throws IOException;
    public abstract void connectToTeamRoom(String trAddr,int trPort) throws IOException;

}