package kled.pagesaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class MarkCompleteActivity extends AppCompatActivity {
    public final static String ID_BUNDLE_KEY = "_idbundle key";
    private long mEntryId;
    private BookEntry entry;

    private LatLng chosenLatLng = null;
    private final static int MAP_REQUEST_CODE = 5789;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private EditText mDurationHour;
    private EditText mDurationMinute;
    private RatingBar mRatingBar;
    private EditText mCommentsView;


    //bundle keys
    private final static String TIME_KEY = "time";
    private final static String DHOUR_KEY = "dhour";
    private final static String DMINUTE_KEY = "dminute";
    private final static String LAT_BUNDLE_KEY = "latitude";
    private final static String LNG_BUNDLE_KEY = "longitude";
    private final static String RATING_KEY = "rating";
    private final static String COMMENTS_KEY = "comments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_complete);

        Bundle bundle = getIntent().getExtras();
        mEntryId = bundle.getLong(ID_BUNDLE_KEY);

        entry = new BookEntryDbHelper(this).fetchEntryByIndex(mEntryId);

        setUpUIConnections();
    }

    private void setUpUIConnections() {
        mDatePicker = (DatePicker)findViewById(R.id.date_picker_complete_book);
        mTimePicker = (TimePicker)findViewById(R.id.time_picker_complete_book);
        mDurationHour = (EditText)findViewById(R.id.complete_dur_hours);
        mDurationMinute = (EditText)findViewById(R.id.complete_dur_minutes);
        mRatingBar = (RatingBar)findViewById(R.id.complete_book_rating_bar);
        mCommentsView = (EditText)findViewById(R.id.complete_book_comments);
    }

    //REGION button call backs
    public void onAddLocationClick(View view) {
        Intent intent = new Intent(this, PSMapActivity.class);
        Bundle extras = new Bundle();
        extras.putString(PSMapActivity.MAP_MODE, PSMapActivity.PLACE_MARKER_MODE);
        intent.putExtras(extras);
        startActivityForResult(intent, MAP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MAP_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                double lat = bundle.getDouble(PSMapActivity.LAT_KEY, -1);
                double lng = bundle.getDouble(PSMapActivity.LNG_KEY, -1);
                if(lat != -1 && lng != -1)
                    chosenLatLng = new LatLng(lat, lng);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Don't do anything
            }
        }
    }

    public void onSaveButtonClicked(View view) {
        //TODO Save changes
        String errorString = isReadyToAdd();
        if(errorString.equals("")) {
            //Mark as complete and add the final page range
            entry.setStatus(BookEntry.STATUS_PAST);
            entry.addPageRange(entry.getFurthestPageRead(), entry.getTotalPages());

            //get the other updated info
            retrieveUIInfo();
            new BookEntryDbHelper(this).updateEntry(entry);
            finish();
        } else {
            //SHOW DIALOG with error
            makeTextDialog(errorString);
        }
    }

    public void onCancelButtonClicked(View view) {
        finish();
    }

    private String isReadyToAdd() {
        String errorString = "Must enter the following fields to add entry:\n";
        boolean canAddFlag = true;


        if(!fieldNotEmpty(mDurationHour)) {
            canAddFlag = false;
            errorString = errorString + "Hours Spent Reading\n";
        }

        if(!fieldNotEmpty(mDurationMinute)) {
            canAddFlag = false;
            errorString = errorString + "Minutes Spent Reading\n";
        }

        //TODO UNCOMMENT THIS WHEN WESLEY FIXES

//        if(chosenLatLng == null) {
//            errorString = errorString + "Choose a Location\n";
//            canAddFlag = false;
//        }

        if(canAddFlag) {
            return "";
        } else {
            return errorString;
        }


    }

    private boolean fieldNotEmpty(EditText field) {
        if("".equals(field.getText().toString()))
            return false;

        return true;
    }

    private BookEntry retrieveUIInfo() {


        long startTime = getChosenTime();

        //Get end time
        long minutes = Long.parseLong(mDurationMinute.getText().toString());
        long hours = Long.parseLong(mDurationHour.getText().toString());
        long endTime = startTime + 3600000 * hours + 60000 * minutes;

        entry.setRating(mRatingBar.getNumStars());
        entry.setComment(mCommentsView.getText().toString());

        if(endTime > startTime)
            entry.addStartEndTime(startTime, endTime);


        //Add Location if any
        if(chosenLatLng != null)
            entry.addLatLng(chosenLatLng);

        return entry;

    }

    private long getChosenTime() {
        Calendar cal = Calendar.getInstance();
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        cal.set(year, month, day, hour, minute);
        return cal.getTimeInMillis();
    }

    private void makeTextDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {

        saveInstanceState.putLong(TIME_KEY, getChosenTime());
        saveInstanceState.putString(DHOUR_KEY,
                mDurationHour.getText().toString());
        saveInstanceState.putString(DMINUTE_KEY,
                mDurationMinute.getText().toString());

        saveInstanceState.putString(COMMENTS_KEY, mCommentsView.getText().toString());
        saveInstanceState.putInt(RATING_KEY, (int)mRatingBar.getRating());

        if(chosenLatLng != null) {
            saveInstanceState.putDouble(LAT_BUNDLE_KEY, chosenLatLng.latitude);
            saveInstanceState.putDouble(LNG_BUNDLE_KEY, chosenLatLng.longitude);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mDurationHour.setText(savedInstanceState.getString(DHOUR_KEY, ""));
        mDurationMinute.setText(savedInstanceState.getString(DMINUTE_KEY, ""));

        long time = savedInstanceState.getLong(TIME_KEY);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        mDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        mTimePicker.setHour(cal.get(Calendar.HOUR));
        mTimePicker.setMinute(cal.get(Calendar.MINUTE));

        mCommentsView.setText(savedInstanceState.getString(COMMENTS_KEY));
        mRatingBar.setRating(savedInstanceState.getInt(RATING_KEY));

        double lat = savedInstanceState.getDouble(LAT_BUNDLE_KEY, -1);
        double lng = savedInstanceState.getDouble(LNG_BUNDLE_KEY, -1);

        if(lat != -1 && lng != -1)
            chosenLatLng = new LatLng(lat, lng);
        else
            chosenLatLng = null;



    }



}
