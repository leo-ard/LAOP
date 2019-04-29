package org.lrima.laop.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Utility class to create CSV files
 *
 * @author Léonard
 */
public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';

    /**
     * Write a line in the csv file
     *
     * @param w the writer
     * @param values the values to write
     * @throws IOException if an error occurs when writing to the file
     */
    public static void writeLine(Writer w, Collection<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, '"');
    }


    private static String followCVSformat(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }

    private static void writeLine(Writer w, Collection<String> values, char separators, char customQuote) throws IOException {
        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }

    /**
     * Create a CSV file defined in the consumer parsed in parameters
     *
     * @param file the file
     * @param bufferedWriterConsumer the consumer
     */
    public static void createCSVFile(File file, Consumer<BufferedWriter> bufferedWriterConsumer){
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriterConsumer.accept(bufferedWriter);

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}