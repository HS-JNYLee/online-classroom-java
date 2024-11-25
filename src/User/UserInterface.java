package User;

import javax.swing.*;
import java.io.IOException;

public interface UserInterface {

    // User Info
    String getId();

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

    void connectToClassRoom(String trAddr, int trPort) throws IOException;

    void connectToTeamRoom(String trAddr, int trPort) throws IOException;
}

