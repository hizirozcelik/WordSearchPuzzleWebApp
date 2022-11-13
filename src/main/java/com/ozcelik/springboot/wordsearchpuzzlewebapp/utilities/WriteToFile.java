package com.ozcelik.springboot.wordsearchpuzzlewebapp.utilities;

import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.Cell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {
    // Main driver method
    public void createAFile(Cell[][] grid, List<String> vocabularies, int row, int column) {
        // Assigning the file content
        String text = "Search Word Puzzle Generator v1.2\n";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                text += grid[i][j].getLetter() + "\t";
            }
            text += "\n";
        }
        text+="\n\n";
        text+="Vocabularies\n";
        int counter = 0;
        for (String word: vocabularies) {
            text += word + ",\t";
        }
        text+="\nCreated by Hizir Ozcelik, November 2022, @Oakville";


        // Try block to check for exceptions
        try {

            // Step 1: Create an object of BufferedWriter
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    "src/main/resources/output/puzzlePage.txt"));

            // Step 2: Write text(content) to file
            f_writer.write(text);

            // Step 3: Printing the content inside the file
            // on the terminal/CMD
            System.out.print(text);

            // Step 4: Display message showcasing
            // successful execution of the program
            System.out.println("");
            System.out.println(
                    "File is created successfully with the content.");

            // Step 5: Close the BufferedWriter object
            f_writer.close();
        }

        // Catch block to handle if exceptions occurs
        catch (IOException e) {

            // Print the exception on console
            // using getMessage() method
            System.out.print(e.getMessage());
        }
    }
}
