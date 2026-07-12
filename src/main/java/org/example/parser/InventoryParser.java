package org.example.parser;

import org.example.model.Part;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        String category = normalizeCategory(rawCategory);
        java.time.LocalDate dateAdded = DateUtil.parseFlexibleDate(rawDate);
        int quantity;
        try {
            quantity = Integer.parseInt(rawQuantity.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid quantity: " + rawQuantity);
        }

        try {
            return new Part(code, name, supplier, price, quantity, category, dateAdded, imagePath);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid Part data in line: " + line + " (" + e.getMessage() + ")");
        }
    }

    public static List<Part> parseFile(List<String> lines) {
        List<Part> parts = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (String line : lines) {
            try {
                parts.add(parseLine(line));
            } catch (Exception e) {
                errors.add(line + " | REASON: " + e.getMessage());
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("parse_errors.txt", true))) {
            for (String err : errors) {
                pw.println(err);
            }
        } catch (IOException ioe) {
            System.err.println("Could not write parse_errors.txt: " + ioe.getMessage());
        }

        return parts;
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
        return ',';
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

    private static String normalizeCategory(String raw) {
        String c = raw.trim().toUpperCase();
        switch (c) {
            case "ELEC":
            case "ELECTRONIC":
                return "ELECTRONICS";
            case "HW":
            case "HARDWARE ITEM":
                return "HARDWARE";
            default:
                return c;
        }
    }

}
