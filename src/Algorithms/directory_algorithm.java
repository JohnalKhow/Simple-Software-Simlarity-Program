package Algorithms;

import java.io.File;
import java.util.ArrayList;


public class directory_algorithm {
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> file_name= new ArrayList<>();

    public ArrayList<String> get_files(String dir){
        //Returns all directory references of the scanned files
        read(dir);
        return files;
    }

    public ArrayList<String> get_file_names(){
        //Returns all file names of the scanned files
        return file_name;
    }

    public void read(String dir)  {
        File[] files = new File(dir).listFiles();
        for (int i = 0; i < files.length; i++) {
            //If file is indeed a file, store file_names and full directory paths
            //files = directory reference
            //file_name = display name reference
            if(files[i].isFile()){
                this.files.add(files[i].toString());
                this.file_name.add(files[i].getName());
            }

            //If "file" is directory
            else if (files[i].isDirectory()) {
                read(dir+"/"+files[i].getName());
            }
        }
    }

}
