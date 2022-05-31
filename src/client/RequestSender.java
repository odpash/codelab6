package client;

import common.Config;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import static server.MainServer.SERVICE_PORT;

public class RequestSender {
    protected final int MAX_ATTEMPTS_COUNT = 5;
    private int port = Config.PORT;

    public RequestSender() {}

    public RequestSender(int port) {
        this.port = port;
    }

    public CommandResult sendRequest(Request<?> request){
        if(request == null){
            throw new IllegalArgumentException("Запрос не может быть null!");
        }

        int attempts = 0;
        while (attempts < MAX_ATTEMPTS_COUNT){
            try{
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName("localhost");
                byte[] receivingDataBuffer = new byte[1024];

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(request);
                oo.close();
                byte[] sendingDataBuffer = bStream.toByteArray();
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, SERVICE_PORT);
                clientSocket.send(sendingPacket);
                DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                clientSocket.receive(receivingPacket);
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receivingPacket.getData()));
                CommandResult result = (CommandResult) iStream.readObject();
                iStream.close();
                clientSocket.close();
                if(attempts != 0){
                    System.out.println("Подключение восстановлено.");
                }
                attempts = MAX_ATTEMPTS_COUNT;
                return result;
            }
            catch (IOException | ClassNotFoundException exc){
                System.out.println(exc);
                System.out.println("Не удалось подключиться к серверу, подождем...");
                attempts++;
                try {
                    Thread.sleep(5 * 1000);
                }
                catch (Exception ignored) { }
            }
        }
        return new CommandResult(ResultStatus.ERROR, "Прошло 25 секунд, сервер умер.");
    }
}