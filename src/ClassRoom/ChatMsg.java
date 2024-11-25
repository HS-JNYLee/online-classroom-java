package ClassRoom;

import User.User;

import javax.swing.*;
import java.io.Serializable;

public class ChatMsg implements Serializable {
    public final static int MODE_CONNECT = 0x1;
    public final static int MODE_LOGIN = 0x2;
    public final static int MODE_LOGOUT = 0x3;
    public final static int MODE_TX_STRING = 0x10;
    public final static int MODE_TX_FILE = 0x20;
    public final static int MODE_TX_IMAGE = 0x40;

    String userID;
    int mode;
    String message;
    ImageIcon image;
    long size;
    String uId;
    String uName;
    String uType;

    public ChatMsg(String userID, int code, String message, ImageIcon image, long size, String uId, String uName, String uType) {
        this.userID = userID;
        this.mode = code;
        this.message = message;
        this.image = image;
        this.size = size;
        this.uId = uId;
        this.uName = uName;
        this.uType = uType;
    }

    public ChatMsg(String userID, int code) {
        this(userID, code, null, null, 0, null, null, null);
    }

    public ChatMsg(String userID, int code, String message) {
        this(userID, code, message, null, 0, null, null, null);
    }

    public ChatMsg(String userID, int code, ImageIcon image) {
        this(userID, code, null, image, 0, null, null, null);
    }

    public ChatMsg(String userID, int code, String message, ImageIcon image) {
        this(userID, code, message, image, 0, null, null, null);
    }


    public ChatMsg(String userID, int code, String filename, long size) {
        this(userID, code, filename, null, size, null, null, null);
    }

    public ChatMsg(String userID, int code, String filename, ImageIcon image, String uId, String uName, String uType) {
        this(userID, code, filename, image, 0, uId, uName, uType);
    }
}

