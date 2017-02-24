package kled.pagesaver;

import java.util.ArrayList;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class Search {
    int mSearchBy;
    String keyWord;
    BookEntryDbHelper database;

    public Search(int searchBy, String search) {
        mSearchBy = searchBy;
        keyWord = search.trim().toLowerCase();
    }

    public ArrayList<BookEntry> findAllEntries(){
        if(mSearchBy == 0){
            return findTitle();
        } else if(mSearchBy == 1){
            return findAuthor();
        } else if(mSearchBy == 2){
            return findGenre();
        }else if(mSearchBy == 3){
            return findRating();
        } else if(mSearchBy == 4){
            return findComment();
        } else if(mSearchBy == 5){
            return findStatus();
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<BookEntry> findTitle(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getTitle().equals(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<BookEntry> findAuthor(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getAuthor().equals(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<BookEntry> findGenre(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getGenre() == Integer.valueOf(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<BookEntry> findRating(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getRating() == Integer.valueOf(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<BookEntry> findComment(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getComment().contains(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }


    public ArrayList<BookEntry> findStatus(){
        ArrayList<BookEntry> result = new ArrayList<>();
        for(BookEntry entry : database.fetchEntries()) {
            if(entry.getStatus() == Integer.valueOf(keyWord)){
                result.add(entry);
            }
        }
        return result;
    }

}
