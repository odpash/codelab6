package client;

import client.util.CommandManager;
import client.util.Interactor;
import common.Config;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

import static server.MainServer.SERVICE_PORT;

public class MainClient {
    public final static int port = Config.PORT;

    public static void main(String[] args) throws IOException {;
        Scanner scanner = new Scanner(System.in);
        RequestSender requestSender = new RequestSender(port);
        CommandManager commandManager = new CommandManager(requestSender, scanner);


        String input;
        do {
            System.out.print("Введите команду: ");
            if (!scanner.hasNextLine()) return;
            input = scanner.nextLine();
            try {
                commandManager.parseAndExecute(input);
            } catch (Exception e) {
                Interactor.printError(e.getMessage());
            }
            Interactor.println("");
        } while (!input.equals("exit"));
    }
}
