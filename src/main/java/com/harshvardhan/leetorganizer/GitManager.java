package com.harshvardhan.leetorganizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitManager {

    private final Path repositoryPath;

    public GitManager(String repositoryPath) {
        this(Paths.get(Objects.requireNonNull(repositoryPath, "repositoryPath cannot be null")));
    }

    public GitManager(Path repositoryPath) {
        this.repositoryPath = Objects.requireNonNull(repositoryPath).toAbsolutePath();
    }

    public boolean pullLatest() {
        Console.printSection("Pulling latest changes...");

        if (!isGitRepository()) {
            Console.printError("Repository is not a Git repository: " + repositoryPath);
            return false;
        }

        CommandResult result = execute(
                "git",
                "pull",
                "--rebase",
                "--autostash",
                Constants.GIT_REMOTE,
                Constants.DEFAULT_BRANCH
        );

        if (result.exitCode() != 0) {
            Console.printError("Git pull failed.");
            result.output().forEach(Console::printError);
            return false;
        }

        Console.printSuccess("Repository up-to-date.");
        return true;
    }

    public boolean hasChanges() {
        CommandResult result = execute("git", "status", "--porcelain");
        return result.exitCode() == 0 && !result.output().isEmpty();
    }

    public boolean stageChanges() {
        Console.printInfo("Staging changes...");

        CommandResult result = execute("git", "add", "--all");
        if (result.exitCode() != 0) {
            Console.printError("Git add failed.");
            result.output().forEach(Console::printError);
            return false;
        }
        return true;
    }

    public boolean commitChanges() {
        if (!hasChanges()) {
            Console.printInfo("No changes detected.");
            return false;
        }

        Console.printInfo("Committing changes...");

        CommandResult result = execute(
                "git",
                "commit",
                "-m",
                Constants.COMMIT_MESSAGE
        );

        if (result.exitCode() == 0) {
            Console.printSuccess("Commit created.");
            return true;
        }

        if (result.output().stream().anyMatch(line -> line.contains("nothing to commit"))) {
            Console.printInfo("Nothing to commit.");
            return false;
        }

        Console.printError("Git commit failed.");
        result.output().forEach(Console::printError);
        return false;
    }

    public boolean pushChanges() {
        Console.printInfo("Pushing changes...");

        CommandResult result = execute(
                "git",
                "push",
                Constants.GIT_REMOTE,
                Constants.DEFAULT_BRANCH
        );

        if (result.exitCode() != 0) {
            Console.printError("Git push failed.");
            result.output().forEach(Console::printError);
            return false;
        }

        Console.printSuccess("Pushed successfully.");
        return true;
    }

    public boolean sync() {
        if (!stageChanges()) {
            return false;
        }

        if (!hasChanges()) {
            Console.printInfo("No changes detected.");
            return true;
        }

        if (!commitChanges()) {
            return false;
        }

        return pushChanges();
    }

    private boolean isGitRepository() {
        return Files.isDirectory(repositoryPath.resolve(".git"));
    }

    private CommandResult execute(String... command) {
        List<String> output = new ArrayList<>();
        int exitCode = -1;

        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(repositoryPath.toFile());
            builder.redirectErrorStream(true);

            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.add(line);
                }
            }

            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            Console.printError("Git command execution failed.");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        return new CommandResult(exitCode, output);
    }

    private record CommandResult(int exitCode, List<String> output) {
    }
}
