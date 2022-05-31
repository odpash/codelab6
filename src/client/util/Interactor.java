
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
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Interactor {
    private static final int MIN_X = -512;
    private static final int MIN_WEIGHT = 0;
    private static final int MIN_PASSPORT_ID_LENGTH = 4;
    private static final int MAX_LOCATION_NAME_LENGTH = 272;
    private static final int MIN_STUDENTS_COUNT = 0;
    private static final int MIN_EXPELLED_STUDENTS = 0;
    private static final int MIN_SHOULD_BE_EXPELLED = 0;
    private static Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

    static boolean fileMode = false;


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


    public static int askX(Scanner scanner, boolean withLimit) throws IncorrectInputInScriptException {
        String strX = "";
        int x;
        while (true) {
            try {
                if (withLimit)
                    System.out.println("Введите координату X > " + MIN_X + ":");
                else
                    System.out.println("Введите координату X:");
                displayInput();
                strX = scanner.nextLine().trim();
                if (fileMode) System.out.println(strX);
                x = Integer.parseInt(strX);
                if (withLimit && x <= MIN_X) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                printError("Координата X должна быть в диапазоне (" + (withLimit ? MIN_X : Integer.MIN_VALUE)
                        + ";" + Integer.MAX_VALUE + ")!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strX).matches())
                    printError("Координата X должна быть в диапазоне (" + (withLimit ? MIN_X : Integer.MIN_VALUE)
                            + ";" + Integer.MAX_VALUE + ")!");
                else
                    printError("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }


    public static long askY(Scanner scanner) throws IncorrectInputInScriptException {
        String strY = "";
        long y;
        while (true) {
            try {
                System.out.println("Введите координату Y:");
                displayInput();
                strY = scanner.nextLine().trim();
                if (fileMode) System.out.println(strY);
                y = Long.parseLong(strY);
                break;
            } catch (NoSuchElementException exception) {
                printError("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strY).matches())
                    printError("Координата Y должна быть в диапазоне (" + Long.MIN_VALUE
                            + ";" + Long.MAX_VALUE + ")!");
                else
                    printError("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }


    public static Coordinates askCoordinates(Scanner scanner) throws IncorrectInputInScriptException {
        int x = askX(scanner, true);
        long y = askY(scanner);
        return new Coordinates(x, y);
    }


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

    public static Album askBestAlbum(Scanner scanner) throws IncorrectInputInScriptException {
        String name = askAlbumName(scanner);
        long tracks = askTracks(scanner);
        int length = askLength(scanner);
        return new Album(name, tracks, length);
    }


    public static String askAlbumName(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите название альбома:", 0, Integer.MAX_VALUE);
    }


    public static String askLocationName(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите имя локации:", 0, MAX_LOCATION_NAME_LENGTH);
    }


    public static String askBandName(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите имя группы:", 0, Integer.MAX_VALUE);
    }


    public static String askDescription(Scanner scanner) throws IncorrectInputInScriptException {
        return askName(scanner, "Введите описание:", 0, Integer.MAX_VALUE);
    }


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

    public static LocalDateTime askEstablishmentTime(Scanner scanner) throws IncorrectInputInScriptException {
        String element = "";
        while (true) {
            try {
                System.out.println("Введите дату публикации:");
                displayInput();
                element = scanner.nextLine().trim();
                if (fileMode) System.out.println(element);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
                return LocalDate.parse(element, dtf).atStartOfDay();

            } catch (NoSuchElementException exception) {
                printError("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(element).matches())
                    printError("Число должно быть в диапазоне (" + MIN_WEIGHT + ";" + Long.MAX_VALUE + ")!");
                else
                    printError("Количество должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }


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
                if (weight <= MIN_WEIGHT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strWeight).matches())
                    printError("Число должно быть в диапазоне (" + MIN_WEIGHT + ";" + Long.MAX_VALUE + ")!");
                else
                    printError("Длина должна быть представлена числом!");
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


    public static boolean askQuestion(Scanner scanner, String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                displayInput();
                answer = scanner.nextLine().trim();
                if (fileMode) System.out.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                printError("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                printError("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
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