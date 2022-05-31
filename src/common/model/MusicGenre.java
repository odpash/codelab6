package common.model;

import java.io.Serializable;

/**
 * MusicGenre data enum
 */

public enum MusicGenre implements Serializable {
    PSYCHEDELIC_CLOUD_RAP,
    POP,
    POST_PUNK;
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (MusicGenre musicGenre: values()) {
            nameList.append(musicGenre.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}