package utils;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class txtutil {
    public static List<String[]> x(String path){
        List<String[]> a=new ArrayList();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    path));
            String line = reader.readLine();
            while (line != null) {
                a.add(line.split(" "));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
}
