package server.execution;

import client.commands.*;
import common.DataManager;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.util.HashMap;

public class ExecutionService {
    private HashMap<String, Executable> commands = new HashMap<>();
    private DataManager dataManager;

    public ExecutionService(DataManager dataManager) {
        this.dataManager = dataManager;
        initCommands();
    }

    private void initCommands() {
        commands.put("add", dataManager::add);
        commands.put("addIf", dataManager::addIf);
        commands.put("clear", dataManager::clear);
        commands.put("countByGenre", dataManager::countByGenre);
        commands.put("execute", dataManager::execute);
        commands.put("exit", dataManager::exit);
        commands.put("filterContains", dataManager::filterContains);
        commands.put("filterStarts", dataManager::filterStarts);
        commands.put("help", dataManager::help);
        commands.put("info", dataManager::info);
        commands.put("remove", dataManager::remove);
        commands.put("removeGreater", dataManager::removeGreater);
        commands.put("save", dataManager::save);
        commands.put("update", dataManager::update);
    }

    public CommandResult executeCommand(Request<?> request) {
        if (!commands.containsKey(request.command))
            return new CommandResult(ResultStatus.ERROR, "Такой команды на сервере нет.");
        return commands.get(request.command).execute(request);
    }
}