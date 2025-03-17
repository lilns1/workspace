package FileLearn;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileDemo {
    public static void main(String[] args) {
//        File filedir = new File("D:/JavaLearn");
//        if (!filedir.exists()) {
//            try {
//                if (filedir.mkdir())
//                    System.out.println("Directory created");
//                else System.out.println("Directory not created");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        File newfile = new File("D:/JavaLearn/073.txt");
//        if (!newfile.exists()) {
//            try {
//                if (newfile.createNewFile())
//                    System.out.println("File created");
//                else System.out.println("File not created");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        File renamefile = new File("D:/JavaLearn/lilns.txt");
//        if (newfile.renameTo(renamefile)) {}
//            System.out.println("newFile renamed successfully");
        FileReader fis = null;
        try {
            fis = new FileReader("D:/JavaLearn/lilns.txt");
            int c;
            while ((c = fis.read()) > 0){
                System.out.print((char)c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        if (renamefile.delete())
//            System.out.println("nenameFile deleted successfully");
//        if (filedir.delete())
//            System.out.println("JavaDir deleted successfully");
    }
}
