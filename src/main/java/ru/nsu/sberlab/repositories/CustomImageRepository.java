package ru.nsu.sberlab.repositories;

import java.io.IOException;

public interface CustomImageRepository {
    void removeImageFromFileSystem(String partOfPathToRemove, String fileName) throws IOException;
    String restorePath(String partOfPath);
    void saveDefaultImageOnFileSystemIfRequired(String defaultImageName) throws IOException;
}
