package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
    ArrayList<String> fileName = new ArrayList<>();
    String data1;
    String data2;
    double total = 0;
    double similar = 0;
    double[][] similarity;
    Files dir = new Files();
    File program_1;
    File program_2;
    Scanner first_cmp;
    Scanner second_cmp;
    public double[][] getScore() throws FileNotFoundException {
        fileName = dir.getFiles("Directory");
        similarity = new double[fileName.size()][fileName.size()];
        for (int i = 0; i < fileName.size(); i++) {
            for (int j = 0; j < fileName.size(); j++) {
                program_1 = new File(fileName.get(i));
                program_2 = new File(fileName.get(j));
                first_cmp = new Scanner(program_1);
                second_cmp = new Scanner(program_2);
                while (first_cmp.hasNextLine() && second_cmp.hasNextLine()) {
                    data1 = first_cmp.nextLine();
                    data2 = second_cmp.nextLine();
                    total++;
                    if (data1.equals(data2)) {
                        similar++;
                    }
                }
                double percentage = (similar / total);
                similarity[i][j] = percentage;
                total = 0;
                similar = 0;
                first_cmp.close();
                second_cmp.close();
            }
        }

        return similarity;
    }
}