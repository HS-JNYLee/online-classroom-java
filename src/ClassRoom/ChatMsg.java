package ClassRoom;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public final static int MODE_USERINFO_MSG = 0x52;
    public final static int MODE_SHARED_SCREEN = 0x53;
    public final static int MODE_MIC_SOUND = 0x59;
    public final static int MODE_EMOJI = 0x60; // 이모티콘 송수신 모드

    String userID;
    int mode;
    String message;
    ImageIcon image;
    long size;
    String uName;
    String uType;
    byte[] imageBytes;
    byte[] micSound;
    int x;
    int y;

    public ChatMsg(String userID, String uType, int code){
        this.userID = userID;
        this.uType = uType;
        this.mode = code;
    }

    public ChatMsg(String userID, String uType, int code, int x, int y){
        this(userID, uType, code);
        this.x = x;
        this.y = y;
    }

    // TODO 학생의 모둠 번호도 추가하여 모둠 별로만 통신이 가능하도록 설계
    public ChatMsg(String userID, String uType, int code, byte[] micSound){
        this.userID = userID;
        this.uType = uType;
        this.mode = code;
        this.micSound = micSound;
    }

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

    public ChatMsg(String userID, int code, BufferedImage image) {
        this(userID, code, null, null, 0, null, null);
        setImageBytes(image);
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

    public byte[] getMicSound(){
        return this.micSound;
    }

    public int getCode() {
        return this.mode;
    }




    public void setImageBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
            this.imageBytes = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImageBytes() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(this.imageBytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

