package com.harshvardhan.leetorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.harshvardhan.leetorganizer.model.Problem;

public class LanguageDetector {

    private static final Map<String, String> EXTENSION_TO_LANGUAGE = Map.ofEntries(
            Map.entry(".java", "Java"),
            Map.entry(".sql", "SQL"),
            Map.entry(".py", "Python"),
            Map.entry(".cpp", "Cpp"),
            Map.entry(".cc", "Cpp"),
            Map.entry(".cxx", "Cpp"),
            Map.entry(".js", "JavaScript"),
            Map.entry(".ts", "TypeScript"),
            Map.entry(".kt", "Kotlin"),
            Map.entry(".go", "Go"),
            Map.entry(".rs", "Rust")
    );

    public String detectLanguage(Problem problem) {
        Objects.requireNonNull(problem, "problem cannot be null");

        try (Stream<Path> files = Files.walk(problem.getFolderPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(String::toLowerCase)
                    .map(this::languageFromFileName)
                    .filter(language -> !language.equals("Unknown"))
                    .findFirst()
                    .orElse("Unknown");
        } catch (IOException e) {
            Console.printError("Failed to detect language for folder: " + problem.getFolderName());
            e.printStackTrace();
            return "Unknown";
        }
    }

    private String languageFromFileName(String fileName) {
        for (Map.Entry<String, String> entry : EXTENSION_TO_LANGUAGE.entrySet()) {
            if (fileName.endsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "Unknown";
    }
}