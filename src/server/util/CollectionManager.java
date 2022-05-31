package server.util;

import common.DataManager;
import common.model.MusicBand;
import common.model.MusicGenre;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionManager extends DataManager {
    private final FileManager fileManager;
    private TreeSet<MusicBand> musicBandCollection = new TreeSet<>();
    private final Comparator<MusicBand> sortByName = Comparator.comparing(MusicBand::getName);
    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public MusicBand getLast() {
        if (musicBandCollection.isEmpty()) return null;
        return musicBandCollection.last();
    }

    @Override
    protected Long generateNextId() {
        return getLast().getId() + 1;
    }

    @Override
    public CommandResult add(Request<?> request) {
        try {
            MusicBand musicBand = (MusicBand) request.entity;
            musicBand.setId(generateNextId());
            musicBandCollection.add(musicBand);
            return new CommandResult(ResultStatus.OK, "Новый элемент успешно добавлен");
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult addIf(Request<?> request) {
        try {
            MusicBand musicBand = (MusicBand) request.entity;
            musicBand.setId(generateNextId());
            if (musicBand.compareTo(musicBandCollection.last()) > 0) {
                musicBandCollection.add(musicBand);
                return new CommandResult(ResultStatus.OK, "Новый элемент успешно добавлен");
            }
            return new CommandResult(ResultStatus.OK, "Элемент не максимальный");
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult clear(Request<?> request) {
        musicBandCollection.clear();
        return new CommandResult(ResultStatus.OK, "Элементы успешно удалены из коллекции");
    }

    @Override
    public CommandResult countByGenre(Request<?> request) {
        try {
            MusicGenre musicGenre = (MusicGenre) request.entity;
            int num = (int) musicBandCollection.stream()
                    .filter(musicBand -> musicBand.getGenre().equals(musicGenre))
                    .count();
            return new CommandResult(ResultStatus.OK, "Элементов с Genre: " + num);
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult execute(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult exit(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult filterContains(Request<?> request) {
        try {
            String text = (String) request.entity;
            String result = musicBandCollection.stream()
                    .filter(MusicBand -> MusicBand.getName().contains(text))
                    .sorted(sortByName)
                    .map(MusicBand::toString)
                    .collect(Collectors.joining("\n"));
            return new CommandResult(ResultStatus.OK, result);
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult filterStarts(Request<?> request) {
        try {
            String text = (String) request.entity;
            String result = musicBandCollection.stream()
                    .filter(MusicBand -> MusicBand.getName().contains(text))
                    .sorted(sortByName)
                    .map(MusicBand::toString)
                    .collect(Collectors.joining("\n"));
            return new CommandResult(ResultStatus.OK, result);
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult help(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult info(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult remove(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult removeGreater(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult save(Request<?> request) {
        return null;
    }

    @Override
    public CommandResult update(Request<?> request) {
        return null;
    }

}
