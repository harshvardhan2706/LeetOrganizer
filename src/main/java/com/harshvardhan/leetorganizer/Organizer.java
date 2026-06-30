package com.harshvardhan.leetorganizer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.harshvardhan.leetorganizer.model.Problem;

public class Organizer {

    private final FolderScanner scanner = new FolderScanner();
    private final LanguageDetector detector = new LanguageDetector();
    private final FileMover mover = new FileMover();

    public OrganizerResult organize(String repositoryPath) {
        Objects.requireNonNull(repositoryPath, "repositoryPath cannot be null");

        Path repositoryRoot = Paths.get(repositoryPath).toAbsolutePath();

        Console.printSection("Scanning repository...");
        List<Problem> problems = scanner.scan(repositoryPath);

        Console.printInfo("Problems Found : " + problems.size());
        Console.printInfo("---------------------------------------");

        int movedCount = 0;
        int skippedCount = 0;
        List<String> movedProblems = new ArrayList<>();

        for (Problem problem : problems) {
            String language = detector.detectLanguage(problem);
            boolean moved = mover.moveProblem(problem, repositoryRoot, language);
            if (moved) {
                movedCount++;
                movedProblems.add(problem.getFolderName());
            } else {
                skippedCount++;
            }
        }

        Console.printInfo("");
        Console.printSuccess("Organization Completed.");

        ReadmeGenerator generator = new ReadmeGenerator(repositoryPath);
        generator.generate();

        return new OrganizerResult(problems.size(), movedCount, skippedCount, movedProblems);
    }

    public static final class OrganizerResult {
        private final int totalProblems;
        private final int movedProblems;
        private final int skippedProblems;
        private final List<String> movedProblemNames;

        public OrganizerResult(int totalProblems,
                               int movedProblems,
                               int skippedProblems,
                               List<String> movedProblemNames) {
            this.totalProblems = totalProblems;
            this.movedProblems = movedProblems;
            this.skippedProblems = skippedProblems;
            this.movedProblemNames = List.copyOf(Objects.requireNonNull(movedProblemNames, "movedProblemNames cannot be null"));
        }

        public int getTotalProblems() {
            return totalProblems;
        }

        public int getMovedProblems() {
            return movedProblems;
        }

        public int getSkippedProblems() {
            return skippedProblems;
        }

        public List<String> getMovedProblemNames() {
            return movedProblemNames;
        }
    }
}
