package kled.pagesaver;

/**
 * Created by kelle on 2/26/2017.
 */

public class GoalEntry {
    private String bookTitle;
    private String description;

    public GoalEntry(String _bookTitle, String _description) {
        bookTitle = _bookTitle;
        description = _description;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
