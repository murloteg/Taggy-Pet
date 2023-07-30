package ru.nsu.sberlab.models.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class ImageRemover {
    public static void removeImageFromFileSystem(String partOfPathToRemove, String fileName, String defaultImageName) throws IOException {
        if (!Objects.equals(fileName, defaultImageName)) {
            Path imagePath = Paths.get(partOfPathToRemove + fileName);
            Files.deleteIfExists(imagePath);
        }
    }

    private ImageRemover() {
        throw new IllegalStateException();
    }
}
