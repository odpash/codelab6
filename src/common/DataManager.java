package common;

import common.net.CommandResult;
import common.net.Request;

import java.io.IOException;

public abstract class DataManager {
    protected abstract Long generateNextId();
    public abstract CommandResult add(Request<?> request);
    public abstract CommandResult addIf(Request<?> request);
    public abstract CommandResult clear(Request<?> request);
    public abstract CommandResult count_by_genre(Request<?> request);
    public abstract CommandResult execute(Request<?> request);
    public abstract CommandResult exit(Request<?> request);
    public abstract CommandResult filter_contains_name(Request<?> request);
    public abstract CommandResult filterStarts(Request<?> request);
    public abstract CommandResult help(Request<?> request);
    public abstract CommandResult info(Request<?> request);
    public abstract CommandResult remove(Request<?> request);
    public abstract CommandResult show(Request<?> request);
    public abstract CommandResult removeGreater(Request<?> request);
    public abstract CommandResult update(Request<?> request);
    public abstract CommandResult save(Request<?> request) throws IOException;
}


