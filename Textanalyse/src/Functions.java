import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Functions {


    // a) Zeilen, in denen ein Text vorkommt
    //import file -> check for word by usimg method filter(.contains )the convert to Optional<String> by
    //using .findany()
    static Optional<String> findLine(String inFile, String text) throws IOException {
        Stream<String> line = Files.lines(Paths.get(inFile));
        Optional<String> search = line.filter(lines->lines.contains(text)).findAny();
        line.close();
        return search;
    }
    //import file -> check for word by usimg method .contains the convert to list()
    //by using Collectors.toList()
    static List<String> findLines(String inFile, String text) throws IOException {
        List<String> list;
        Stream<String> line = Files.readAllLines(Paths.get(inFile))
                .stream()
                .filter(lines -> lines.contains(text));
        list = line.collect(Collectors.toList());
        line.close();
        return list;
    }


    //(b) Zeilen ohne Leerzeilen schreiben
    //import file-> convert to stream -> delete empty spaces -> write the output file
    static void writeNoEmptyLines(String inFile, String outFile) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(inFile)));
        String adjusted = data.replaceAll("(?m)^[ \t]*\r?\n", "");
        //write the new file using the path of the file
        Files.write(Paths.get(outFile), adjusted.getBytes(), StandardOpenOption.CREATE);

    }


    // c) Alle Wörter
    //import -> split  -> to stream Array  -> words that bigger than 0  -> to lower case  -> sort words
    static Stream<String> wordStream(String inFile) throws IOException {
        Stream<String> line = Files.lines(Paths.get(inFile));
        Stream<String> wordStream = line.map(lines -> lines.split("[ .,;?!.:()-]"))
                .flatMap(array -> Arrays.stream(array))
                .filter(word -> word.length() > 0)
                .map(word -> word.toLowerCase())
                .distinct().sorted();
        return wordStream;
    }
 //import  -> apply method wordStream -> collecting as a list  using Collectors
    static List<String> words(String inFile) throws IOException {
        List<String> words = Functions
                .wordStream(inFile)
                .collect(Collectors.toList());
        return words;
    }


    //d)Durchschnitt:
    //import file -> set as stream collect values using the length of each line by using Collectors
    static double averageLineLength(String inFile) throws IOException {
        Stream<String> line = Files.lines(Paths.get(inFile));
        double avg = line
                .collect(Collectors.averagingDouble(String::length));
        line.close();
        return avg;
    }
    //import file -> set as stream -> split the lines to words
    // -> collect values using the length of each word by using Collectors of String arrays
    static double averageWordsLength(String inFile) throws IOException {
        double avg = Files.lines(Paths.get(inFile)).map(s -> s.split("[ .,;?!.:()-]"))
                .filter(s->!s.equals(" "))
                .collect(Collectors.averagingDouble(Functions::applyAsDouble));
        return avg;
    }


    //e) Gruppierung der Wörter nach Anfangsbuchstaben
    //import file -> set as stream -> split the lines to words using method flatMap
    //-> collect the values and keys using the method Collectors.groupingBy
    static Map<Character, List<String>> alphaGrouping(String inFile) throws IOException {
        Map<Character, List<String>> alphabet =
                Files.lines(Paths.get(inFile)).
                flatMap(s -> Stream.of(s.split("[ .,;?!.:()-]"))).
                filter(s -> s.length() > 0).
                map(s -> s.toLowerCase()).
                collect(Collectors.groupingBy(s -> s.charAt(0)));
        return alphabet;
    }

    private static double applyAsDouble(String[] s) {
        return s.length;
    }
}
