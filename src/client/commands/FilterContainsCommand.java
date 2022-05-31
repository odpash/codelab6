package client.commands;


import client.RequestSender;
import client.util.Interactor;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.util.Scanner;

/**
 * 'filter_greater_than_expelled_students' command.
 */
public class FilterContainsCommand extends Command {
    private Scanner scanner;
    public FilterContainsCommand(RequestSender requestSender) {
        super(requestSender);
        Scanner scanner = new Scanner(System.in);
        this.scanner = scanner;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */

    @Override
    public void execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            String name = argument;
            Request<String> request = new Request<>(getName(), name);
            CommandResult result = requestSender.sendRequest(request);
            if (result.status == ResultStatus.OK)
                Interactor.println(result.message);
            else
                Interactor.printError(result.message);
        } catch (WrongAmountOfArgumentsException exception) {
            Interactor.println("Использование: '" + getName() + "'");
        } catch (NumberFormatException exception) {
            Interactor.println("Ожидается число типа Long");
        }
    }

    @Override
    public String getName() {
        return "filter_contains_name";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}