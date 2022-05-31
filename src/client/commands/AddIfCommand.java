package client.commands;

import client.RequestSender;

import java.util.Scanner;

/**
 * Command 'add_if'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfCommand extends AddCommand {

    public AddIfCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender, scanner);
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент, если его значение больше, чем у максимального";
    }
}