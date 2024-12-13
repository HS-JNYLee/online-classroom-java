package User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Student extends User{

    private int teamNum;

    public Student(){}

    public Student(String id, String name, ImageIcon profileImage){
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.role = Roles.STUDENT;
    }

    public Student(String id, String name, ImageIcon profileImage, Roles roles){
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.role = roles;
    }

    public Student(ImageIcon profileImage){
        this.profileImage = profileImage;
    }

    public int getTeamNum(){
        return this.teamNum;
    }

    public void setTeamNum(int num){
        this.teamNum = num;
    }

    @Override
    public void sendMic() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void receiveMessage() {

    }

    @Override
    public void receiveSound() {

    }

    public void sendEmojiIcon(){

    }

    @Override
    public void connectToClassRoom(String crAddr, int crPort) throws IOException {
        this.socket = new Socket(crAddr, crPort);
    }

    @Override
    public void connectToTeamRoom(String trAddr, int trPort) throws IOException {
        this.socket = new Socket(trAddr, trPort);
    }
}
