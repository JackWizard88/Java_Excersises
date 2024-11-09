package ru.jackwizard.Exercise5;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {

        try {
            openConnection();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect Server");
            System.exit(1);
        }
        messageSender();
    }

    public static void main(String[] args) {
        new Client();
    }

    private void openConnection() throws IOException {

        socket = new Socket(Common.SERVER_ADDRESS, Common.DEFAULT_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        //отправка имени пользователя на сервер
        String username = JOptionPane.showInputDialog("Введите имя пользователя");
        out.writeUTF(username);

        //запуск потока отправки сообщений
        messageSender();

        //цикл потока входящих сообщений
        try {
            while (true) {
                System.out.println(in.readUTF());
            }
        } catch (Exception e) {
            System.out.println("Connection has been closed!");
            System.exit(0);
        }

    }

    public void messageSender() {

        new Thread(() -> {
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String msg = null;
                try {
                    msg = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (msg.equals(Common.END_COMMAND)) {
                    System.out.println("Connection has been closed!");
                    System.exit(0);
                }

                if (!msg.trim().isEmpty()) {
                    try {
                        out.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
                    }
                }
            }
        }).start();
    }
}
