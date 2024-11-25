package User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class User implements UserInterface {
    protected String id;
    protected String name;
    protected Roles role;
    protected ImageIcon profileImage;
    protected String classRoomAddr;
    protected int classRoomPort;
    protected String teamRoomAddr; // groupId
    protected int teamRoomPort;
    protected Socket socket;

    public String getId() {
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

    public void setId(String id) {
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

    public void setTeamRoomAddr(String teamRoomAddr) {
        this.teamRoomAddr = teamRoomAddr;
    }

    public void sendMic() {

    }

    public void sendMessage() {

    }

    public void receiveMessage() {

    }

    public void receiveSound() {
        
    }

    public void connectToClassRoom(String crAddr, int crPort) throws IOException {

    }

    public void connectToTeamRoom(String trAddr, int trPort) throws IOException {

    }

}
