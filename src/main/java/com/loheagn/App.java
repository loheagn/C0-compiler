package com.loheagn;

import com.loheagn.grammaAnalysis.GrammaAnalyser;
import com.loheagn.tokenizer.Token;
import com.loheagn.tokenizer.Tokenizer;
import com.loheagn.utils.CompileException;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws CompileException {
        String fileName = "input";
        List<Token> tokens = new Tokenizer().getAllTokens(fileName);
        GrammaAnalyser grammaAnalyser = new GrammaAnalyser(tokens);
        System.out.println(grammaAnalyser);
    }
}
