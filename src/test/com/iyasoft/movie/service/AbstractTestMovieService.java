package com.iyasoft.movie.service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class AbstractTestMovieService {

    public String getJsonPayloadFromFile(String fileName){
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}