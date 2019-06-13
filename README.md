# FileSearch

FileSearch is designed to search for content within articles that are stored as text files.
Users can add articles, remove articles and search.

Search Options : 
1. Search using title.
2. Search using author name.
3. Search using phrase.

Searching of author name is implemented using indexing where the author name and associated articles are indexed. This allows for faster searching of author name.

Searching of phrase is performed on all articles, where we run the searching of the phrase/pattern for every text file using threads from a thread pool. This allows us to simultaneously search through multiple articles and increase efficiency and reduce time taken.

To run :

-->Import project folder in IntelliJ Idea.
   Run Main.java as main class. OR
                  
-->Navigate to out/production/foogle/
   Run java com.foogle.Main in terminal/powershell.


Mini Project made for 6th sem File Structure mini project under VTU.
