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
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command {
    private Scanner scanner;

    public AddCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender);
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(String stringArgument) {
        try {
            if (!stringArgument.isEmpty()) throw new WrongAmountOfArgumentsException();
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
}