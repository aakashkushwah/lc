package com.example.lc.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    private Utils() {}

    /**
     * Convert a raw string into HTML paragraphs.
     * Behavior:
     * - If input is null or empty (after trimming) returns an empty string.
     * - Split the input into paragraphs on two or more newlines.
     * - Within a paragraph, single newlines are converted to <br/> to preserve line breaks.
     * - Each paragraph is wrapped with <p>...</p> and concatenated (no extra wrapper).
     *
     * Examples:
     * "Line1\nLine2\n\nPara2" -> "<p>Line1<br/>Line2</p><p>Para2</p>"
     */
    public static String prettifyStringIntoPara(String str) {
        if (str == null) return "";
        String trimmed = str.trim();
        if (trimmed.isEmpty()) return "";

        // Split on two or more newlines (handles \r\n and \n)
        String[] paragraphs = trimmed.split("(\\r?\\n){2,}");
        StringBuilder sb = new StringBuilder();
        for (String p : paragraphs) {
            String t = p.trim();
            if (t.isEmpty()) continue;
            // Replace single newlines within a paragraph with <br/>
            t = t.replaceAll("\\r?\\n", "<br/>");
            sb.append("<p>").append(t).append("</p>");
        }
        return sb.toString();
    }

    /**
     * Extracts the value of the "response" field from an Ollama JSON response string.
     * Expected input format (example):
     * {"model":"llama3","created_at":"2025-11-08T16:50:34.636060047Z","response":"Here","done":false}
     *
     * Implementation notes:
     * - First attempts to parse the input as JSON using Jackson's ObjectMapper and return the
     *   value of the "response" key if present.
     * - If Jackson parsing fails (malformed JSON, streaming fragments, etc.), falls back to a
     *   regex that tries to capture a quoted "response" value.
     * - Returns null if the input is null or the response field cannot be found.
     */
    public static String getResponseFromOllamaResponse(String input) {
        if (input == null) return null;
        String strs[] = input.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            String res = null;
            String trimmed = str.trim();
            if (trimmed.isEmpty()) return null;

            // Try to parse JSON using Jackson (preferred - handles escaping properly)
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(trimmed, new TypeReference<Map<String, Object>>() {});
                if (map.containsKey("response")) {
                    Object val = map.get("response");
                    res = (val == null ? null : String.valueOf(val));
                }
            } catch (Exception ignored) {
                // fall through to regex fallback
            }
            if(res != null) {
                sb.append(res);
            }
            if(res == null) {
                // Fallback: try to capture a simple quoted value for "response" using regex.
                // This handles cases like: "response":"Here" (doesn't fully unescape JSON escapes).
                Pattern p = Pattern.compile("\"response\"\\s*:\\s*\"(.*?)\"");
                Matcher m = p.matcher(trimmed);
                if (m.find()) {
                    String found = m.group(1);
                    // Basic unescape of common sequences (this won't handle all JSON escapes, but helps simple cases)
                    found = found.replaceAll("\\\\\"", "\"") // \" -> "
                            .replaceAll("\\\\n", "\n")    // \n -> newline
                            .replaceAll("\\\\r", "\r")    // \r -> carriage return
                            .replaceAll("\\\\t", "\t");   // \t -> tab
                    sb.append(found);
                }
            }

        }



        return sb.toString();
    }
}
