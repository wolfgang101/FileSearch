package com.foogle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;


public class patternmatch implements Runnable {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_CYAN = "\u001B[36m";


    String pattern;
    String filename;
    CountDownLatch latch;
    public patternmatch(String pattern, String filename, CountDownLatch latch){
        this.pattern = pattern;
        this.filename = filename;
        this.latch = latch;
    }
    public void run(){
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(filename));
            int i=0;
            Path path = Paths.get(filename);
            long count = Files.lines(path).count();
            String line = in.readLine();
            while(line!=null){
                i++;
                if(line.contains(pattern)){
                    if(i>2 && i<count-1) {
                        Main.resultcount+=1;
                        System.out.println(ANSI_CYAN + filename.replace(".txt", "") + " : " + ANSI_YELLOW + line + ANSI_CYAN + " Line : " + (i-2) + ANSI_BLACK);
                    }
                }
                line = in.readLine();
            }
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
            latch.countDown();
        }

    }
}