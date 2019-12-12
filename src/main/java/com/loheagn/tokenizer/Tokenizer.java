package com.loheagn.tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.loheagn.utils.Position;

enum DFAState {

}

/**
 * Tokenizer
 */
public class Tokenizer {

    Position currentPosition;
    List<String> content;

    void readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        content = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            content.add(line + "\n");
            line = reader.readLine();
        }
    }

    Character nextChar() throws IOException {
        currentPosition.column++;
        if (currentPosition.column > content.get(currentPosition.row).length() - 1) {
            currentPosition.column = 0;
            currentPosition.row++;
            if (currentPosition.row > content.size() - 1)
                return null;
        }
        return content.get(currentPosition.row).charAt(currentPosition.column);
    }

    void unreadChar() {
        currentPosition.column--;
        if (currentPosition.column < 0) {
            if (currentPosition.row == 0)
                return;
            currentPosition.row--;
            currentPosition.column = content.get(currentPosition.row).length() - 1;
        }
    }

    private Token nextToken() throws Exception {
        return null;
    }

    public List<Token> getAllTokens(String fileName) throws Exception {
        List<Token> tokens = new ArrayList<Token>();
        Token token = nextToken();
        while (token != null) {
            tokens.add(token);
            token = nextToken();
        }
        return tokens;
    }
}