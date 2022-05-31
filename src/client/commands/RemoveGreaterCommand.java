package client.commands;

import client.util.Interactor;
import client.RequestSender;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.model.MusicBand;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * 'remove_greater' command. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends Command {

    private Scanner scanner;

    public RemoveGreaterCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender);
        this.scanner = scanner;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public void execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            MusicBand musicBand = new MusicBand(
                    Interactor.askBandName(scanner),
                    Interactor.askCoordinates(scanner),
                    ZonedDateTime.now(),
                    Interactor.askParticipantsCount(scanner),
                    Interactor.askDescription(scanner),
                    Interactor.askEstablishmentTime(scanner),
                    Interactor.askGenre(scanner),
                    Interactor.askBestAlbum(scanner)
            );
            Request<MusicBand> request = new Request<>(getName(), musicBand);
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
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }
}