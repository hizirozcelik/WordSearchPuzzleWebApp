package com.ozcelik.springboot.wordsearchpuzzlewebapp.controllers;

import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.Cell;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.beans.UserInputs;
import com.ozcelik.springboot.wordsearchpuzzlewebapp.utilitys.HelperMethods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Controller
public class PuzzleController {


    @GetMapping("/")
    public String index(UserInputs userInputs) {
        return "index";
    }

    @PostMapping("/createPuzzle")
    public String createPuzzle(UserInputs userInputs) throws ExecutionException, InterruptedException {

        int row = 0;
        int column = 0;
        int numberOfWord = 0;
        String type = userInputs.getType();
        String level = userInputs.getLevel();
        if (level.compareTo("Easy") == 0) {
            numberOfWord = 15;
            row = 20;
            column = 20;
        } else if (level.compareTo("Medium") == 0) {
            numberOfWord = 30;
            row = 30;
            column = 20;
        } else {
            numberOfWord = 50;
            row = 40;
            column = 30;
        }

        HelperMethods utility = new HelperMethods(row,column);
        Cell[][] grid = new Cell[row][column];
        String[] vocabularies = new String[numberOfWord];
        for (int i = 0; i < numberOfWord; i++) {
            if (level.compareTo("Easy") == 0) {
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
        // printing puzzle grid
        utility.printGrid(grid);
        // Printing words in order
        System.out.println("Vocabulary list for the puzzle");
        var sorted = Arrays.asList(vocabularies);
        sorted.sort(String::compareToIgnoreCase);
        int sno = 0;
        for (String item : sorted) {
            sno++;
            System.out.println(sno + ". " + item);
        }
        return "createPuzzle";
    }
}
