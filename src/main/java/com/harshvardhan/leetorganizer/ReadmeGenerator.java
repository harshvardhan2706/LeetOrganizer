package com.harshvardhan.leetorganizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReadmeGenerator {

    private final Path repositoryPath;

    public ReadmeGenerator(String repositoryPath) {
        this(Paths.get(Objects.requireNonNull(repositoryPath, "repositoryPath cannot be null")));
    }

    public ReadmeGenerator(Path repositoryPath) {
        this.repositoryPath = Objects.requireNonNull(repositoryPath).toAbsolutePath();
    }

    public void generate() {
        Console.printInfo("Generating README...");

        try {
            Statistics statistics = loadStatistics();
            String content = buildReadme(statistics);
            Path readmePath = repositoryPath.resolve("README.md");

            Files.writeString(
                    readmePath,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            Console.printSuccess("README Generated.");
        } catch (IOException e) {
            Console.printError("Failed to generate README.");
            e.printStackTrace();
        }
    }

    private Statistics loadStatistics() throws IOException {
        Map<String, List<String>> languageProblems = new TreeMap<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(repositoryPath)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    continue;
                }

                String language = path.getFileName().toString();
                if (language.startsWith(".") || isIgnoredReadmeFolder(language)) {
                    continue;
                }

                List<String> problems = getProblems(path);
                if (!problems.isEmpty()) {
                    languageProblems.put(language, problems);
                }
            }
        }

        return new Statistics(languageProblems);
    }

    private boolean isIgnoredReadmeFolder(String folderName) {
        return folderName.equals(".git")
                || folderName.equals(".github")
                || folderName.equals(".idea")
                || folderName.equals(".vscode")
                || folderName.equals("target")
                || folderName.equals("src");
    }

    private List<String> getProblems(Path languageFolder) throws IOException {
        List<String> problems = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(languageFolder)) {
            for (Path path : stream) {
                if (Files.isDirectory(path) && !path.getFileName().toString().startsWith(".")) {
                    problems.add(path.getFileName().toString());
                }
            }
        }

        return problems.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private String buildReadme(Statistics statistics) {
        StringBuilder readme = new StringBuilder();
        appendHeader(readme);
        appendStatistics(readme, statistics);
        appendLanguages(readme, statistics);
        appendFooter(readme);
        return readme.toString();
    }

    private void appendHeader(StringBuilder readme) {
        readme.append("# 🚀 LeetCode Solutions\n\n");
        readme.append("This repository contains my **LeetCode** solutions ")
                .append("organized automatically using **LeetOrganizer**.\n\n");
        readme.append("---\n\n");
    }

    private void appendStatistics(StringBuilder readme, Statistics statistics) {
        readme.append("## 📊 Statistics\n\n");
        readme.append("| Language | Problems |\n");
        readme.append("|----------|---------:|\n");

        for (Map.Entry<String, List<String>> entry : statistics.getLanguageProblems().entrySet()) {
            readme.append("| ")
                    .append(entry.getKey())
                    .append(" | ")
                    .append(entry.getValue().size())
                    .append(" |\n");
        }

        readme.append("\n");
        readme.append("**Total Solved : ")
                .append(statistics.getTotalProblems())
                .append("**\n\n");
        readme.append("---\n\n");
    }

    private void appendLanguages(StringBuilder readme, Statistics statistics) {
        for (Map.Entry<String, List<String>> entry : statistics.getLanguageProblems().entrySet()) {
            String language = entry.getKey();
            List<String> problems = entry.getValue();

            readme.append("## ")
                    .append(getEmoji(language))
                    .append(" ")
                    .append(language)
                    .append("\n\n");

            for (String problem : problems) {
                readme.append("- ")
                        .append(formatProblemName(problem))
                        .append("\n");
            }
            readme.append("\n");
        }
    }

    private void appendFooter(StringBuilder readme) {
        readme.append("---\n\n");
        readme.append("Generated automatically by **LeetOrganizer** ❤️\n\n");
        readme.append("Last Updated : ");
        readme.append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        readme.append("\n");
    }

    private String getEmoji(String language) {
        return switch (language) {
            case "Java" -> "☕";
            case "SQL" -> "🗄";
            case "Python" -> "🐍";
            case "Cpp" -> "⚙";
            case "JavaScript" -> "📜";
            case "TypeScript" -> "🟦";
            case "Go" -> "🐹";
            case "Rust" -> "🦀";
            case "Kotlin" -> "🧡";
            default -> "📁";
        };
    }

    private String formatProblemName(String folderName) {
        String name = folderName;
        int firstDash = name.indexOf('-');
        if (firstDash == -1) {
            return folderName;
        }

        String number = name.substring(0, firstDash);
        String title = name.substring(firstDash + 1).replace("-", " ");
        String[] words = title.split(" ");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            formatted.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                formatted.append(word.substring(1));
            }
            formatted.append(" ");
        }

        return number + " - " + formatted.toString().trim();
    }
}