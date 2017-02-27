package kled.pagesaver;

/**
 * Created by kelle on 2/26/2017.
 */

public class GoalEntry {
    private Long id;
    private String bookTitle;
    private String description;
    private int pagesToComplete;
    private int dailyPages;
    private Long endTime;

    public GoalEntry() {
        bookTitle = "";
        description = "";
        pagesToComplete = 0;
        dailyPages = 0;
    }

    public GoalEntry(String _bookTitle, String _description, int _totalPages, int _dailyPages) {
        bookTitle = _bookTitle;
        description = _description;
        pagesToComplete = _totalPages;
        dailyPages = _dailyPages;

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

    public int getPagesToComplete() {
        return pagesToComplete;
    }

    public void setPagesToComplete(int pagesToComplete) {
        this.pagesToComplete = pagesToComplete;
    }

    public int getDailyPages() {
        return dailyPages;
    }

    public void setDailyPages(int dailyPages) {
        this.dailyPages = dailyPages;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
