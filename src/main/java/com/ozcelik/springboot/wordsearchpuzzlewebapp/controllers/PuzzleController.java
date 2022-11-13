package com.ozcelik.springboot.wordsearchpuzzlewebapp.controllers;

import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.Cell;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.UserInputs;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.utilities.HelperMethods;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.utilities.WriteToFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class PuzzleController {

    @GetMapping("/")
    public String index(UserInputs userInputs) {
        return "index";
    }

    @PostMapping("/createPuzzle")
    public String createPuzzle(Model model, UserInputs userInputs) throws ExecutionException, InterruptedException {

        int row = 0;
        int column = 0;
        int numberOfWord = 0;
        String type = userInputs.getType();
        String level = userInputs.getLevel();
        if (level.equalsIgnoreCase("Easy")) {
            numberOfWord = 15;
            row = 15;
            column = 15;
        } else if (level.equalsIgnoreCase("Medium")) {
            numberOfWord = 20;
            row = 20;
            column = 20;
        } else {
            numberOfWord = 30;
            row = 30;
            column = 20;
        }

        HelperMethods utility = new HelperMethods(row, column);
        Cell[][] grid = new Cell[row][column];
        String[] vocabularies = new String[numberOfWord];
        for (int i = 0; i < numberOfWord; i++) {
            if (level.equalsIgnoreCase("Easy")) {
                String vocab = utility.GetVocabularies(type).toUpperCase();
                int numOfChar = vocab.length();
                if (numOfChar <= 8) {
                    vocabularies[i] = vocab;
                } else i--;
            } else vocabularies[i] = utility.GetVocabularies(type).toUpperCase();
        }
        // Initialize puzzle grid
        utility.initGrid(grid);
        // creating puzzle with words
        utility.fillGridWithVocabularies(grid, vocabularies);
        // copy solution to a new array
        char[][] solution = utility.copySolution(grid, row, column);
        //Cell[][] solution = Arrays.stream(grid).map(Cell[]::clone).toArray(Cell[][]::new);
        // fill the puzzle with random letters
        utility.fillRandomLetter(grid);
        // Printing words in order
        System.out.println("Vocabulary list for the puzzle");
        var sorted = Arrays.asList(vocabularies);
        sorted.sort(String::compareToIgnoreCase);

        model.addAttribute("arr", grid);
        model.addAttribute("solution", solution);
        model.addAttribute("vocabList", sorted);

        WriteToFile toFile = new WriteToFile();
        toFile.createAFile(grid, sorted, row, column);
        return "createPuzzle";
    }
}
