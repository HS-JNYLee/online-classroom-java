package User;

import java.io.IOException;
import java.net.Socket;

public class Student extends User{

    private int teamNum;

    Student(){}

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
