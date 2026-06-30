package com.harshvardhan.leetorganizer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.harshvardhan.leetorganizer.model.Problem;

public class FolderScanner {

    public List<Problem> scan(String repositoryPath) {
        Objects.requireNonNull(repositoryPath, "repositoryPath cannot be null");

        Path repositoryRoot = Paths.get(repositoryPath).toAbsolutePath();

        if (!Files.isDirectory(repositoryRoot)) {
            throw new IllegalArgumentException("Repository path is not a directory: " + repositoryRoot);
        }

        List<Problem> problems = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(repositoryRoot)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    continue;
                }

                String folderName = path.getFileName().toString();

                if (isIgnoredTopLevelFolder(folderName)) {
                    continue;
                }

                if (!isProblemFolder(path)) {
                    continue;
                }

                problems.add(new Problem(folderName, path));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan repository: " + repositoryPath, e);
        }

        return problems;
    }

    private boolean isIgnoredTopLevelFolder(String folderName) {
        return folderName.startsWith(".")
                || Constants.TOP_LEVEL_IGNORE.contains(folderName);
    }

    private boolean isProblemFolder(Path path) throws IOException {
        if (!Files.isDirectory(path) || path.getFileName().toString().startsWith(".")) {
            return false;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path child : stream) {
                if (Files.isRegularFile(child) && isSolutionFile(child)) {
                    return true;
                }

                if (Files.isDirectory(child) && !child.getFileName().toString().startsWith(".") && containsSolutionFile(child)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean containsSolutionFile(Path directory) throws IOException {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream
                    .filter(Files::isRegularFile)
                    .anyMatch(this::isSolutionFile);
        }
    }

    private boolean isSolutionFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".java")
                || fileName.endsWith(".py")
                || fileName.endsWith(".sql")
                || fileName.endsWith(".cpp")
                || fileName.endsWith(".cc")
                || fileName.endsWith(".cxx")
                || fileName.endsWith(".js")
                || fileName.endsWith(".ts")
                || fileName.endsWith(".kt")
                || fileName.endsWith(".go")
                || fileName.endsWith(".rs");
    }
}