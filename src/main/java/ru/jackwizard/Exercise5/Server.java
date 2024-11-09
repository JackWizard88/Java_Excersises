package ru.jackwizard.Exercise5;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Написать консольный вариант обмена сообщениями между клиентами и сервером.
 * Сервер принимает сообщения от клиентов и отображает их у себя, а так же рассылает остальным клиентам.
 * Сервер может отправлять сообщения от имени Сервера
 * Клиенты могут отправлять и получать сообщения
 */

public class Server {

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    HashMap<User, Socket> userlist;

    public Server() {
        userlist = new HashMap<>();
        try {
            startServer(Common.DEFAULT_PORT);
        } catch (IOException e) {
            System.err.println("Port is closed or already in use");
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    private void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(this::messageSender).start();
        System.out.println("Сервер запущен\n");
        conListener();
    }

    private void conListener() throws IOException {

        while (true) {
            clientSocket = serverSocket.accept();
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            String name = dataInputStream.readUTF();
            User user = new User(name, dataInputStream, dataOutputStream, clientSocket);
            userlist.put(user, clientSocket);
            System.out.println(name + " подключился");

            for (Map.Entry<User, Socket> entry : userlist.entrySet()) {
                if(!user.equals(entry.getKey())) {
                    try {
                        entry.getKey().getOut().writeUTF(name + " подключился");
                    } catch (IOException el) {
                        el.printStackTrace();
                    }
                }
            }
            new Thread(() -> messageReader(user)).start();
        }

    }

    private void messageSender() {

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;

            try {
                msg = reader.readLine();
            } catch (IOException e) {
                System.err.println("Ошибка ввода");
            }

            if (!msg.trim().isEmpty()) {

                if (msg.equals(Common.END_COMMAND)) closeServer();

                try {
                    for (Map.Entry<User, Socket> entry : userlist.entrySet()) {
                        entry.getKey().getOut().writeUTF("Server: " + msg);
                    }

                } catch (IOException e) {
                    errorPanel("Ошибка отправки сообщения");
                }
            }
        }
    }

    private void closeServer() {
        try {
            if (!userlist.isEmpty()) {
                for (Map.Entry<User, Socket> entry : userlist.entrySet()) {
                    entry.getKey().getOut().writeUTF("Server SHUTDOWN ");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            errorPanel("Ошибка отправки сообщения");
        }
        System.exit(0);
    }

    private static void errorPanel(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void messageReader(User user) {

        while (true) {

            String message = null;

            try {

                message = user.getIn().readUTF();

                switch (message) {
                    case Common.CLIENT_LIST_COMMAND:
                        sendUserList(user);
                        break;
                    default:
                        sendMessage(user, message);
                }


            } catch (SocketException e) {
                try {
                    System.out.println(user.getName() + " отключился");
                    for (Map.Entry<User, Socket> entry : userlist.entrySet()) {
                        if (!user.equals(entry.getKey())) {
                            try {
                                entry.getKey().getOut().writeUTF(user.getName() + " отключился");
                            } catch (IOException el) {
                                el.printStackTrace();
                            }
                        }
                    }
                    user.getSocket().close();
                    userlist.remove(user);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    private void sendMessage(User user, String message) {
        System.out.println(user.getName() + ": " + message);
        for (Map.Entry<User, Socket> entry : userlist.entrySet()) {
            if(!user.equals(entry.getKey())) {
                try {
                    entry.getKey().getOut().writeUTF(user.getName() + ": " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendUserList(User user) throws IOException {
        List<String> us = userlist.keySet().stream().map(User::getName).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append("online users:\n");
        us.forEach(u -> sb.append(u).append("\n"));
        user.getOut().writeUTF(sb.toString());
    }
}
