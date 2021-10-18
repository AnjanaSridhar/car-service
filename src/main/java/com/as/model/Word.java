package com.as.model;

import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class Word {
    private String word;
    private int score;
    private String numSyllables;
}
