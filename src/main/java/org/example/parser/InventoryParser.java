package org.example.parser;

import org.example.model.Part;

public class InventoryParser {

    public static Part parseLine(String line) throws ParseException {
        if (line == null || line.isBlank()) {
            throw new ParseException("Empty or null line");
        }

        char delim = detectDelimiter(line);

        String[] rawFields = line.split(java.util.regex.Pattern.quote(String.valueOf(delim)), -1);
        String[] fields = new String[rawFields.length];
        for (int i = 0; i < rawFields.length; i++) {
            fields[i] = rawFields[i].trim();
        }

        // TODO Step 4: apply defaults for missing fields
        // TODO Step 5: parse currency -> price
        // TODO Step 6: normalize category
        // TODO Step 7: parse date -> dateAdded

        return null; // placeholder until steps above are wired in
    }

    private static char detectDelimiter(String line) {
        char[] candidates = {',', '|', ';'};
        for (char c : line.toCharArray()) {
            for (char cand : candidates) {
                if (c == cand) {
                    return cand;
                }
            }
        }
        return ','; // fallback default, matches assumptions.md
    }

}
