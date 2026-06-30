package com.harshvardhan.leetorganizer.model;

import java.nio.file.Path;

public class Problem {

    private final String folderName;
    private final Path folderPath;

    public Problem(String folderName, Path folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public String getFolderName() {
        return folderName;
    }

    public Path getFolderPath() {
        return folderPath;
    }

    @Override
    public String toString() {
        return folderName;
    }
}