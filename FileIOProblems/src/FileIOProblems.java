import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileIOProblems
{

    public static void main(String[] args) throws IOException
    {
        writeDataToFile("myFile.txt", "This is the contents of the file!");
        String fileContents = readFile("TextDataFiles/mobydick.txt");

        int beforeLongestWord = fileContents.indexOf("uninterpenetratingly");
        String message = fileContents.substring(beforeLongestWord - 100, beforeLongestWord + 100);

        System.out.println(message);


        String [] words = fileContents.split("\n");

        for(int i = 0; i < words.length; i++)
        {
            words[i] = words[i].trim();
        }



        String stripped = fileContents.replaceAll("[^a-zA-Z]", " ");
        String [] words0 = stripped.split(" ");

        String longest = getLongestMobyDickWord(words0);
        System.out.println(longest);

        ArrayList<String> words1 = getNLetterWords(words, 3);
        ArrayList<String> words2 = getNLetterWords(words, 12);

        ArrayList<String> words3 = getwordsWithout(words, "e");
        ArrayList<String> words4 = getwordsWithout(words, "a");

        // System.out.println("File contains: " + data);
        // System.out.println("Length: " + data.length());
    }

    private static String getLongestMobyDickWord(String [] words0)
    {
        String longest = words0[0];

        for(int i = 1; i < words0.length; i++)
        {
            if(words0[i].length() >= longest.length())
            {
                longest = words0[i];
            }
        }

        return longest;
    }

    private static ArrayList<String> getwordsWithout(String [] words, String forbiddenLetter)
    {
        ArrayList<String> wordsWithout = new ArrayList<>();

        for(int i = 0; i < words.length; i++)
        {
            if(words[i].contains(forbiddenLetter) == false)
            {
                wordsWithout.add(words[i]);
            }
        }

        return wordsWithout;
    }

    private static ArrayList<String> getNLetterWords(String [] words, int n)
    {
        ArrayList<String> rightLetters = new ArrayList<>();

        for(int i = 0; i < words.length; i++)
        {
            if(words[i].length() == n)
            {
                rightLetters.add(words[i]);
            }
        }

        return rightLetters;
    }

    public static void writeDataToFile(String filePath, String data) throws IOException
    {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);)
        {
            writer.println(data);
        } catch (IOException error)
        {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
