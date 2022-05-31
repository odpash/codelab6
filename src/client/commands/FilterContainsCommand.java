package client.commands;


import client.RequestSender;
import client.util.Interactor;
import common.exceptions.WrongAmountOfArgumentsException;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

/**
 * 'filter_greater_than_expelled_students' command.
 */
public class FilterContainsCommand extends Command {

    public FilterContainsCommand(RequestSender requestSender) {
        super(requestSender);
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */

    @Override
    public void execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            Long expelledStudents = Long.parseLong(argument);
            Request<Long> request = new Request<>(getName(), expelledStudents);
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
        return "filter_contains";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}