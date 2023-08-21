package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String fileTemp = "F://Games//temp//temp.txt";
        String dirSave = "F://Games//savegames";

        ArrayList<String> dirList = new ArrayList<>();
        ArrayList<String> fileList = new ArrayList<>();

        dirList.add("F://Games//src");
        dirList.add("F://Games//res");
        dirList.add("F://Games//savegames");
        dirList.add("F://Games//temp");

        dirList.add("F://Games//src//main");
        dirList.add("F://Games//src//test");

        dirList.add("F://Games//res//drawables");
        dirList.add("F://Games//res//vectors");
        dirList.add("F://Games//res//icons");

        fileList.add("F://Games//src//main//Main.java");
        fileList.add("F://Games//src//main//Utils.java");

        fileList.add("F://Games//temp//temp.txt");

        StringBuilder installLog = new StringBuilder();

        // Создаём папки
        for (String dir: dirList) {
            createDir(dir, installLog);
        }

        // Создаём файлы
        for (String file: fileList) {
            createFile(file, installLog);
        }

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

        File fileSave1 = new File(dirSave + "/save1.sav");
        File fileSave2 = new File(dirSave + "/save2.sav");
        File fileSave3 = new File(dirSave + "/save3.sav");

        // 2.2 Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи
        saveGameProcess(gameProgress1, fileSave1);
        saveGameProcess(gameProgress2, fileSave2);
        saveGameProcess(gameProgress3, fileSave3);

        // 2.3 Созданные файлы сохранений из папки savegames запаковать в архив zip
        File path = new File("");
        archiveDir(dirSave, path);

        // 2.4 Удалить файлы сохранений, лежащие вне архива
        cleanDir(dirSave);
    }

    // Создание директорий с сохранением лога
    private static void createDir(String dirAbsoluteName, StringBuilder log) {
        File dir = new File(dirAbsoluteName);

        log.append(dirAbsoluteName);

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
    private static void createFile(String fileAbsoluteName, StringBuilder log) {
        File file = new File(fileAbsoluteName);

        log.append(fileAbsoluteName);

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
    private static void archiveDir(String dirAbsoluteName, File path) {
        File dir = new File(dirAbsoluteName);
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
    private static void cleanDir(String dirAbsoluteName) {
        File dir = new File(dirAbsoluteName);
        for (File file: dir.listFiles()) {
            file.delete();
        }
    }

    // Распаковка архива в заданный каталог
    private static void openZip(File fileZip, File dirExtract) {

    }
}
