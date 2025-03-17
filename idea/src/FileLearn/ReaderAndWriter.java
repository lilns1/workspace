package FileLearn;
import java.io.*;
import java.util.*;
public class ReaderAndWriter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        System.out.println(filename);
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("D:/JavaLearn/rw1.txt"));
            bw = new BufferedWriter(new FileWriter("D:/JavaLearn/rw2.txt"));
            String str = null;
            while ((str = br.readLine()) != null) {
                bw.write(str + "\r");
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
