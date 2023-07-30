package ru.nsu.sberlab.models.utils;

import java.io.File;
import java.nio.file.Paths;

public final class PathResolver {
    public static String resolvePath(String partOfPath) {
        return Paths.get(".").toAbsolutePath() + partOfPath + File.separator;
    }

    private PathResolver() {
        throw new IllegalStateException();
    }
}
