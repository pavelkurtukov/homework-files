import java.io.File;
import java.io.IOException;

public class Task1 {
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

        dirSrc.mkdir();
        dirRes.mkdir();
        dirSave.mkdir();
        dirTemp.mkdir();

        dirMain.mkdir();
        dirTest.mkdir();

        dirDrawables.mkdir();
        dirVectors.mkdir();
        dirIcons.mkdir();

        try {
            fileMain.createNewFile();
            fileUtils.createNewFile();

            fileTemp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
