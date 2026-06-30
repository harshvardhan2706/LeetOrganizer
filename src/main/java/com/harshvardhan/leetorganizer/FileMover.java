package com.harshvardhan.leetorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.harshvardhan.leetorganizer.model.Problem;

public class FileMover {

    public boolean moveProblem(Problem problem, Path repositoryRoot, String language) {
        Objects.requireNonNull(problem, "problem cannot be null");
        Objects.requireNonNull(repositoryRoot, "repositoryRoot cannot be null");
        Objects.requireNonNull(language, "language cannot be null");

        if (language.equalsIgnoreCase("Unknown")) {
            Console.printWarning("Skipped (Unknown Language): " + problem.getFolderName());
            return false;
        }

        Path source = problem.getFolderPath();
        Path targetDirectory = repositoryRoot.resolve(language);
        Path targetPath = targetDirectory.resolve(problem.getFolderName());

        try {
            Files.createDirectories(targetDirectory);

            if (Files.exists(targetPath)) {
                Console.printInfo("Skipped duplicate: " + problem.getFolderName());
                return false;
            }

            Files.move(source, targetPath);
            Console.printSuccess("Moved : " + problem.getFolderName() + " -> " + language);
            return true;
        } catch (IOException e) {
            Console.printError("Failed to move: " + problem.getFolderName());
            e.printStackTrace();
            return false;
        }
    }
}