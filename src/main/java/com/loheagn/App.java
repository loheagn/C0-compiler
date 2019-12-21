package com.loheagn;

import com.loheagn.ast.C0ProgramAST;
import com.loheagn.grammaAnalysis.GrammaAnalyser;
import com.loheagn.tokenizer.Tokenizer;
import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private static String inFileName;

    private static List<String> compile()  {
        GrammaAnalyser grammaAnalyser = new GrammaAnalyser(new Tokenizer().getAllTokens(inFileName));
        C0ProgramAST c0ProgramAST = grammaAnalyser.C0Program();
        return c0ProgramAST.generateInstructions();
    }

    public static void main(String[] args){
        Options options = new Options();
        options.addOption(Option.builder("h").hasArg(false).required(false).desc("显示关于编译器使用的帮助").build());
        options.addOption(Option.builder("c").hasArg(false).required(false).desc("将输入的 c0 源代码翻译为二进制目标文件").build());
        options.addOption(Option.builder("s").hasArg(false).required(false).desc("将输入的 c0 源代码翻译为文本汇编文件").build());
        options.addOption(Option.builder("o").hasArg(true).required(false).desc("输出到指定的文件 file").build());
        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println("命令行参数解析错误.");
            return;
        }
        if (commandLine.hasOption("h")) {
            System.out.println("Usage:\n" +
                    "  cc0 [options] input [-o file]\n" +
                    "or \n" +
                    "  cc0 [-h]\n" +
                    "Options:\n" +
                    "  -s        将输入的 c0 源代码翻译为文本汇编文件\n" +
                    "  -c        将输入的 c0 源代码翻译为二进制目标文件\n" +
                    "  -h        显示关于编译器使用的帮助\n" +
                    "  -o file   输出到指定的文件 file");
            return;
        }
        try {
            String outFileName = "out";
            inFileName = commandLine.getArgList().get(0);
            if(commandLine.hasOption("o")){
                outFileName = commandLine.getOptionValue("o");
            }
            List<String> instructions = compile();
            if(commandLine.hasOption("s")){
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
                for(String s:instructions){
                    writer.write(s+"\n");
                }
                writer.close();
            } else if(commandLine.hasOption("o")) {
                // todo
            }
//        } catch (CompileException e){
//            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println("文件操作错误.");
        }
    }
}
