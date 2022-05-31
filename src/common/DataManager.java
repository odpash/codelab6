package common;

import common.net.CommandResult;
import common.net.Request;

public abstract class DataManager {
    protected abstract Long generateNextId();
    public abstract CommandResult add(Request<?> request);
    public abstract CommandResult addIf(Request<?> request);
    public abstract CommandResult clear(Request<?> request);
    public abstract CommandResult countByGenre(Request<?> request);
    public abstract CommandResult execute(Request<?> request);
    public abstract CommandResult exit(Request<?> request);
    public abstract CommandResult filterContains(Request<?> request);
    public abstract CommandResult filterStarts(Request<?> request);
    public abstract CommandResult help(Request<?> request);
    public abstract CommandResult info(Request<?> request);
    public abstract CommandResult remove(Request<?> request);
    public abstract CommandResult removeGreater(Request<?> request);
    public abstract CommandResult save(Request<?> request);
    public abstract CommandResult update(Request<?> request);
}


