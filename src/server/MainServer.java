package server;

import common.Config;
import common.DataManager;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;
import server.execution.ExecutionService;
import server.util.CollectionManager;
import server.util.FileManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MainServer {
    public final static int SERVICE_PORT = Config.PORT;

    public static void main(String[] args) throws IOException {
        DataManager dataManager;
        try {
            dataManager = new CollectionManager(new FileManager(Config.FilePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        try {
            ExecutionService executionService = new ExecutionService(dataManager);
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            byte[] receivingDataBuffer = new byte[1024];
            byte[] sendingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");
            serverSocket.receive(inputPacket);

            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
            Request<> request = (Request<?>) iStream.readObject();
            System.out.println("Command : " + request.command);

            CommandResult result = executionService.executeCommand(request);
            if (result.status == ResultStatus.OK)
                System.out.println("Команда выполнена успешно");
            else
                System.out.println("Команда выполнена неуспешно");

            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            //objectOutputStream.writeObject(result);


//            String receivedData = new String(inputPacket.getData());
//            System.out.println("Sent from the client: " + receivedData);
//            sendingDataBuffer = receivedData.toUpperCase().getBytes();
//            InetAddress senderAddress = inputPacket.getAddress();
            //           int senderPort = inputPacket.getPort();
            //          DatagramPacket outputPacket = new DatagramPacket(
            //                sendingDataBuffer, sendingDataBuffer.length,
            //              senderAddress, senderPort
            //    );
            //  serverSocket.send(outputPacket);
            //serverSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}