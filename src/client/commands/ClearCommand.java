package client.commands;

import client.util.Interactor;
import client.RequestSender;
import common.exceptions.WrongAmountOfArgumentsException;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.util.Scanner;

/**
 * 'clear' command. Prints information about the collection.
 */
public class ClearCommand extends Command {

    public ClearCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender);
    }

    @Override
    public void execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();

            Request<String> request = new Request<>(getName(), null);
            CommandResult result = requestSender.sendRequest(request);
            if (result.status == ResultStatus.OK) {
                Interactor.println(result.message);
            }
            else
                Interactor.printError(result.message);
        } catch (WrongAmountOfArgumentsException exception) {
            Interactor.println("Использование: '" + getName() + "'");
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}