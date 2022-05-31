package server.execution;

import client.commands.*;
import common.DataManager;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.io.IOException;
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
        commands.put("add_if_max", dataManager::addIf);
        commands.put("clear", dataManager::clear);
        commands.put("show", dataManager::show);
        commands.put("count_by_genre", dataManager::count_by_genre);
        commands.put("execute", dataManager::execute);
        commands.put("exit", dataManager::exit);
        commands.put("filter_contains_name", dataManager::filter_contains_name);
        commands.put("filter_starts_with_name", dataManager::filterStarts);
        commands.put("help", dataManager::help);
        commands.put("info", dataManager::info);
        commands.put("remove_by_id", dataManager::remove);
        commands.put("remove_greater", dataManager::removeGreater);
        commands.put("update", dataManager::update);
        commands.put("save", dataManager::save);
    }

    public CommandResult executeCommand(Request<?> request) throws IOException {
        if (!commands.containsKey(request.command))
            return new CommandResult(ResultStatus.ERROR, "Такой команды на сервере нет.");
        return commands.get(request.command).execute(request);
    }
}