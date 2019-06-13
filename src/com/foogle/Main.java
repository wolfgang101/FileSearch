package com.foogle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static int resultcount=0;
    public static final int MAX = 15;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                System.out.println(ANSI_BLUE_BACKGROUND+ANSI_BLACK+"\n\t    _______ __        _____                      __  \n" +
                        "\t   / ____(_) /__     / ___/___  ____ ___________/ /_ \n" +
                        "\t  / /_  / / / _ \\    \\__ \\/ _ \\/ __ `/ ___/ ___/ __ \\\n" +
                        "\t / __/ / / /  __/   ___/ /  __/ /_/ / /  / /__/ / / /\n" +
                        "\t/_/   /_/_/\\___/   /____/\\___/\\__,_/_/   \\___/_/ /_/ \n" +
                        "                                                     \n\n"+ANSI_BLACK);

                int choice = -1;
                while(true){
                    System.out.println(ANSI_BLACK+"\n\nChoose From The Options Below : \n");
                    System.out.println("1. Add Article");
                    System.out.println("2. Delete Article");
                    System.out.println("3. Search Articles By Title");
                    System.out.println("4. Search Articles By Author");
                    System.out.println("5. Search Articles Using PhraseSearch");
                    System.out.println("6. Exit\n");
                    try {
                        choice = Integer.parseInt(br.readLine());
                    }
                    catch (IOException ex){
                        System.out.println("IO Exception");
                        break;
                    }
                    catch (NumberFormatException ex){
                        System.out.println("Invalid Input, Please Choose A Valid Option");
                        continue;
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                        break;
                    }
                    switch (choice) {
                        case 1:
                            addArticle();
                            break;
                        case 2:
                            delArticle();
                            break;
                        case 3:
                            searchTitle();
                            break;
                        case 4:
                            searchAuthor();
                            break;
                        case 5:
                            searchPhrase();
                            break;
                        case 6:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("\nInvalid Input, Please Choose A Valid Option.");
                            break;
                    }
                }
            }

            public static void addArticle(){
                Scanner in = new Scanner(System.in);
                System.out.println("Enter Title : ");
                String title = in.nextLine();
                File file = new File(title+".txt");
                if(file.exists()){
                    System.out.println("Article With Title Already Exists!");
                    return;
                }
                fileHandler fhand = new fileHandler(title+".txt");
                System.out.println("Enter Author : ");
                String author = in.nextLine();
                String line = "";
                String body = "";
                System.out.println("Press Return/Enter Twice To Exit.");
                System.out.println("Enter Body : ");
                line = in.nextLine();
                do{
                    body += line + System.lineSeparator();
                    line = in.nextLine();
                }while (!line.equals(""));
                article add = new article(title, author, body);
                fhand.writeFile(add);
                fhand.authorIndex(add);
                return;
            }

            public static void delArticle(){
                Scanner in = new Scanner(System.in);
                System.out.println("Enter Title : ");
                String title = "";
                title = in.nextLine();
                if(title.equals("")){
                    System.out.println("Article Deletion Failed, Please Try Again!");
                    return;
                }
                fileHandler fhand = new fileHandler(title+".txt");
                int i = fhand.delFile();
                if(i==0){
                    fhand.delauthorIndex(title);
                    System.out.println("Article Deleted\n");
                }
                else if(i==1){
                    System.err.println("Article Doesn't Exist!");
                }
                else{
                    System.out.println("Article Deletion Failed, Please Try Again!");
                }
                return;
            }

            public static void searchTitle(){
                Scanner in = new Scanner(System.in);
                System.out.println("Search Article : ");
                String title = in.nextLine();
                File file = new File(title+".txt");
                if(file.exists()){
                    fileHandler fhand = new fileHandler(title+".txt");
                    fhand.readArticle();
                    System.out.println("\nPress Return/Enter Twice To Exit.");
                    for(int i=0;i<2;i++){
                        String line = in.nextLine();
                        if(line.equals("")){
                            continue;
                        }
                    }
                    return;
                }
                else{
                    System.out.println(ANSI_RED+"\nArticle Not Found!\n"+ANSI_BLACK);
                    return;
                }
            }

            public static void searchPhrase(){
                resultcount=0;
                Scanner in = new Scanner(System.in);
                System.out.println("Search Phrase : ");
                String pattern = in.nextLine();
                Instant start = Instant.now();
                fileHandler fhand = new fileHandler();
                File textFiles[] = fhand.getFiles();
                article add[] = new article[textFiles.length];
                for(int i=0;i<textFiles.length;i++){
                    add[i] = new fileHandler(textFiles[i].getName()).getArticle();
                }
                Runnable run[] = new Runnable[textFiles.length];
                CountDownLatch latch = new CountDownLatch(textFiles.length);
                for(int i=0;i<textFiles.length;i++){
                    run[i] = new patternmatch(pattern,textFiles[i].getName(), latch);
                }
                System.out.println("Finding pattern in articles...");
                ExecutorService pool = Executors.newFixedThreadPool(MAX);
                for(int i=0;i<textFiles.length;i++){
                    pool.execute(run[i]);
                }
                try{
                    latch.await();
                }
                catch (Exception ex){
                    System.out.println("Thread Interruped!");
                }
                if(resultcount==0){
                    System.err.println("\nNo Files Found With Pattern : "+pattern);
                    return;
                }
                else {
                    Instant finish = Instant.now();
                    System.out.println("\nResults Found in " + Duration.between(start, finish).toMillis() + " ms");
                }
                if(pool.isTerminated()){
                    return;
                }
            }

            public static void searchAuthor(){
                Scanner in = new Scanner(System.in);
                System.out.println("Search Author : ");
                String author = in.nextLine();
                Instant start = Instant.now();
                fileHandler fhand = new fileHandler();
                List<String> fileList = fhand.getAuthorArticles(author);
                if(fileList.isEmpty()){
                    System.out.println(ANSI_RED+"No Articles Found By "+author+ANSI_BLACK);
                    return;
                }
                System.out.println("Articles By "+author+" : ");
                for(String iterator : fileList){
                    System.out.println(ANSI_YELLOW+iterator+ANSI_BLACK);
                }
                Instant finish = Instant.now();
                System.out.println("\nResults Found in "+Duration.between(start, finish).toMillis()+" ms");
                return;
            }
    }