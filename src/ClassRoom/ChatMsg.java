package ClassRoom;

import User.Roles;

import javax.swing.*;
import java.io.Serializable;

public class ChatMsg implements Serializable {
    public final static int MODE_CONNECT = 0x1;
    public final static int MODE_LOGIN = 0x2;
    public final static int MODE_LOGOUT = 0x3;
    public final static int MODE_TX_STRING = 0x10;
    public final static int MODE_TX_FILE = 0x20;
    public final static int MODE_TX_IMAGE = 0x40;
    public final static int MODE_TX_ACCESS = 0x50;
    public final static int MODE_TX_DENIED = 0x51;
    public final static int MODE_USER_INFO = 0x52;

    String userID;
    int mode;
    String message;
    ImageIcon image;
    long size;
    String uName;
    String uType;

    public ChatMsg(String userID, String userName, ImageIcon image, String uType ,int code, String message){
        this.userID = userID;
        this.uName = userName;
        this.image = image;
        this.uType = uType;
        this.mode = code;
        this.message = message;
    }

    public ChatMsg(String userID, int code, String message, ImageIcon image, long size, String uName, String uType) {
        this.userID = userID;
        this.mode = code;
        this.message = message;
        this.image = image;
        this.size = size;
        this.uName = uName;
        this.uType = uType;
    }

    public ChatMsg(String userID, int code) {
        this(userID, code, null, null, 0, null, null);
    }

    public ChatMsg(String userID, int code, String message) {
        this(userID, code, message, null, 0, null, null);
    }

    public ChatMsg(String userID, int code, ImageIcon image) {
        this(userID, code, null, image, 0, null, null);
    }

    public ChatMsg(String userID, int code, String message, ImageIcon image) {
        this(userID, code, message, image, 0, null, null);
    }

    public ChatMsg(String userID, int code, String filename, long size) {
        this(userID, code, filename, null, size, null, null);
    }

    public ChatMsg(String userID, int code, String filename, ImageIcon image, String uName, String uType) {
        this(userID, code, filename, image, 0, uName, uType);
    }

    public String getMessage(){
        return this.message;
    }

    public String getuId(){
        return this.userID;
    }

    public String getuType(){
        return this.uType;
    }

    public String getuName(){
        return this.uName;
    }

    public ImageIcon getImage(){
        return this.image;
    }
}

