package common.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * MusicBand data class
 */
public class MusicBand implements Comparable<MusicBand>{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private java.time.LocalDateTime establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле может быть null
    private static Long lastId = 0L;
    public MusicBand(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Integer numberOfParticipants, String description, LocalDateTime establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(String name, Coordinates coordinates, ZonedDateTime creationDate, Integer numberOfParticipants, String description, LocalDateTime establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(String[] groupInformation) {
        lastId++;
        this.id = lastId;
        this.name = groupInformation[1];
        long xx;
        long yy;
        if (groupInformation[7].length() > 0) {
            xx = Long.parseLong(groupInformation[7]);
        } else {
            xx = 1L;
        }
        if (groupInformation[8].length() > 0) {
            yy = Long.parseLong(groupInformation[8]);
        } else {
            yy = 1L;
        }

        this.coordinates = new Coordinates(xx, yy);
        this.creationDate = ZonedDateTime.now();
        if (groupInformation[3].length() > 0) {
            this.numberOfParticipants = Integer.valueOf(groupInformation[3]);
        } else {
            this.numberOfParticipants = null;
        }
        if (groupInformation[4].length() > 0) {
            this.description = groupInformation[4];
        } else {
            this.description = null;
        }
        if (groupInformation[5].length() > 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
            this.establishmentDate = LocalDate.parse(groupInformation[5], dtf).atStartOfDay();
        } else {
            this.establishmentDate = null;
        }
        this.genre = MusicGenre.valueOf(groupInformation[6]);
        long tracks;
        if (groupInformation.length > 10 && groupInformation[10].length() > 0) {
            tracks = 0;
            try {
                tracks = Long.parseLong(groupInformation[10]);
            } catch (Exception e) {
                tracks = 1;
            }
        } else {
            tracks = 1;
        }
        int length;
        if (groupInformation.length > 11 && groupInformation[11].length() > 0) {
            try {
                length = Integer.parseInt(groupInformation[11]);
            } catch (Exception e) {
                length = 1;
            }
        } else {
            length = 1;
        }
        this.bestAlbum = new Album(groupInformation[9], tracks, length);
    }


    public MusicBand() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEstablishmentDate(LocalDateTime establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return this.creationDate;
    }

    public Integer getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getEstablishmentDate() {
        return this.establishmentDate;
    }

    public MusicGenre getGenre() {
        return this.genre;
    }

    public Album getBestAlbum() {
        return this.bestAlbum;
    }

    public void update(MusicBand musicBand) {
        this.name = musicBand.name;
        this.coordinates = musicBand.coordinates;
        this.numberOfParticipants = musicBand.numberOfParticipants;
        this.description = musicBand.description;
        this.establishmentDate = musicBand.establishmentDate;
        this.genre = musicBand.genre;
        this.bestAlbum = musicBand.bestAlbum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand musicBand = (MusicBand) o;
        return Objects.equals(id, musicBand.id) && similar(musicBand);
    }
    /**
     * Like equals but id doesn't matter
     * @param musicBand - StudyGroup object to compare
     * @return true if all fields of objects are equal
     */
    public boolean similar(MusicBand musicBand) {
        return Objects.equals(name, musicBand.name) &&
                Objects.equals(coordinates, musicBand.coordinates) &&
                Objects.equals(creationDate, musicBand.creationDate) &&
                Objects.equals(numberOfParticipants, musicBand.numberOfParticipants) &&
                Objects.equals(description, musicBand.description) &&
                Objects.equals(establishmentDate, musicBand.establishmentDate) &&
                genre == musicBand.genre &&
                Objects.equals(bestAlbum, musicBand.bestAlbum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, numberOfParticipants, description, establishmentDate, genre, bestAlbum);
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate.toString() +
                ", numberOfParticipants=" + numberOfParticipants +
                ", description=" + description +
                ", establishmentDate=" + establishmentDate +
                ", genre=" + genre.toString() +
                ", bestAlbum=" + bestAlbum.toString() +
                '}';
    }

    @Override
    public int compareTo(MusicBand o) {
        return name.compareTo(o.name);
    }

}