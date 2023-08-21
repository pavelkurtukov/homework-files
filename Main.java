import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

        // 1.1 В папке Games создайте несколько директорий: src, res, savegames, temp.
        createDir(dirSrc, installLog);
        createDir(dirRes, installLog);
        createDir(dirSave, installLog);
        createDir(dirTemp, installLog);

        // 1.2 В каталоге src создайте две директории: main, test
        createDir(dirMain, installLog);
        createDir(dirTest, installLog);

        // 1.3 В подкаталоге main создайте два файла: Main.java, Utils.java
        createFile(fileMain, installLog);
        createFile(fileUtils, installLog);

        // 1.4 В каталог res создайте три директории: drawables, vectors, icons
        createDir(dirDrawables, installLog);
        createDir(dirVectors, installLog);
        createDir(dirIcons, installLog);

        // 1.5 В директории temp создайте файл temp.txt
        createFile(fileTemp, installLog);

        // Сохраняем лог в файл temp.txt
        try (FileWriter fileWriter = new FileWriter(fileTemp)) {
            fileWriter.append(installLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2.1 Создать три экземпляра класса GameProgres
        GameProgress gameProgress1 = new GameProgress(10, 10, 1, 2);
        GameProgress gameProgress2 = new GameProgress(20, 15, 7, 3);
        GameProgress gameProgress3 = new GameProgress(100500, 333, 80, 100);

        File fileSave1 = new File(dirSave, "save1.sav");
        File fileSave2 = new File(dirSave, "save2.sav");
        File fileSave3 = new File(dirSave, "save3.sav");

        // 2.2 Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи
        saveGameProcess(gameProgress1, fileSave1);
        saveGameProcess(gameProgress2, fileSave2);
        saveGameProcess(gameProgress3, fileSave3);

        // 2.3 Созданные файлы сохранений из папки savegames запаковать в архив zip
        archiveDir(dirSave);

        // 2.4 Удалить файлы сохранений, лежащие вне архива
        cleanDir(dirSave);
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

    // Сохранение сериализованного игрового процесса в заданный файл
    private static void saveGameProcess(GameProgress gameProgress, File saveFile) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            outputStream.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Архивирование всех файлов директории в каталог проекта
    private static void archiveDir(File dir) {
        String archiveName = dir.getName() + ".zip";
        File fileArc = new File(archiveName);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(fileArc))) {
            for (File file: dir.listFiles()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    fileInputStream.close();
                    zipOutputStream.write(buffer);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Очистка директории
    private static void cleanDir(File dir) {
        for (File file: dir.listFiles()) {
            System.out.println(file.getAbsolutePath());
            file.delete();
        }
    }
}
