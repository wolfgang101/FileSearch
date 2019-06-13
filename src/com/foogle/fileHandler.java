package com.foogle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class fileHandler {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    String filename = "";
    BufferedReader in;
    PrintWriter out;
    public static final int buffer = 8*1024;

    public fileHandler(String filename){
        this.filename = filename;
    }

    public fileHandler(){

    }

    public String readFile(){
        String str="";
        try{
            in = new BufferedReader(new FileReader(filename), buffer);
            String line = in.readLine();
            while(line!=null){
                str = str+line+"\n";
                line = in.readLine();
            }
            return str;
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
            return str;
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
            catch(NullPointerException ex){
                return null;
            }
        }
    }

    public String getLine(String text){
        String str="";
        try{
            in = new BufferedReader(new FileReader(filename), buffer);
            String line = in.readLine();
            while(line!=null){
                if(line.contains(text)){
                    return line;
                }
                line = in.readLine();
            }
            return str;
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
            return str;
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
        }
    }

    public article readArticle(){
        article add;
        try {
            String str="";
            in = new BufferedReader(new FileReader(filename), buffer);
            String line = in.readLine();
            String author = null;
            while(line!=null){
                str = str+line+"\n";
                author = line;
                line = in.readLine();
            }
            String title = str.substring(0,str.indexOf("\n"));
            System.out.println(ANSI_CYAN+"\nTitle : "+ANSI_YELLOW+title+ANSI_BLACK);
            System.out.println(ANSI_CYAN+"Author : "+ANSI_YELLOW+author+ANSI_BLACK+"\n");
            String body = str.substring(str.indexOf("\n"),str.indexOf(author));
            body = body.trim();
            System.out.println(ANSI_YELLOW+body+ANSI_BLACK);
            add = new article(title, author, body);
            return add;
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
            return null;
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
        }
    }

    public article getArticle(){
        article add;
        try {
            String str="";
            in = new BufferedReader(new FileReader(filename), buffer);
            String line = in.readLine();
            String author = null;
            while(line!=null){
                str = str+line+"\n";
                author = line;
                line = in.readLine();
            }
            String title = str.substring(0,str.indexOf("\n"));
            String body = str.substring(str.indexOf("\n"),str.indexOf(author));
            body = body.trim();
            add = new article(title, author, body);
            return add;
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
            return null;
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
        }
    }

    public void writeFile(String text){
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true), buffer));
            out.println(text);
            out.flush();
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
        }
        finally {
            out.close();
        }
    }

    public void writeFile(article arr){
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename),buffer));
            out.println(arr.getArticle_title()+System.lineSeparator());
            String str = arr.getArticle_body();
            out.println(str);
            out.print(arr.getArticle_author());
            out.flush();
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
        }
        finally {
            out.close();
        }
    }

    public int delFile(){
        try{
            File file = new File(filename);
            if(!file.exists()){
                return 1;
            }
            file.delete();
            return 0;
        }
        catch(Exception ex){
            System.err.println("File Deletion Error : "+ex);
            return 2;
        }
    }

    public File[] getFiles(){
        File dir = new File(".");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".txt")){
                    return true;
                }
                return false;
            }
        };
        return dir.listFiles(filter);
    }

    public void authorIndex(article add){
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter("authorIndex.index", true), buffer));
            out.println(add.getArticle_author()+"\t"+add.getArticle_title());
            out.flush();
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
        }
        finally {
            out.close();
        }
    }

    public void delauthorIndex(String title){
        try{

            File file = new File("authorIndex.index");
            List<String> out = Files.lines(file.toPath())
                    .filter(line -> !line.contains(title))
                    .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
        }
    }

    public List<String> getAuthorArticles(String author){
        try{
            List<String> fileList = new ArrayList<String>();
            in = new BufferedReader(new FileReader("authorIndex.index"), buffer);
            String line = in.readLine();
            while(line!=null){
                String columns[] = line.split("\t");
                String auth = columns[0];
                String fname = columns[1];
                if(auth.equals(author)){
                    fileList.add(fname);
                }
                line = in.readLine();
            }
            return fileList;
        }
        catch(IOException ex){
            System.err.println("Error Opening File : "+filename+" "+ex);
            return null;
        }
        finally {
            try{
                in.close();
            }
            catch (IOException ex){
                System.err.println("Error Closing File : "+filename+" "+ex);
            }
            catch(NullPointerException ex){
                return null;
            }
        }
    }


    public static void main(String args[]){
        fileHandler fh = new fileHandler("Random.txt");
        File f[] = fh.getFiles();
        for(int i=0;i<f.length;i++){
            System.out.println(f[i].getName()+" "+f[i].getAbsolutePath());
        }
        System.out.println(f.length);
        fh.readArticle();

    }
}