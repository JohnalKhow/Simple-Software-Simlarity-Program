package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.regex.Pattern;

public class Calculator {

    public double[][] getScore() throws IOException {
        Files fileRef = new Files();
        String data1;
        String data2;
        File program_1;
        File program_2;
        Scanner first_cmp;
        Scanner second_cmp;
        ArrayList<String> filePointers = fileRef.getFiles("Directory", 1);
        double[][] similarityScore = new double[filePointers.size()][filePointers.size()];
        double total = 0;
        double similar = 0;
        for (int i = 0; i < filePointers.size(); i++) {
            for (int j = 0; j < filePointers.size(); j++) {
                program_1 = new File(filePointers.get(i));
                program_2 = new File(filePointers.get(j));
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
                similarityScore[i][j] = percentage;
                total = 0;
                similar = 0;
                first_cmp.close();
                second_cmp.close();
            }
        }
        return similarityScore;
    }


}