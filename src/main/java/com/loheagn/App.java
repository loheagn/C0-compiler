package com.loheagn;

import com.loheagn.ast.C0ProgramAST;
import com.loheagn.grammaAnalysis.GrammaAnalyser;
import com.loheagn.tokenizer.Tokenizer;
import com.loheagn.utils.CompileException;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private static String inFileName;

    private static C0ProgramAST generateC0ProgramAST() {
        GrammaAnalyser grammaAnalyser = new GrammaAnalyser(new Tokenizer().getAllTokens(inFileName));
        return grammaAnalyser.C0Program();
    }

    private static List<String> compileASM()  {
        return generateC0ProgramAST().generateInstructionsString();
    }

    private static List<Byte> compileBinary() {
        return generateC0ProgramAST().generateInstructionsBytes();
    }

    public static void main(String[] args){
        Options options = new Options();
        options.addOption(Option.builder("h").hasArg(false).required(false).desc("显示关于编译器使用的帮助").build());
        options.addOption(Option.builder("c").hasArg(false).required(false).desc("将输入的 c0 源代码翻译为二进制目标文件").build());
        options.addOption(Option.builder("s").hasArg(false).required(false).desc("将输入的 c0 源代码翻译为文本汇编文件").build());
        options.addOption(Option.builder("o").hasArg(true).required(false).desc("输出到指定的文件 file").build());
        CommandLine commandLine;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println("命令行参数解析错误.");
            return;
        }
        String helpString = "Usage:\n" +
                "  java -jar cc0 [options] input [-o file]\n" +
                "or \n" +
                "  java -jar cc0 [-h]\n" +
                "Options:\n" +
                "  -s        将输入的 c0 源代码翻译为文本汇编文件\n" +
                "  -c        将输入的 c0 源代码翻译为二进制目标文件\n" +
                "  -h        显示关于编译器使用的帮助\n" +
                "  -o file   输出到指定的文件 file";
        if (commandLine.hasOption("h")) {
            System.out.println(helpString);
            return;
        }
        try {
            inFileName = commandLine.getArgList().get(0);
            String outFileName = "out";
            if(commandLine.hasOption("o")){
                outFileName = commandLine.getOptionValue("o");
            }
            if(commandLine.hasOption("s")){
                List<String> instructions = compileASM();
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
                for(String s:instructions){
                    writer.write(s+"\n");
                }
                writer.close();
            } else if(commandLine.hasOption("c")) {
                List<Byte> bytes = compileBinary();
                DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outFileName));
                for(Byte byte1: bytes){
                    outputStream.write(byte1);
                }
                outputStream.close();
            }
        } catch (CompileException e){
            System.out.println(e.toString());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("文件操作错误.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(helpString);
            System.exit(1);
        }
    }
}
