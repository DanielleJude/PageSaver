package kled.pagesaver;

/**
 * Created by eloisedietz on 2/24/17.
 * Modified by Danielle for use on appside
 */

public class Entry {

    public static final String PARENT_KIND = "parent kind";
    public static final String PARENT_IDENTIFIER = "parent identifier";
    public static final String ENTITY_NAME = "entry";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String GENRE = "genre";
    public static final String RATING = "rating";
    public static final String COMMENT = "comment";
    public static final String STATUS = "status";

    private String mId;

    private String mTitle;
    private String mAuthor;
    private String mGenre;
    private int mRating;
    private String mComment;
    private String mStatus;


    public void setId(String id){
        mId = id;
    }

    public String  getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public int getRating() {
        return mRating;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getComment() {
        return mComment;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

}
