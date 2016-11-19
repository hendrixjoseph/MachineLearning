package edu.wright.hendrix11.cs7830;

import edu.wright.hendrix11.cs7830.tools.StringParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

/**
 * @author Joe Hendrix
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        DowData data = new DowData("dow_jones_index.csv");
    }
}
