package edu.columbia.cs.nlptk.util;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 6/23/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderedWordList {

    private final File wordFile;
    private ArrayList<String> wordList = new ArrayList<String>();
    private Set<String> containmentSet = new HashSet<String>();

    public OrderedWordList(File wordFile) {
        this.wordFile = wordFile;

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile), "UTF-8"));

            while(reader.ready()) {
                String line = reader.readLine();
                if (!line.equals("")) {
                    wordList.add(line.intern());
                    containmentSet.add(line.intern());
                }

            }

            reader.close();


        } catch (IOException ioe) {
            System.err.println(this + ": Could not open file: " + wordFile);
            ioe.printStackTrace();
            System.exit(-1);
        }

    }

    public List<String> getWordList() {return wordList;}
    public boolean containsWord(String word) {return containmentSet.contains(word);}
    public int size() {return wordList.size();}
    public String get(int index) {return wordList.get(index);}

}
