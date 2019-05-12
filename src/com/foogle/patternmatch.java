package com.foogle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class patternmatch implements Runnable {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
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
        String str="";
        BufferedReader in = null;
        bmPatternMatch pm = new bmPatternMatch();
        try{
            in = new BufferedReader(new FileReader(filename));
            int i=0;
            String line = in.readLine();
            while(line!=null){
                i++;
                if(line.contains(pattern)){
                    System.out.println(ANSI_CYAN+filename.replace(".txt","")+ " : " + ANSI_YELLOW + line + ANSI_CYAN + " Line : "+i+ANSI_BLACK);
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