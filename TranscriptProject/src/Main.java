import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main
{
    static ArrayList<String> speakers = new ArrayList<>();

    public static void main(String [] args) throws IOException
    {
        String fileContents = readFile("data/sample01.vtt");
        String [] lines = fileContents.split("\n");

        writeSummaryStatisticsFile("SummaryStatistics.txt", lines);
        writeCondensedTranscriptFile("CondensedTranscript.txt", lines);
    }

    // read the file
    public static String readFile(String fileName) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    // get all the people from the file
    public static void getPeople(String [] lines)
    {
        String name = "";
        
        for(int i = 4; i < lines.length; i += 4)
        {
            int colon = lines[i].indexOf(":");

            if(colon != -1)
            {
                name = lines[i].substring(0, colon);
            }

            if(! speakers.contains(name))
            {
                speakers.add(name);
            }
        }
    }

    // get total talking time for a specific person
    public static double getTotalTalkingTimeForPerson(String name, String [] lines)
    {
        double talkingTime = 0;
        String speaker = "";

        for(int j = 3; j < lines.length; j += 4)
        {
            int colon = lines[j + 1].indexOf(":");

            if(colon != -1)
            {
                speaker = lines[j + 1].substring(0, colon);
            }

            if(speaker.equals(name))
            {
                talkingTime += getTalkingTime(lines, j);
            }
        }

        return talkingTime;
    }

    // get talking time given the line number
    public static double getTalkingTime(String [] lines, int line)
    {
        String hrs = lines[line].substring(0, 2);
        String mins = lines[line].substring(3, 5);
        String secs = lines[line].substring(6, 8);
        String milliSecs = lines[line].substring(9, 12);

        int hrsToSecs = Integer.parseInt(hrs) * 3600;
        int minsToSecs = Integer.parseInt(mins) * 60;
        int secs2 = Integer.parseInt(secs);
        double secsToMillisecs = Integer.parseInt(milliSecs) / 1000.00;

        double totalStartingSecs = hrsToSecs + minsToSecs + secs2 + secsToMillisecs;

        String endHrs = lines[line].substring(17, 19);
        String endMins = lines[line].substring(20, 22);
        String endSecs = lines[line].substring(23, 25);
        String endMilliSecs = lines[line].substring(26, 29);

        int endHrsToSecs = Integer.parseInt(endHrs) * 3600;
        int endMinsToSecs = Integer.parseInt(endMins) * 60;
        int endSecs2 = Integer.parseInt(endSecs);
        double endSecsToMillisecs = Integer.parseInt(endMilliSecs) / 1000.00;

        double totalEndingSecs = endHrsToSecs + endMinsToSecs + endSecs2 + endSecsToMillisecs;

        double talkingTime = (totalEndingSecs - totalStartingSecs) / 60;

        return talkingTime;
    }

    // get the total talking time of all people
    public static double getTotalTalkingTime(String [] lines)
    {
        double totalTime = 0;

        for(int i = 3; i < lines.length; i += 4)
        {
            totalTime += getTalkingTime(lines, i);
        }

        return totalTime;
    }

    // get the percent of time a person talks for
    public static double getPercentTalkingTimeForPerson(String name, String [] lines)
    {
        double totalPersonTalkingTime = getTotalTalkingTimeForPerson(name, lines);
        double totalTalkingTime = getTotalTalkingTime(lines);

        return (totalPersonTalkingTime / totalTalkingTime) * 100;
    }

    // get average length of speech event for a specific person
    public static double getAverageSpeechLengthForPerson(String name, String [] lines)
    {
        int numTimesTalked = 0;
        String speaker = "";

        for(int i = 4; i < lines.length; i += 4)
        {
            int colon = lines[i].indexOf(":");

            if(colon != -1)
            {
                speaker = lines[i].substring(0, colon);
            }

            if(speaker.equals(name))
            {
                numTimesTalked++;
            }
        }

        double totalTalkedTime = getTotalTalkingTimeForPerson(name, lines);

        return totalTalkedTime / numTimesTalked;
    }

    // get the total length of session
    public static double getTotalLengthOfSession(String [] lines)
    {
        String hrs = lines[3].substring(0, 2);
        String mins = lines[3].substring(3, 5);
        String secs = lines[3].substring(6, 8);
        String milliSecs = lines[3].substring(9, 12);

        int hrsToSecs = Integer.parseInt(hrs) * 3600;
        int minsToSecs = Integer.parseInt(mins) * 60;
        int secs2 = Integer.parseInt(secs);
        double secsToMillisecs = Integer.parseInt(milliSecs) / 1000.00;

        double totalStartingSecs = hrsToSecs + minsToSecs + secs2 + secsToMillisecs;

        String endHrs = lines[lines.length - 2].substring(17, 19);
        String endMins = lines[lines.length - 2].substring(20, 22);
        String endSecs = lines[lines.length - 2].substring(23, 25);
        String endMilliSecs = lines[lines.length - 2].substring(26, 29);

        int endHrsToSecs = Integer.parseInt(endHrs) * 3600;
        int endMinsToSecs = Integer.parseInt(endMins) * 60;
        int endSecs2 = Integer.parseInt(endSecs);
        double endSecsToMillisecs = Integer.parseInt(endMilliSecs) / 1000.00;

        double totalEndingSecs = endHrsToSecs + endMinsToSecs + endSecs2 + endSecsToMillisecs;

        return (totalEndingSecs - totalStartingSecs) / 60;
    }

    // get the number of speaker switches throughout the file
    public static int getTotalSpeakerSwitches(String [] lines)
    {
        int numSwitches = 0;
        String speaker = "";
        String speaker2 = "";

        for(int i = 8; i < lines.length; i += 4)
        {
            int colon = lines[i].indexOf(":");

            if(colon != -1)
            {
                speaker = lines[i].substring(0, colon);
            }

            int colonBefore = lines[i - 4].indexOf(":");

            if(colonBefore != -1)
            {
                speaker2 = lines[i - 4].substring(0, colonBefore);
            }

            if(speaker.equals(speaker2) == false)
            {
                numSwitches++;
            }
        }

        return numSwitches;
    }

    // write the condensed transcript
    public static String getCondensedTranscript(String [] lines)
    {
        String file = "";
        int i = 4;

        while(i < lines.length)
        {
            file += lines[i].substring(0, (lines[i].indexOf(":") + 1)) + "\n";

            file += lines[i].substring(lines[i].indexOf(":") + 2);

            i += 4;

            if((lines[i].indexOf(":") != -1) && (lines[i - 4].indexOf(":") != -1))
            {
                while(i < lines.length && lines[i].substring(0, (lines[i].indexOf(":"))).equals(lines[i - 4].substring(0, lines[i].indexOf(":"))))
                {
                    file += lines[i].substring(lines[i].indexOf(":") + 1);

                    i += 4;
                }
            }

            file += "\n\n";
        }

        return file;
    }

    // write the statistics to a file
    public static void writeSummaryStatisticsFile(String filePath, String [] lines) throws IOException
    {
        String out = "";
        getPeople(lines);

        out += "Total # People: " + speakers.size() + "\n";
        out += "Total Length of Session: " + Math.round(getTotalLengthOfSession(lines) * 100.0) / 100.0 + " min" + "\n";
        out += "Total Speaking Time: " + Math.round(getTotalTalkingTime(lines) * 100.0) / 100.0 + " min" + "\n";
        out += "Total # of Speaker Switches: " + getTotalSpeakerSwitches(lines) + "\n";
        out += "\n";
        out += "Total Talk Time: " + "\n";

        for(int i = 0; i < speakers.size(); i++) {
            String name = speakers.get(i);
            out += name + ": " + Math.round(getTotalTalkingTimeForPerson(name, lines) * 100.0) / 100.0 + " min - " + Math.round(getPercentTalkingTimeForPerson(name, lines) * 100.0) / 100.0 + "%" + "\n";
        }

        out += "\n";
        out += "Average Length of a Speech Event:" + "\n";

        for(int i = 0; i < speakers.size(); i++) {
            String name = speakers.get(i);
            out += name + ": " + Math.round(getAverageSpeechLengthForPerson(name, lines) * 100.0) / 100.0 + " min" + "\n";
        }

        writeDataToFile(filePath, out);
    }

    // write the condensed transcript to a file
    public static void writeCondensedTranscriptFile(String filePath, String [] lines) throws IOException
    {
        String out = "";

        out += getCondensedTranscript(lines);

        writeDataToFile(filePath, out);
    }

    // function to write data to a file
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
}
