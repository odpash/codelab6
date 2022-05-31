package client.commands;

import client.util.CommandManager;
import client.util.Interactor;
import common.exceptions.WrongAmountOfArgumentsException;

/**
 * 'help' command. Just for logical structure. Does nothing.
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super();
        this.commandManager = commandManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public void execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            Interactor.println(commandManager.getCommandsNameWithDescription());
        } catch (WrongAmountOfArgumentsException exception) {
            Interactor.println("Использование: '" + getName() + "'");
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}