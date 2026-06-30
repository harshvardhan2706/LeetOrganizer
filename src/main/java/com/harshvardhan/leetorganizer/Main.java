package com.harshvardhan.leetorganizer;

public class Main {

    public static void main(String[] args) {
        Console.printBanner();

        String repositoryPath = Config.getRepositoryPath();
        GitManager gitManager = new GitManager(repositoryPath);

        if (!gitManager.pullLatest()) {
            Console.printError("Unable to pull latest changes. Exiting.");
            return;
        }

        Organizer organizer = new Organizer();
        Organizer.OrganizerResult result = organizer.organize(repositoryPath);

        Console.printSection("Summary");
        Console.printInfo("Total folders scanned: " + result.getTotalProblems());
        Console.printInfo("Moved: " + result.getMovedProblems());
        Console.printInfo("Skipped: " + result.getSkippedProblems());

        if (!gitManager.sync()) {
            Console.printError("Git synchronization failed. Exiting.");
            return;
        }

        Console.printSuccess("Done.");
    }

}