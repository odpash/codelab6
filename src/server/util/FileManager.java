package server.util;

import common.model.MusicBand;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Csv executing
 */
public class FileManager {
    private String filePath;
    public FileManager(String filePath) {
        this.filePath = filePath;
    }
    /**
     * write
     *
     * @param collection
     * @throws IOException
     */
    public void writeCollection(TreeSet<MusicBand> collection) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "MacCyrillic"));
        bw.append("id (авто)");
        bw.append(",");
        bw.append("Название");
        bw.append(",");
        bw.append("Дата создания записи (авто)");
        bw.append(",");
        bw.append("Число участников");
        bw.append(",");
        bw.append("Описание");
        bw.append(",");
        bw.append("Дата создания группы");
        bw.append(",");
        bw.append("Жанр");
        bw.append(",");
        bw.append("Расположение по X");
        bw.append(",");
        bw.append("Расположение по Y");
        bw.append(",");
        bw.append("Название лучшего альбома");
        bw.append(",");
        bw.append("Треки");
        bw.append(",");
        bw.append("Длина");
        bw.newLine();
        for (MusicBand element : collection) {
            bw.append(Long.toString(element.getId()));
            bw.append(",");
            bw.append(element.getName());
            bw.append(",");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
            String formattedString = element.getCreationDate().format(formatter);
            bw.append(formattedString);
            bw.append(",");
            bw.append(String.valueOf(element.getNumberOfParticipants()));
            bw.append(",");
            bw.append(element.getDescription());
            bw.append(",");
            try {
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                bw.append(element.getEstablishmentDate().format(formatter2));
            } catch (Exception e) {
                bw.append("");
            }
            bw.append(",");
            bw.append(element.getGenre().name());
            bw.append(",");
            bw.append(Long.toString(element.getCoordinates().getX()));
            bw.append(",");
            bw.append(Long.toString(element.getCoordinates().getY()));
            bw.append(",");
            bw.append(element.getBestAlbum().getName());
            bw.append(",");
            bw.append(Long.toString(element.getBestAlbum().getTracks()));
            bw.append(",");
            bw.append(String.valueOf(element.getBestAlbum().getLength()));

            bw.newLine();
        }
        bw.close();
    }


    public TreeSet<MusicBand> readCollection() throws FileNotFoundException {
        InputStream targetStream = null;
        try {
            File initialFile = new File(filePath);
            if (!initialFile.exists()) {
                System.out.println("File not found!");
                System.exit(1);
            }
            targetStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            System.out.println("Permission denied!");
            System.exit(1);
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(targetStream, "MacCyrillic"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        TreeSet<MusicBand> collection = new TreeSet<>();
        String line;
        int index = 0;
        try {
            while (true) {
                assert reader != null;
                if ((line = reader.readLine()) == null) break;
                String[] lineArr = line.split(";");
                if (index != 0 && lineArr.length > 0) {
                    collection.add(new MusicBand(lineArr));
                }
                index++;
            }
            System.out.println("Коллекция успешно загружена! Размер: " + collection.size());
            return collection;
        } catch (NoSuchElementException | NullPointerException | IllegalStateException exception) {

            System.out.println("NoSuchElementException | NullPointerException | IllegalStateException!");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(1);
        return new TreeSet<>();
    }
}
