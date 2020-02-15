package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read {
    ArrayList<String> file_name = new ArrayList<>();
    public double[][] read_files() throws FileNotFoundException {
        String data1, data2;
        directory_algorithm dir = new directory_algorithm();
        file_name= dir.get_files("Directory");
        double [][] similarity= new double[file_name.size()][file_name.size()];
        double total = 0, similar = 0;
        for (int i = 0; i < file_name.size(); i++) {
            for (int j = 0; j < file_name.size() ; j++) {
                File program_1 = new File(file_name.get(i));
                File program_2 = new File(file_name.get(j));
                Scanner first_cmp = new Scanner(program_1);
                Scanner second_cmp = new Scanner(program_2);
                while(first_cmp.hasNextLine() && second_cmp.hasNextLine()) {
                    data1 = first_cmp.nextLine();
                    data2 = second_cmp.nextLine();
                    total++;
                    if (data1.equals(data2)) {
                        similar++;
                    }
                }
                double percentage = (similar / total);
                similarity[i][j]= percentage;
                total=0;
                similar=0;
                first_cmp.close();
                second_cmp.close();
            }
        }

        return similarity;
    }
}