package FileLearn;
import java.io.*;
import java.util.*;

public class WriteDataToFile {
    public static void main(String[] args) {

        File fileA = new File("D:/JavaLearn/TestA.txt");
        File fileB = new File("D:/JavaLearn/TestB.txt");
        File filet = new File("D:/JavaLearn/mytest.txt");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String str;
        try {
            fis = new FileInputStream(fileA);
            fos = new FileOutputStream(filet,true);
            int c;
            while((c = fis.read()) > 0) {
                fos.write(c);
            }
            fis = new FileInputStream(fileB);
            while((c = fis.read()) > 0) {
                fos.write(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
