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

        String code        = getField(fields, 0, "UNKNOWN");
        String name         = getField(fields, 1, "UNKNOWN");
        String supplier     = getField(fields, 2, "UNKNOWN");
        String rawPrice     = getField(fields, 3, "0");
        String rawQuantity  = getField(fields, 4, "0");
        String rawCategory  = getField(fields, 5, "UNCATEGORIZED");
        String rawDate      = getField(fields, 6, "");
        String imagePath    = getField(fields, 7, "");

        double price = parsePrice(rawPrice);

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

    private static double parsePrice(String raw) throws ParseException {
        String cleaned = raw.replaceAll("[^0-9.]", "");
        if (cleaned.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid price: " + raw);
        }
    }

    private static String getField(String[] fields, int idx, String defaultVal) {
        if (idx >= fields.length || fields[idx].isBlank()) {
            return defaultVal;
        }
        return fields[idx];
    }


}
