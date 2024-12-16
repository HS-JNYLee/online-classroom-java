package User;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Professor extends User{

    public Professor(){}

    public Professor(String id, String name, ImageIcon profileImage){
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.role = Roles.PROFESSOR;
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

    public void receiveEmojiIcon(){

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
