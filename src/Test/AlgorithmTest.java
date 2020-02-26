package Test;

import Algorithms.Calculator;
import Algorithms.Files;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    @Test
    void getScore() throws IOException {
        Files file = new Files();
        Calculator score = new Calculator();
        file.getFiles("Directory", 1);
        double[][] grade = score.getScore();
        assertEquals(1.00,grade[1][1]);
    }

    @Test
    void getFiles() throws IOException {
        Files file = new Files();
        ArrayList<String> files = file.getFiles("Directory", 1);
        assertTrue(files.get(1).contains(".java"));
    }

    @Test
    void checkMetric() throws IOException {
        boolean checker =false;
        Files file = new Files();
        ArrayList<String> files = file.insertFiles("assets/Metrics", 3);
        for(int i=0; i<files.size(); i++){

            if (files.get(i).contains("Metrics.txt")) {
                checker=true;
            }
        }
        assertTrue(checker);
    }
}