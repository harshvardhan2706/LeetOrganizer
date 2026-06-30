package com.harshvardhan.leetorganizer;

import java.util.Set;

public final class Constants {

    public static final Set<String> TOP_LEVEL_IGNORE = Set.of(
            ".git",
            ".github",
            ".idea",
            ".vscode",
            "target",
            "src",
            "Java",
            "SQL",
            "Python",
            "Cpp",
            "JavaScript",
            "TypeScript",
            "Go",
            "Rust",
            "Kotlin"
    );

    public static final String DEFAULT_BRANCH = "main";
    public static final String GIT_REMOTE = "origin";
    public static final String COMMIT_MESSAGE = "Auto organized by LeetOrganizer";

    private Constants() {
        // Utility class
    }
}
