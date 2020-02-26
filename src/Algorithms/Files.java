package Algorithms;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Files {
    ArrayList<String> final_ref = new ArrayList<>();
    ArrayList<String> file_name = new ArrayList<>();
    File[] files;
    String fileExt;

    public ArrayList<String> getFiles(String dir, int choice) throws IOException {
        //Returns all directory references of the scanned files
        readDirectory(dir, choice);
        if (choice == 1) {
            fileExt = ".java";
        } else if (choice == 2) {
            fileExt = ".cpp";
        }
        for (int i = 0; i < file_name.size(); i++) {
            merge(file_name.get(i), fileExt);
        }
        insertFiles(choice);
        return final_ref;
    }

    public void insertFiles(int choice) {
        File[] final_files = new File("temp").listFiles();
        for (File file : final_files) {
            if (file.isFile()) {
                if (choice == 1) {
                    if (file.getName().contains(".java")) {
                        this.final_ref.add(file.toString());
                    }
                } else if (choice == 2) {
                    if (file.getName().contains(".cpp")) {
                        this.final_ref.add(file.toString());
                    }
                }
            }
        }
    }

    public ArrayList<String> readDirectory(String dir, int choice) {
        files = new File(dir).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                readDirectory(dir + "/" + file.getName(), choice);
                this.file_name.add(file.getName());
            }
        }
        return file_name;
    }

    public void merge(String fileRef, String fileExt) throws IOException {
        File dir = new File("Directory/" + fileRef);
        PrintWriter pw = new PrintWriter("temp/" + fileRef + "" + fileExt);
        String[] fileNames = dir.list();
        for (String fileName : fileNames) {
            File f = new File(dir, fileName);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                pw.println(line);
                line = br.readLine();
            }
            pw.flush();
        }
    }

}
