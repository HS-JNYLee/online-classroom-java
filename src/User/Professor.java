package User;

import java.io.IOException;
import java.net.Socket;

public class Professor extends User{

    Professor(){}

    @Override
    public void sendMic() {

    }

    @Override
    public void receiveMic() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void receiveMessage() {

    }

    @Override
    public void sendSound() {

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
