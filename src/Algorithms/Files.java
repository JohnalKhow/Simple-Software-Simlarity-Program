package Algorithms;

import java.io.File;
import java.util.ArrayList;


public class Files {
    ArrayList<String> file_references = new ArrayList<>();
    ArrayList<String> file_name = new ArrayList<>();
    File[] files;
    public ArrayList<String> getFiles(String dir) {
        //Returns all directory references of the scanned files
        readDirectory(dir);
        return file_references;
    }

    public ArrayList<String> getFileNames() {
        //Returns all file names of the scanned files
        return file_name;
    }

    public void readDirectory(String dir) {
        files = new File(dir).listFiles();
        assert files != null;
        for (File file : files) {
            //If file is indeed a file, store file_names and full directory paths
            //files = directory reference
            //file_name = display name reference
            if (file.isFile()) {
                this.file_references.add(file.toString());
                this.file_name.add(" ");
            }

            //If "file" is directory
            else if (file.isDirectory()) {
                readDirectory(dir + "/" + file.getName());
                this.file_name.add(file.getName());
            }
        }
    }

}
