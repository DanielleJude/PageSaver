package kled.pagesaver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener{
    private LatLng chosenLatLng = null;

    private EditText mTitleView;
    private EditText mAuthorView;
    private EditText mGenreView;
    private EditText mProgressSoFarView;
    private EditText mTotalPagesView;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private EditText mDurationHour;
    private EditText mDurationMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_add_book_view);
        fab.setOnClickListener(this);

        setUpUIConnections();
    }


    //REGION button call backs
    public void onAddLocationClick(View view) {
        //Get a location from the mapview!
    }

    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.fab_add_add_book_view) {
            EntryDatastoreHelper datastoreHelper = new EntryDatastoreHelper(this);
            BookEntry entry = retrieveUIInfo();
            datastoreHelper.addEntry(entry);

            finish();

            //TODO if title author and rating, add to datastore
            //TODO show dialog if entries missing
        }
    }

    //TODO: orientation changes --> saving state

    //TODO: retrieving info and sending to server to add
    private BookEntry retrieveUIInfo() {
        BookEntry entry = new BookEntry();

        entry.setStatus(BookEntry.STATUS_CURRENT);

        entry.setTitle(mTitleView.getText().toString());
        entry.setAuthor(mAuthorView.getText().toString());
        entry.setGenre(mGenreView.getText().toString());

        //Deal with progress entries
        int progressSoFar = Integer.parseInt(mProgressSoFarView.getText().toString());
        if(progressSoFar > 0)
            entry.addPageRange(0, progressSoFar);

        entry.setTotalPages(Integer.parseInt(mTotalPagesView.getText().toString()));

        Calendar cal = Calendar.getInstance();
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        cal.set(year, month, day, hour, minute);
        long startTime = cal.getTimeInMillis();

        //Get end time
        long minutes = Long.parseLong(mDurationMinute.getText().toString());
        long hours = Long.parseLong(mDurationHour.getText().toString());
        long endTime = startTime + 3600000 * hours + 60000 * minutes;

        if(endTime > startTime)
            entry.addStartEndTime(startTime, endTime);


        //Add Location if any
        if(chosenLatLng != null)
            entry.addLatLng(chosenLatLng);

        return entry;

    }

    private void setUpUIConnections() {
        mTitleView = (EditText)findViewById(R.id.add_book_title);
        mAuthorView = (EditText)findViewById(R.id.add_book_author);
        mGenreView = (EditText)findViewById(R.id.add_book_genre);
        mProgressSoFarView = (EditText)findViewById(R.id.add_book_progress_so_far);
        mTotalPagesView = (EditText)findViewById(R.id.add_book_total_pages);
        mDatePicker = (DatePicker)findViewById(R.id.date_picker_add_book);
        mTimePicker = (TimePicker)findViewById(R.id.time_picker_add_book);
        mDurationHour = (EditText)findViewById(R.id.add_book_duration_hour);
        mDurationMinute = (EditText)findViewById(R.id.add_book_duration_minute);

    }

}
