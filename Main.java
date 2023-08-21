import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File dirRoot = new File("F://Games");

        File dirSrc = new File(dirRoot, "src");
        File dirRes = new File(dirRoot, "res");
        File dirSave = new File(dirRoot, "savegames");
        File dirTemp = new File(dirRoot, "temp");

        File dirMain = new File(dirSrc, "main");
        File dirTest = new File(dirSrc, "test");

        File fileMain = new File(dirMain, "Main.java");
        File fileUtils = new File(dirMain, "Utils.java");

        File dirDrawables = new File(dirRes, "drawables");
        File dirVectors = new File(dirRes, "vectors");
        File dirIcons = new File(dirRes, "icons");

        File fileTemp = new File(dirTemp, "temp.txt");

        StringBuilder installLog = new StringBuilder();

        createDir(dirSrc, installLog);
        createDir(dirRes, installLog);
        createDir(dirSave, installLog);
        createDir(dirTemp, installLog);

        createDir(dirMain, installLog);

        createFile(fileMain, installLog);
        createFile(fileUtils, installLog);

        createDir(dirTest, installLog);

        createFile(fileTemp, installLog);

        createDir(dirDrawables, installLog);
        createDir(dirVectors, installLog);
        createDir(dirIcons, installLog);

        // Сохраняем лог в файл temp.txt
        try (FileWriter fileWriter = new FileWriter(fileTemp)) {
            fileWriter.append(installLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Создание директорий с сохранением лога
    private static void createDir(File dir, StringBuilder log) {
        String path = dir.getAbsolutePath();

        log.append(path);

        if (dir.mkdir()) {
            log.append(" успешно создана");
        } else {
            if (dir.exists()) {
                log.append(" уже существует");
            } else {
                log.append(" не создана");
            }
        }

        log.append("\n");
    }

    // Создание файлов с сохранением лога
    private static void createFile(File file, StringBuilder log) {
        String path = file.getAbsolutePath();

        log.append(path);

        try {
            if (file.createNewFile()) {
                log.append(" успешно создан");
            } else {
                if (file.exists()) {
                    log.append(" уже существует");
                } else {
                    log.append(" не создан");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.append("\n");
    }
}
