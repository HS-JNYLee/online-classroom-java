package User;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
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

    public User(){}

    public User(String id, ImageIcon profileImage, String name) {
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
    }

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

    static public String roleToString(Roles roles){

        if(roles == Roles.STUDENT){
            return "학생";
        }else if(roles == Roles.PROFESSOR){
            return "교수";
        } else if (roles == Roles.TEAM_LEADER) {
            return "팀장";
        } else if (roles== Roles.TEAM_MEMBER) {
            return "팀원";
        }else return "역할이 할당 안됨";
    }

    static public Roles stringToRole(String target){
        Roles roles = Roles.PROFESSOR;

        if(target.equals("학생")){
            roles = Roles.STUDENT;
        } else if (target.equals("교수")) {
            roles = Roles.PROFESSOR;
        } else if (target.equals("팀장")) {
            roles = Roles.TEAM_LEADER;
        } else if (target.equals("팀원")) {
            roles = Roles.TEAM_MEMBER;
        }

        return roles;
    }

}
