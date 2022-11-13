package com.ozcelik.springboot.wordsearchpuzzlewebapp.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private int x;
    private int y;
    private char letter;
    private boolean isFilled;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        letter = '0';
        isFilled = false;
    }

    public Cell(char letter) {
        this.letter = letter;
        this.isFilled = true;
    }
}
