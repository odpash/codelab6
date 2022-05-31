package common.model;

import java.util.Formatter;
import java.util.Objects;

/**
 * Album data class
 */
public class Album {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long tracks; //Значение поля должно быть больше 0
    private int length; //Значение поля должно быть больше 0

    public Album(String name, long tracks, int length) {
        this.name = name;
        this.tracks = tracks;
        this.length = length;
    }

    public Album() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTracks(long tracks) {
        this.tracks = tracks;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public long getTracks() {
        return tracks;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return length == album.length &&
                tracks == album.tracks &&
                Objects.equals(name, album.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, tracks, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "length=" + length +
                ", tracks=" + tracks +
                ", name='" + name + '\'' +
                '}';
    }
}