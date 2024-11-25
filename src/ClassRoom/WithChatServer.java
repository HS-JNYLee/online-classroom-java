package ClassRoom;

import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class WithChatServer extends JFrame {
    private int port;
    private ServerSocket serverSocket = null;

    private Thread acceptThread = null;
    private Vector<ClientHandler> users = new Vector<ClientHandler>();

    private JTextArea t_display;
    private JButton b_connect, b_disconnect, b_exit;

    public WithChatServer(int port) {
        super("With Chat Server");

        DatabaseFile.parsing();

        buildGUI();

        setSize(400, 300);
        setLocation(500, 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        this.port = port;
    }

    private void buildGUI() {
        add(createDisplayPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
    }
    private JPanel createDisplayPanel() {
        JPanel p = new JPanel(new BorderLayout());

        t_display = new JTextArea();
        t_display.setEditable(false);

        p.add(new JScrollPane(t_display), BorderLayout.CENTER);
        return p;
    }
    private JPanel createControlPanel() {
        b_connect = new JButton("서버 시작");
        b_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptThread = new Thread(new Runnable() {
                    public void run() {
                        startServer();
                    }
                });
                acceptThread.start();
                b_connect.setEnabled(false); // 연결하기 비활성화
                b_disconnect.setEnabled(true); // 접속끊기 활성화
                b_exit.setEnabled(false); // 종료하기 비활성화
            }
        });

        b_disconnect = new JButton("서버 종료");
        // [접속 끊기] 버튼으로 연결 해제
        b_disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });

        b_exit = new JButton("종료");
        b_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
                System.exit(0);
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3));
        // 배치 미리보기
        // [접속하기] [접속끊기] [종료하기]

        controlPanel.add(b_connect);
        controlPanel.add(b_disconnect);
        controlPanel.add(b_exit);

        b_connect.setEnabled(true); // 연결하기 활성화
        b_disconnect.setEnabled(false); // 접속끊기 비활성화
        b_exit.setEnabled(true); // 종료하기 활성화

        return controlPanel;
    }
    private String getLocalAddr() {
        InetAddress local = null;
        String addr = "";
        try {
            local = InetAddress.getLocalHost();
            addr = local.getHostAddress();
            System.out.println(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return addr;
    }
    private void startServer() {
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            printDisplay("서버가 시작되었습니다.: " + getLocalAddr());
            while (acceptThread == Thread.currentThread()) {
                clientSocket = serverSocket.accept();
                String cAddr = clientSocket.getInetAddress().getHostAddress();
                t_display.append("클라이언트가 연결되었습니다: " +cAddr + "\n");
                ClientHandler cHandler = new ClientHandler(clientSocket);
                users.add(cHandler);
                cHandler.start();
            }
        } catch (SocketException e) {
            printDisplay("서버 소켓 종료");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("서버 닫기 오류> " + e.getMessage());
                System.exit(-1);
            }
        }
    }
    private void disconnect() {
        /* 소켓 닫기 */
        try {
            if(serverSocket != null) serverSocket.close();
            b_connect.setEnabled(true); // 연결하기 활성화
            b_disconnect.setEnabled(false); // 연결 끊기 비활성화
            b_exit.setEnabled(true); // 종료하기 활성화
        } catch (IOException ex) {
            System.err.println("서버 닫기 오류> "+ex.getMessage());
            System.exit(-1);
        }
    }
    private void printDisplay(String msg) {
        t_display.append(msg + "\n");
        t_display.setCaretPosition(t_display.getDocument().getLength());
    }
    class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private String uid;
        ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        private void receiveMessage(Socket cs) {
            try {
                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(cs.getInputStream()));
                out = new ObjectOutputStream(new BufferedOutputStream(cs.getOutputStream()));

                ChatMsg msg;
                while ((msg = (ChatMsg)in.readObject()) != null) {
                    if (msg.mode == ChatMsg.MODE_CONNECT) {
                        uid = msg.userID;
                        printDisplay("새 참가자: " + uid);
                        printDisplay("현재 참가자 수: " + users.size());
                        continue;
                    } else if (msg.mode == ChatMsg.MODE_LOGIN) {
                        printDisplay("참가자 구분: " + msg.uType);
                        printDisplay("참가자 이름: " + msg.uName);
                        printDisplay("참가자 학번/교번: " + msg.uId);
                        User user = new User();
                        user.setRole(DatabaseFile.matchRole(msg.uType));
                        user.setName(msg.uName);
                        user.setId(msg.uId);
                        sendMessage(DatabaseFile.isValidate(user));
                    } else if (msg.mode == ChatMsg.MODE_LOGOUT) {
                        break;
                    } else if (msg.mode == ChatMsg.MODE_TX_STRING) {
                        String message = uid + ": " + msg.message;
                        printDisplay(message);
                        broadcasting(msg);
                    } else if (msg.mode == ChatMsg.MODE_TX_IMAGE) {
                        printDisplay(uid + ": " + msg.message);
                        broadcasting(msg);
                    }
                }
                users.removeElement(this);
                printDisplay(uid + "퇴장. 현재 참가자 수: " + users.size());
            } catch (IOException e) {
                users.removeElement(this);
                printDisplay(uid + " 연결 끊김. 현재 참가자 수: " + users.size());
            } catch (ClassNotFoundException e) {
                users.removeElement(this);
                printDisplay("잘못된 객체가 전달되었습니다.");
            }
            finally {
                try {
                    cs.close();
                } catch (IOException e) {
                    System.err.println("서버 닫기 오류> " + e.getMessage());
                    System.exit(-1);
                }
            }
        }
        private void send(ChatMsg msg) {
            try {
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                System.err.println("클라이언트 일반 전송 오류> "+e.getMessage());
            }
        }

        private void sendMessage(String msg) {
            send(new ChatMsg(uid, ChatMsg.MODE_TX_STRING, msg));
        }

        private void sendMessage(Boolean isValid) {
            String msg = isValid ? "참여를 시작합니다." : "올바르지 않은 데이터입니다.";
            if(isValid) {
                send(new ChatMsg(uid, ChatMsg.MODE_TX_ACCESS, msg));
            } else {
                send(new ChatMsg(uid, ChatMsg.MODE_TX_DENIED, msg));
            }
        }

        private void broadcasting(ChatMsg msg) {
            for (ClientHandler c : users) {
                c.send(msg);
            }
        }

        @Override
        public void run() {
            receiveMessage(clientSocket);
        }
    }


    public static void main(String[] args) {
        int port = 8080;

        WithChatServer server = new WithChatServer(port);
    }
}
