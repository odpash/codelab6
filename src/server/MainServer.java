package server;

import common.Config;
import common.DataManager;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;
import server.execution.ExecutionService;
import server.util.CollectionManager;
import server.util.FileManager;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainServer {
    public final static int SERVICE_PORT = Config.PORT;


    private static Thread getUserInputHandler(DataManager dataManager, AtomicBoolean exit) {
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (scanner.hasNextLine()) {
                    String serverCommand = scanner.nextLine();

                    if ("exit".equals(serverCommand)) {
                        exit.set(true);
                        return;
                    } else {
                        System.out.println("Такой команды нет.");
                    }
                } else {
                    exit.set(true);
                    return;
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        DataManager dataManager;
        try {
            dataManager = new CollectionManager(new FileManager(Config.FilePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Выход");
        }));

        ExecutionService executionService = new ExecutionService(dataManager);
        AtomicBoolean exit = new AtomicBoolean(false);
        getUserInputHandler(dataManager, exit).start();
        while (!exit.get()) {
            try {
                DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
                byte[] receivingDataBuffer = new byte[1024];
                DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                System.out.println("Waiting for a client to connect...");
                serverSocket.receive(inputPacket);

                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Request<?> request = (Request<?>) iStream.readObject();
                System.out.println("Command : " + request.command);

                CommandResult result = executionService.executeCommand(request);
                if (result.status == ResultStatus.OK)
                    System.out.println("Команда выполнена успешно");
                else
                    System.out.println("Команда выполнена неуспешно");
                InetAddress IPAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(result);
                oo.close();
                byte[] sendingDataBuffer = bStream.toByteArray();
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, senderPort);
                serverSocket.send(sendingPacket);
                serverSocket.close();


            } catch (SocketException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}