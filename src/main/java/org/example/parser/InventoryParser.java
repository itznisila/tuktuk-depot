package org.example.parser;

import org.example.model.Part;

public class InventoryParser {

    public static Part parseLine(String line) throws ParseException {
        if (line == null || line.isBlank()) {
            throw new ParseException("Empty or null line");
        }

        // TODO Step 2: detect delimiter
        // TODO Step 3: split and trim fields
        // TODO Step 4: apply defaults for missing fields
        // TODO Step 5: parse currency -> price
        // TODO Step 6: normalize category
        // TODO Step 7: parse date -> dateAdded

        return null; // placeholder until steps above are wired in
    }

    public static void main(String[] args) {
        // match the field order to your Part constructor's real parameter order
        String testLine = "P001,Widget,AcmeCorp,19.99,100,Electronics,2023-01-15,";
        try {
            Part p = parseLine(testLine);
            System.out.println(p);
        } catch (Exception e) {
            System.out.println("Failed to parse: " + e.getMessage());
        }
    }
}
