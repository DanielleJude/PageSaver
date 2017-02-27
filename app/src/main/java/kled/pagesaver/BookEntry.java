package kled.pagesaver;

import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.util.Base64;


import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class BookEntry {
    private Long mRowId;
    private String mPhoneId; // unique phone ID

    private String mTitle;
    private String mAuthor;
    private String mGenre;
    private int mRating;
    private String mComment;
    private int mStatus;
    private String mQuote;
    private ArrayList<LatLng> mLocationList;
    private ArrayList<StartEndTimes> mTimeList;
    private ArrayList<StartEndPages> mPageList;
    private int mTotalPages;   // number of total pages

    public static final String ID = "id";
    public static final String PHONE_ID = "phone_id";
    public static final String REG_ID = "reg_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String GENRE = "genre";
    public static final String RATING = "rating";
    public static final String COMMENT = "comment";
    public static final String STATUS = "status";
    public static final String QUOTE = "quote";
    public static final String LOCATION_LIST = "location_list";
    public static final String TIMES_LIST = "times_list";
    public static final String PAGES_LIST = "pages_list";
    public static final String PAGES = "total_pages";

    public static final int STATUS_PAST = 1;
    public static final int STATUS_CURRENT = 0;

    public BookEntry() {

        mRowId = -1l;
        mLocationList = new ArrayList<>();
        mTimeList = new ArrayList<>();
        mPageList = new ArrayList<>();

    }

    /* List of duration ranges for each individual reading period */
    public class StartEndTimes {
        Long startTime;  // start times in milliseconds
        Long endTime;    // end times in milliseconds

        public StartEndTimes(Long mStartTime, Long mEndTime){
            startTime = mStartTime;
            endTime = mEndTime;
        }
    }


    /* List of page ranges for each individual reading period */
    public class StartEndPages {
        int startPage;  // page number where this reading starts
        int endPage;    // page number where this reading finishes

        public StartEndPages(int mStartPage, int mEndPage){
            startPage = mStartPage;
            endPage = mEndPage;
        }
    }


    public void setRowId(Long rowId){
        mRowId = rowId;
    }

    public Long getRowId() {
        return mRowId;
    }

    public void setPhoneId(String phoneId){
        mPhoneId = phoneId;
    }

    public String getPhoneId() {
        return mPhoneId;
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

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getStatus() {
        return mStatus;
    }

    public boolean isCompleted() {

        return mStatus == STATUS_PAST;
    }

    public void setQuote(String quote) {
        mQuote = quote;
    }

    public String getQuote() {
        return mQuote;
    }

    public ArrayList<LatLng> getLocationList() {
        return mLocationList;
    }

    // Convert Location ArrayList to byte array to store in SQLite database
    public byte[] getLocationByteArray() {
        int[] intArray = new int[mLocationList.size() * 2];

        for (int i = 0; i < mLocationList.size(); i++) {
            intArray[i * 2] = (int) (mLocationList.get(i).latitude * 1E6);
            intArray[(i * 2) + 1] = (int) (mLocationList.get(i).longitude * 1E6);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(intArray.length
                * Integer.SIZE);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);

        return byteBuffer.array();
    }

    // Convert byte array to Location ArrayList
    public void setLocationListFromByteArray(byte[] bytePointArray) {
        if(bytePointArray.length == 0)
            return;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytePointArray);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        int[] intArray = new int[bytePointArray.length / Integer.SIZE];
        intBuffer.get(intArray);

        int locationNum = intArray.length / 2;

        for (int i = 0; i < locationNum; i++) {
            LatLng latLng = new LatLng((double) intArray[i * 2] / 1E6F,
                    (double) intArray[i * 2 + 1] / 1E6F);
            mLocationList.add(latLng);
        }
    }

    public void addLatLng(LatLng latLng) {
        mLocationList.add(latLng);
    }

    public ArrayList<StartEndTimes> getTimeList() {
        return mTimeList;
    }

    // Convert start and end times ArrayList to byte array to store in SQLite database
    public byte[] getTimeByteArray() {
        long[] longArray = new long[mTimeList.size() * 2];

        for (int i = 0; i < mTimeList.size(); i++) {
            longArray[i * 2] = mTimeList.get(i).startTime;
            longArray[(i * 2) + 1] = mTimeList.get(i).endTime;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(longArray.length
                * Integer.SIZE);
        LongBuffer longBuffer = byteBuffer.asLongBuffer();
        longBuffer.put(longArray);

        return byteBuffer.array();
    }

    // Convert byte array to time ArrayList
    public void setTimeListFromByteArray(byte[] bytePointArray) {
        if(bytePointArray.length == 0)
            return;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytePointArray);
        LongBuffer longBuffer = byteBuffer.asLongBuffer();

        long[] longArray = new long[bytePointArray.length / Integer.SIZE];
        longBuffer.get(longArray);

        int timeNum = longArray.length / 2;

        for (int i = 0; i < timeNum; i++) {
            StartEndTimes startEndTimes = new StartEndTimes(longArray[i * 2], longArray[i * 2 + 1]);
            mTimeList.add(startEndTimes);
        }
    }

    public void addStartEndTime(Long start, Long end) {
        mTimeList.add(new StartEndTimes(start, end));
    }

    public ArrayList<StartEndPages> getPageList() {
        return mPageList;
    }

    // Convert ArrayList for pages to byte array to store in SQLite database
    public byte[] getPageByteArray() {
        int[] intArray = new int[mPageList.size() * 2];

        for (int i = 0; i < mPageList.size(); i++) {
            intArray[i * 2] = mPageList.get(i).startPage;
            intArray[(i * 2) + 1] = mPageList.get(i).endPage;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(intArray.length
                * Integer.SIZE);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);

        return byteBuffer.array();
    }

    // Convert byte array to ArrayList for pages
    public void setPageListFromByteArray(byte[] bytePointArray) {
        if(bytePointArray.length == 0)
            return;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytePointArray);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        int[] intArray = new int[bytePointArray.length / Integer.SIZE];
        intBuffer.get(intArray);

        int pageNum = intArray.length / 2;

        for (int i = 0; i < pageNum; i++) {
            StartEndPages startEndPages = new StartEndPages(intArray[i * 2], intArray[i * 2 + 1]);
            mPageList.add(startEndPages);
        }
    }

    public void addPageRange(int start, int end) {
        mPageList.add(new StartEndPages(start, end));
    }

    public int getFurthestPageRead() {
        int last = mPageList.size() - 1;
        if(last < 0)
            return 0;


        return mPageList.get(last).endPage;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    //Put the current entry into a map to send to server;
    public void entryToMap(Map<String, String> map) {
        map.put(ID, ""+ mRowId);
        map.put(TITLE, mTitle);
        map.put(AUTHOR, mAuthor);
        map.put(GENRE, mGenre);
        map.put(RATING, ""+mRating);
        map.put(COMMENT, mComment);
        map.put(STATUS, ""+mStatus);
        //TODO add quotes as list
        //map.put(QUOTE, quotes.toString);

        //Convert the locations to byte array then to string using base64
        String byteArrayLocation= new String(Base64.encodeBase64(getLocationByteArray()));
        map.put(LOCATION_LIST, byteArrayLocation);

        //Convert the times to byte array then to string using base64
        String byteArrayTimes= new String(Base64.encodeBase64(getTimeByteArray()));
        map.put(TIMES_LIST, byteArrayTimes);

        //Convert the pages to byte array then to string using base64
        String byteArrayPages= new String(Base64.encodeBase64(getPageByteArray()));
        map.put(PAGES_LIST, byteArrayPages);

        map.put(PAGES, ""+mTotalPages);
    }

    public String getProgressString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format((double)getFurthestPageRead()/mTotalPages * 100) + "% completed";
    }

    public boolean isComplete() {
        if(mStatus == STATUS_PAST)
            return true;

        return false;
    }

}
