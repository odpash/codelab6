
package client.util;

import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInBoundsException;
import common.model.Album;
import common.model.Coordinates;
import common.model.MusicGenre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Interactor {
    private static final int MAX_Y = 512;
    private static final int MIN_WEIGHT = 0;
    private static final int MAX_LOCATION_NAME_LENGTH = 272;
    private static final int MIN_STUDENTS_COUNT = 0;
    private static Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

    static boolean fileMode = false;

    /**
     * Asks a user the name.
     *
     */
    public static String askName(Scanner scanner, String inputTitle, int minLength, int maxLength) throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                System.out.println(inputTitle);
                displayInput();
                name = scanner.nextLine().trim();
                if (fileMode) System.out.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                if (name.length() <= minLength) throw new NotInBoundsException();
                if (name.length() >= maxLength) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                printError("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                printError(String.format("Длина строки не входит в диапазон (%d; %d)", minLength, maxLength));
            }
        }
        return name;
    }

    /**
     * Asks a user the X coordinate.
     *
     * @param withLimit set bounds for X
     * @return X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public static int askX(Scanner scanner, boolean withLimit) throws IncorrectInputInScriptException {
        String strX = "";
        int x;
        while (true) {
            try {
                if (withLimit)
                    System.out.println("Введите координату X:");
                else
                    System.out.println("Введите координату Y: <" + MAX_Y );
                displayInput();
                strX = scanner.nextLine().trim();
                if (fileMode) System.out.println(strX);
                x = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException exception) {
                printError("Координата X не распознана!");

                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the X coordinate.
     *
     * @param withLimit set bounds for X
     * @return X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public static long askY(Scanner scanner) throws IncorrectInputInScriptException {
        String strY = "";
        long y = 0;
        while (true) {
            try {
                System.out.println("Введите координату Y:");
                displayInput();
                strY = scanner.nextLine().trim();
                if (fileMode) System.out.println(strY);
                y = Long.parseLong(strY);
                if (y > MAX_Y) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NoSuchElementException exception) {
                printError("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                    printError("Координата Y должна быть в диапазоне (" + Long.MIN_VALUE
                            + ";" + MAX_Y + ")!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user coordinates.
     */
    public static Coordinates askCoordinates(Scanner scanner) throws IncorrectInputInScriptException {
        int x = askX(scanner, true);
        long y = askY(scanner);
        return new Coordinates(x, y);
    }

    /**
     * Asks a user genre.
     */
    public static MusicGenre askGenre(Scanner scanner) throws IncorrectInputInScriptException {
        String strFormOfEducation;
        MusicGenre musicGenre = null;
        while (true) {
            try {
                System.out.println("Список жанров - " + MusicGenre.nameList());
                System.out.println("Введите жанр:");
                displayInput();
                strFormOfEducation = scanner.nextLine().trim();
                if (fileMode) System.out.println(strFormOfEducation);
                musicGenre = MusicGenre.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                printError("Жанр не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                printError("Жанра нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return musicGenre;
    }

    /**
     * Asks a user participantsCount.
     */
    public static Integer askParticipantsCount(Scanner scanner) throws IncorrectInputInScriptException {
        String strStudentsCount = "";
        int studentsCount;
        while (true) {
            try {
                System.out.println("Введите количество участников:");
                displayInput();
                strStudentsCount = scanner.nextLine().trim();
                if (fileMode) System.out.println(strStudentsCount);
                studentsCount = Integer.parseInt(strStudentsCount);
                if (studentsCount <= MIN_STUDENTS_COUNT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strStudentsCount).matches())
                    printError("Число должно быть в диапазоне (" + MIN_STUDENTS_COUNT + ";" + Integer.MAX_VALUE + ")!");
                else
                    printError("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                printError("Число должно быть больше " + MIN_STUDENTS_COUNT);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user bestAlbum.
     */
    public static Album askBestAlbum(Scanner scanner) throws IncorrectInputInScriptException {
        String name = askAlbumName(scanner);
        long tracks = askTracks(scanner);
        int length = askLength(scanner);
        return new Album(name, tracks, length);
    }

    /**
     * Asks a user Album name.
     */
    public static String askAlbumName(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите название альбома:", 1, Integer.MAX_VALUE);
    }


    /**
     * Asks a user band name.
     */
    public static String askBandName(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите имя группы:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user description.
     */
    public static String askDescription(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите описание:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user tracks.
     */
    public static long askTracks(Scanner scanner) throws IncorrectInputInScriptException {
        String strWeight = "";
        long weight;
        while (true) {
            try {
                System.out.println("Введите количество треков:");
                displayInput();
                strWeight = scanner.nextLine().trim();
                if (fileMode) System.out.println(strWeight);
                weight = Integer.parseInt(strWeight);
                if (weight <= MIN_WEIGHT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strWeight).matches())
                    printError("Число должно быть в диапазоне (" + MIN_WEIGHT + ";" + Long.MAX_VALUE + ")!");
                else
                    printError("Количество должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                printError("Число должно быть больше " + MIN_WEIGHT);
            }
        }
        return weight;
    }

    /**
     * Asks a user EstablishmentTime.
     */
    public static LocalDateTime askEstablishmentTime(Scanner scanner) throws IncorrectInputInScriptException {
        String element = "";
        DateTimeFormatter dtf;
        LocalDateTime d;
        while (true) {
            System.out.println("Введите дату публикации (yyyy-mm-dd):");
            try {
                displayInput();
                element = scanner.nextLine().trim();
                if (fileMode) System.out.println(element);
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
                d = LocalDate.parse(element, dtf).atStartOfDay();
                break;

            } catch (DateTimeParseException | NoSuchElementException e) {
                printError("Дата не распознана!");
            }
        }
        return d;
    }


    /**
     * Asks a user length.
     */
    public static int askLength(Scanner scanner) throws IncorrectInputInScriptException {
        String strWeight = "";
        int weight;
        while (true) {
            try {
                System.out.println("Введите длину треков:");
                displayInput();
                strWeight = scanner.nextLine().trim();
                if (fileMode) System.out.println(strWeight);
                weight = Integer.parseInt(strWeight);
                if (weight <= 0) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strWeight).matches())
                    printError("Число должно быть в диапазоне (" + 0 + ";" + Long.MAX_VALUE + ")!");
                else
                    printError("Длина должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                printError("Число должно быть больше " + 0);
            }
        }
        return weight;
    }


    public static void println(String str) {
        System.out.println(str);
    }
    public static void printError(String error) {
        System.out.println("ОШИБКА! " + error);
    }

    public static void displayInput() {
        System.out.print("> ");
    }
}