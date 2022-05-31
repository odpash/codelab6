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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        RequestSender requestSender = new RequestSender(port);
        CommandManager commandManager = new CommandManager(requestSender, scanner);


        String input;
        do {
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

// TODO: ПЛАН-КАПКАН
//  1) Расписать команды на клиенте (~ чвс): до 3:30
//  2) Расписать добавление команд на клиенте (~ час) до 4:30
//  3) Расписать принятие команд на хосте (~ час) до 5:30
//  4) Расписать выполнение команд на хосте (~ час) до 6:30
//  5) Small fixes and закинуть и запустить на сервере (~ час) до 7:30
//  6) Fixes from 7:30 to 9:00 (~ час)
