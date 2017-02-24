package kled.pagesaver;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class BookEntry {
    private Long mId;

    private String mTitle;
    private String mAuthor;
    private int mGenre;
    private int mRating;
    private String mComment;
    private int mStatus;


    public void setId(Long id){
        mId = id;
    }

    public Long getId() {
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

    public void setGenre(int genre) {
        mGenre = genre;
    }

    public int getGenre() {
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

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getStatus() {
        return mStatus;
    }



}