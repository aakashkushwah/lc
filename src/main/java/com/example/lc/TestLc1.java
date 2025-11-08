package com.example.lc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TestLc1 {
    public static void main(String[] args) throws IOException {
        var res = new ArrayList<String>();
        try(BufferedReader reader = new BufferedReader(new FileReader("lines.txt"))) {
          res = reader.lines().filter(s -> !s.isBlank()).filter(s -> !s.startsWith("a"))
                  .filter(s -> s.length() > 3).collect(Collectors.toCollection(ArrayList::new));
        }
        res.forEach(System.out::println);
    }

    private boolean matches(String num) {
        return num.matches("[0-9]+");
    }
}
