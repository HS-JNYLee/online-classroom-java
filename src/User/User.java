package User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class User implements UserInterface {
    protected int id;
    protected String name;
    protected Roles role;
    protected ImageIcon profileImage;
    protected String classRoomAddr;
    protected int classRoomPort;
    protected String teamRoomAddr;
    protected int teamRoomPort;
    protected Socket socket;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Roles getRole() {
        return this.role;
    }

    public ImageIcon getProfileImage() {
        return this.profileImage;
    }

    public String getClassRoomAddr() {
        return this.classRoomAddr;
    }

    public int getClassRoomPort() {
        return this.classRoomPort;
    }

    public String getTeamRoomAddr() {
        return this.teamRoomAddr;
    }

    public int getTeamRoomPort() {
        return this.teamRoomPort;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setProfileImage(ImageIcon profileImage) {
        this.profileImage = profileImage;
    }

    public void sendMic() {

    }

    public void receiveMic() {

    }

    public void sendMessage() {

    }

    public void receiveMessage() {

    }

    public void sendSound() {

    }

    public void receiveSound() {
        
    }

    public void connectToClassRoom(String crAddr, int crPort) throws IOException {

    }

    public void connectToTeamRoom(String trAddr, int trPort) throws IOException {

    }

}
