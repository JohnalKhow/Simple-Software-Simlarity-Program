package Algorithms;

import java.io.File;


public class directory_algorithm {
    public static File[] showFiles(String dir) {
        String dire="Directory";
        File[] files = new File(dire).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
               showFiles(dir + "/" + files[i].getName());
            }
        }
        return files;
    }
}
