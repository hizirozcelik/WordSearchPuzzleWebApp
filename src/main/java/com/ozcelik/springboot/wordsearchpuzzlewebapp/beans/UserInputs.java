package com.ozcelik.springboot.wordsearchpuzzlewebapp.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputs {
    private final String[] LEVELS = {"Easy", "Medium", "Advanced"};
    private final String[] TYPES = {"Mixed", "Noun", "Verb", "Adjective", "Adverb"};
    private String level;
    private String type;
}
