package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author liyijia
 * @create 2023-11-2023/11/27
 */
public class FileUtils {

    /**
     * 使用 Files.walk 方法来遍历目录，并删除每个文件和子目录，最后删除目录本身。
     * @param dirName 目录路径名，String 类型
     * @throws IOException
     */
    public static void deleteDirectoryIfExists(String dirName) throws IOException {
        java.nio.file.Path dir = Paths.get(dirName);
        // Check if the directory exists
        if (Files.exists(dir)) {
            // Walk the directory
            try (Stream<Path> walk = Files.walk(dir)) {
                // Sort in reverse order so the directory entries get deleted before the directory itself
                walk.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                // Delete each entry
                                Files.delete(path);
                            } catch (IOException e) {
                                // Handle the potential IOException
                                System.err.printf("Unable to delete this path : %s%n%s", path, e);
                            }
                        });
            } catch (IOException e) {
                // Handle the potential IOException from walking the directory
                throw e;
            }
        }
    }
}
