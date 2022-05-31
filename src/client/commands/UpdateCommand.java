package client.commands;

import java.time.ZonedDateTime;
import java.util.Scanner;

import client.util.Interactor;
import client.RequestSender;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.model.MusicBand;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

/**
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private Scanner scanner;

    public UpdateCommand(RequestSender requestSender, Scanner scanner) {
        super(requestSender);
        this.scanner = scanner;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public void execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();

            Integer id = Integer.parseInt(argument);
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
        } catch (NumberFormatException exception) {
            Interactor.printError("ID должен быть представлен числом!");
        } catch (IncorrectInputInScriptException exception) {
            Interactor.printError("Не удалось выполнить скрипт.");
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по ID";
    }
}
