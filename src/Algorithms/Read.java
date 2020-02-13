package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read {
    public double[][] read_files() {
        String data1, data2;
        directory_algorithm dir = new directory_algorithm();
        File[] files = dir.showFiles("Directory");
        double [][] similarity= new double[files.length][files.length];
        double total = 0, similar = 0;
        try {
            for (int i = 0; i < files.length; i++) {
                for (int j = 0; j < files.length ; j++) {
                    Scanner first_cmp = new Scanner(files[i]);
                    Scanner second_cmp = new Scanner(files[j]);
                    while(first_cmp.hasNext() && second_cmp.hasNext()) {
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

        }
        catch(FileNotFoundException e) {
            System.out.println("FILE I/O EXCEPTION");
            e.printStackTrace();
        }
        return similarity;
    }
}