import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


public class MainApp {

    public static void main(String[] args) throws IOException {
        System.out.println("\n--------------------(Original text)---------------------");

        String source = "faust_1.txt";
        Path path = Paths.get(source);

        Stream<String> text = Files.lines(path);
        text.forEach(System.out::println);
        System.out.println("\n---------------------(findLine)--------------------");
        Optional<String> findInTetx = Functions.findLine(source, "labyrinthisch");

        boolean ispresent = findInTetx.isPresent();
        if (ispresent) {
            System.out.println("word found :");
            System.out.println(findInTetx);
        } else {
            System.out.println("word was not found");
        }
        System.out.println("\n-----------------------(Lines that contains a word)------------------");
        List<String> textlines = Functions.findLines(source, "labyrinthisch");
        textlines.forEach(System.out::println);

        System.out.println("\n--------------------(Remove Empty lines + create new file)---------------------");
        String target = "target.txt";
        Functions.writeNoEmptyLines(source, target);
        Stream<String> target2 = Files.lines(Paths.get(target));
        target2.forEach(System.out::println);

        System.out.println("\n----------------------(wordStream)-------------------");
        Stream<String> streamTest = Functions.wordStream(source);
        streamTest.forEach(System.out::println);

        System.out.println("\n-----------------------(wordList+UpperCase test)------------------");
        List<String> wordslist = Functions.words(source);
        wordslist.stream().map(s -> s.toUpperCase()).forEach(System.out::println);

        System.out.println("\n-----------------------(AVG Line length)------------------");
        double avg = Functions.averageLineLength(source);
        System.out.println("AVG : " + String.format("%.2f", avg));

        System.out.println("\n-----------------------(AVG Word length!!)------------------");
        double avgWord = Functions.averageWordsLength(source);
        System.out.println("AVG : " + String.format("%.2f", avgWord));

        System.out.println("\n-----------------------(alphaGrouping)------------------");
        Map<Character, List<String>> grouping = Functions.alphaGrouping(source);
        for (Map.Entry entry : grouping.entrySet()) {
            System.out.println("Letter: " + entry.getKey() + "; Words : " + entry.getValue());
        }
    }
}
