package com.foogle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class fileHandler {
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
            System.out.println("\nTitle :"+title);
            System.out.println("Author : "+author+"\n");
            String body = str.substring(str.indexOf("\n"),str.indexOf(author));
            body = body.trim();
            System.out.println(body);
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
        //Scanner scan = new Scanner(System.in);
        fileHandler fh = new fileHandler("lol.txt");
        //System.out.println("Enter text : ");
        //String str = scan.nextLine();
        //System.out.println("Writing Text: "+str);
        //fh.writeFile(str);
        //System.out.println("Done Writing");
        //System.out.println(fh.getLine("lmao"));
        File f[] = fh.getFiles();
        for(int i=0;i<f.length;i++){
            System.out.println(f[i].getName()+" "+f[i].getAbsolutePath());
        }
        System.out.println(f.length);
        fh.readArticle();

    }
}