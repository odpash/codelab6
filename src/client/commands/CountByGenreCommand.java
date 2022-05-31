package client.commands;

import client.RequestSender;
import client.util.Interactor;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.model.MusicGenre;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.util.Scanner;

/**
 * 'count_by_group_admin_command' command. Prints amount of groups with the same admin
 */
public class CountByGenreCommand extends Command{
    private Scanner scanner;

    public CountByGenreCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender);
        this.scanner = scanner;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public void execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            MusicGenre genre = Interactor.askGenre(scanner);

            Request<MusicGenre> request = new Request<>(getName(), genre);
            CommandResult result = requestSender.sendRequest(request);
            if (result.status == ResultStatus.OK)
                Interactor.println(result.message);
            else
                Interactor.printError(result.message);
        } catch (WrongAmountOfArgumentsException exception) {
            Interactor.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            Interactor.printError("Не удалось выполнить скрипт.");
        }
    }

    @Override
    public String getName() {
        return "count_by_genre";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов, значение поля genre которых равно заданному";
    }
}