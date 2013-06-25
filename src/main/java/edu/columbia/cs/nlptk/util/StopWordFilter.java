package edu.columbia.cs.nlptk.util;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: gbarber205
 * Date: 6/11/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class StopWordFilter {

    private static HashSet<String> stopwords;

    private static Pattern allPunctuation = Pattern.compile("^\\p{Punct}+$");

    static {
        try {
            stopwords = new HashSet<String>();
            InputStream input = ClassLoader.getSystemResourceAsStream("stopwords.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                stopwords.add(line.intern());
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException ioe) {
            System.err.println("StopWordFilter failed to locate 'stopwords.txt'.");
            ioe.printStackTrace();
            System.exit(-1);
        }
    }

    public static String filter( String word ){        //Stop word removal
        if (stopwords.contains(word)) {
            return "";
        }
        else
            return word;
    }


    public static CoreLabel[] filterCoreLabels(CoreLabel[] coreLabels) {

        ArrayList<CoreLabel> filteredLabels = new ArrayList<CoreLabel>(coreLabels.length);

        for(int i = 0; i < coreLabels.length; i++) {

            CoreLabel cl = coreLabels[i];
            Matcher matcher = allPunctuation.matcher(cl.originalText());

            if (!stopwords.contains(cl.originalText().toLowerCase())
                    && !cl.tag().equals("POS")
                    && !matcher.matches()) {

                filteredLabels.add(cl);
            }

        }

        CoreLabel[] coreLabelArray = new CoreLabel[filteredLabels.size()];
        for(int i = 0; i < filteredLabels.size(); i++) {
            coreLabelArray[i] = filteredLabels.get(i);

        }

        return coreLabelArray;

    }

    public static void filterWordsCaseInsensitive(Collection<String> words) {

        Iterator<String> iterator = words.iterator();
        while(iterator.hasNext()) {
            String word = iterator.next();
            if (stopwords.contains(word))
                iterator.remove();
        }

    }


}