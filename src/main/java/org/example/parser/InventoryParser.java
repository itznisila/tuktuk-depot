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

    public static void main(String[] args) {
        System.out.println(detectDelimiter("P001,Widget|AcmeCorp"));   // expect ,
        System.out.println(detectDelimiter("P002;Widget,AcmeCorp"));   // expect ;
        System.out.println(detectDelimiter("P003|Widget;AcmeCorp"));   // expect |
        System.out.println(detectDelimiter("P004 Widget AcmeCorp"));   // expect , (fallback)
    }
}
