package server.util;

import common.DataManager;
import common.model.MusicBand;
import common.model.MusicGenre;
import common.net.CommandResult;
import common.net.Request;
import common.net.ResultStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionManager extends DataManager {
    private final FileManager fileManager;
    private ZonedDateTime lastInitTime;
    private ZonedDateTime lastSaveTime;
    private TreeSet<MusicBand> musicBandCollection = new TreeSet<>();
    private final Comparator<MusicBand> sortByName = Comparator.comparing(MusicBand::getName);
    public CollectionManager(FileManager fileManager) throws FileNotFoundException {
        this.fileManager = fileManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        loadCollection();
    }
    /**
     * Get last object.
     */
    public MusicBand getLast() {
        if (musicBandCollection.isEmpty()) return null;
        return musicBandCollection.last();
    }

    /**
     * Generates next id's.
     */
    @Override
    protected Long generateNextId() {
        if (getLast() != null) {
            return getLast().getId() + 1;
        }
        return 1L;
    }


    /**
     * add to collection.
     */
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


    /**
     * add to collection if.
     */
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


    /**
     * clear collection.
     */
    @Override
    public CommandResult clear(Request<?> request) {
        musicBandCollection.clear();
        return new CommandResult(ResultStatus.OK, "Элементы успешно удалены из коллекции");
    }


    /**
     * Count by current genre.
     */
    @Override
    public CommandResult count_by_genre(Request<?> request) {
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


    /**
     * execute commands from file.
     */
    @Override
    public CommandResult execute(Request<?> request) {
        return null;
    }


    /**
     * exit from program
     */
    @Override
    public CommandResult exit(Request<?> request) {
        return null;
    }


    /**
     * return text about objects that contain subtext in their text.
     */
    @Override
    public CommandResult filter_contains_name(Request<?> request) {
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

    /**
     * return text about objects that contain subtext at start of their text
     */
    @Override
    public CommandResult filterStarts(Request<?> request) {
        try {
            String text = (String) request.entity;
            String result = musicBandCollection.stream()
                    .filter(MusicBand -> MusicBand.getName().startsWith(text))
                    .sorted(sortByName)
                    .map(MusicBand::toString)
                    .collect(Collectors.joining("\n"));
            return new CommandResult(ResultStatus.OK, result);
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }


    /**
     * Help command. no more...
     */
    @Override
    public CommandResult help(Request<?> request) {
        return null;
    }


    /**
     * return collection info.
     */
    @Override
    public CommandResult info(Request<?> request) {
        ZonedDateTime lastInitTime = getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toString();

        ZonedDateTime lastSaveTime = getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toString();

        String result = "" +
                " Тип: " + collectionType() + "\n" +
                " Количество элементов: " + collectionSize() + "\n" +
                " Дата последнего сохранения: " + lastSaveTimeString + "\n" +
                " Дата последней инициализации: " + lastInitTimeString;
        return new CommandResult(ResultStatus.OK, result);
    }


    /**
     * remove by id command.
     */
    @Override
    public CommandResult remove(Request<?> request) {
        try {
            Long id = (Long) request.entity;
            if (musicBandCollection.stream().noneMatch(MusicBand -> MusicBand.getId().equals(id)))
                return new CommandResult(ResultStatus.ERROR, "Группы с таким ID не существует");
            musicBandCollection.removeIf(studyGroup -> studyGroup.getId().equals(id));
            return new CommandResult(ResultStatus.OK, "Группа успешно удалена");
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }


    /**
     * show text about all collection objects.
     */
    @Override
    public CommandResult show(Request<?> request) {
        StringBuilder result = new StringBuilder();
        for (MusicBand element : musicBandCollection) {
            result.append(element.toString()).append("\n");
        }
        return new CommandResult(ResultStatus.OK, result.toString());
    }

    /**
     * remove greater object.
     */
    @Override
    public CommandResult removeGreater(Request<?> request) {
        try {
            MusicBand musicBand = (MusicBand) request.entity;
            int last = musicBandCollection.size();
            musicBandCollection.removeIf(musicBand1 -> musicBand1.compareTo(musicBand) > 0);
            return new CommandResult(ResultStatus.OK, "Удалено групп: " + (last - musicBandCollection.size()));
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }


    /**
     * save collection to file.
     */
    public CommandResult save(Request<?> request) throws IOException {
        saveCollection();
        return new CommandResult(ResultStatus.OK, "Файл успешно сохранен!");
    }


    /**
     * update object by id.
     */
    @Override
    public CommandResult update(Request<?> request) {
        try {
            MusicBand musicBand = (MusicBand) request.entity;
            if (getById(musicBand.getId() - 1) == null)
                return new CommandResult(ResultStatus.ERROR, "Группы с таким ID не существует");
            musicBandCollection.stream()
                    .filter(studyGroup1 -> studyGroup1.getId().equals(musicBand.getId() - 1))
                    .forEach(studyGroup1 -> studyGroup1.update(musicBand));
            return new CommandResult(ResultStatus.OK, "Группа успешно обновлена");
        } catch (Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }


    /**
     * get MusicBand by Id.
     */
    public MusicBand getById(Long id) {
        return musicBandCollection.stream()
                .filter(MusicBand -> MusicBand.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * collection Type.
     */
    public String collectionType() {
        return musicBandCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return musicBandCollection.size();
    }


    public void saveCollection() throws IOException {
        fileManager.writeCollection(musicBandCollection);
        lastSaveTime = ZonedDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() throws FileNotFoundException {
        musicBandCollection = fileManager.readCollection();
        lastInitTime = ZonedDateTime.now();
    }

    public NavigableSet<MusicBand> getCollection() {
        return musicBandCollection;
    }


    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }
}
