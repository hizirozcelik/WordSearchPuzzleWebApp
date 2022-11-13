package com.ozcelik.springboot.wordsearchpuzzlewebapp.controllers;

import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.Cell;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.UserInputs;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.utilities.HelperMethods;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.utilities.WriteToFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;


// This web application create a word search puzzle based on user selections.
// It creates three different size puzzles with diffrent vord type selection.
// Words retrieve from a API endpoint.
// Also it creates a text file for the puzzle.
// Author: Hizir Ozcelik | Nov 2022 | @Oakville
@Controller
public class PuzzleController {

    @GetMapping("/")
    public String index(Model model, UserInputs userInputs) {
        model.addAttribute("userInputs", userInputs);

        return "index";
    }

    @PostMapping("/createPuzzle")
    public String createPuzzle(Model model, UserInputs userInputs) throws ExecutionException, InterruptedException {

        int row;
        int column;
        int numberOfWord;
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
