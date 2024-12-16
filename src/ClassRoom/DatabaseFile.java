package ClassRoom;

import User.User;
import User.Roles;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class DatabaseFile {
    static private ArrayList<User> userList;

    static public void parsing() {
        // 텍스트 파일 경로
        String filePath = "src/account.txt";

        // 데이터를 저장할 리스트
        userList = new ArrayList<>();

        // 파일 읽기
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // , 로 분리
                String[] parts = line.split(",");

                // 데이터 검증
                if (parts.length != 6) {
                    System.err.println("잘못된 데이터 형식: " + line);
                    continue;
                }

                // 데이터 추출 및 Person 객체 생성
                String name             = parts[0].trim();
                String id               = parts[1].trim();
                String type             = parts[2].trim();
                String iconPath         = parts[3].trim();
                String teamRoomAddr     = parts[4].trim();
                String userTableIndex   = parts[5].trim(); // 출석용도 지정 좌석 번호

                User user = new User();
                user.setName(name);
                user.setId(id);

                File file = new File(iconPath);
                if (file.exists()) {
                    ImageIcon icon = new ImageIcon(iconPath);
                    user.setProfileImage(icon);
                }
                user.setTeamRoomAddr(Integer.parseInt(teamRoomAddr));
                user.setUserTableIndex(Integer.parseInt(userTableIndex));

                user.setRole(matchRole(type));
                userList.add(user);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + e.getCause());
        }

    }

    public boolean isValidate(User user) {
        System.out.println("인증 : " + user.getRole() + user.getName() + user.getId());
        for (User user1 : userList) {
            if (user1.getId().equals(user.getId())
                    && user1.getName().equals(user.getName())
                    && user1.getRole().equals(user.getRole())) {
                user.setUserTableIndex(user1.getUserTableIndex());
                user.setTeamRoomAddr(user1.getTeamRoomAddr());
                user.setName(user1.getName());
                return true;
            }
        }
        return false;
    }

    public static Roles matchRole(String type) {
        if(type.equals(Roles.STUDENT.name())) {
            return Roles.STUDENT;
        } else if (type.equals(Roles.PROFESSOR.name())) {
            return Roles.PROFESSOR;
        }
        return Roles.STUDENT;
    }
}

