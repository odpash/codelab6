package client.commands;

import client.RequestSender;

import java.util.Scanner;

public class AddIfCommand extends AddCommand {

    public AddIfCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender, scanner);
    }

    @Override
    public String getName() {
        return "add_if";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент, если его значение больше, чем у максимального";
    }
}