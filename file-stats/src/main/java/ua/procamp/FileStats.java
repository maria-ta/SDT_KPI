package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    //TODO:?
    private final Map<Character, Long> characterCountMap;
    private final char mostPopularCharacter;

    private FileStats(String fileName) {
        Path filePath = getFilePath(fileName);
        characterCountMap = getCharacterMap(filePath);
        mostPopularCharacter = getMostPopularCharacter(characterCountMap);
    }

    private URL getFileUrl(String fileName) {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl == null) {
            throw new FileStatsException("Wrong file path");
        }
        return fileUrl;
    }

    private Path getFilePath(String fileName) {
        try {
            Objects.requireNonNull(fileName);
            URL fileUrl = getFileUrl(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException | NullPointerException e) {
            throw new FileStatsException("Wrong file path", e);
        }
    }

    private Map<Character, Long> getCharacterMap(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return collectCharactersToCountMap(lines);
        } catch (IOException e) {
            throw new FileStatsException("Cannot read the file", e);
        }
    }

    private Map<Character, Long> collectCharactersToCountMap(Stream<String> linesStream) {
        return linesStream
                        .flatMapToInt(String::chars).
                        filter(a -> a != 32).
                        mapToObj(c -> (char) c).
                        collect(groupingBy(identity(), counting()));
    }

    private char getMostPopularCharacter(Map<Character, Long> characterCountMap) {
        return characterCountMap.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new FileStatsException("File is empty"))
                .getKey();
    }

    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        return new FileStats(fileName);
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        //throw new UnsupportedOperationException("It's your job to make it work!"); //todo
        return characterCountMap.get(character).intValue();
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        //throw new UnsupportedOperationException("It's your job to make it work!"); //todo
        return mostPopularCharacter;
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        //throw new UnsupportedOperationException("It's your job to make it work!"); //todo
        return characterCountMap.containsKey(character);
    }
}
