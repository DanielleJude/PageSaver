package kled.pagesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

public class ViewGoalActivity extends AppCompatActivity {

    private GoalsDbHelper goalsDataBase;
    Long entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goalsDataBase = new GoalsDbHelper(this);

        Intent intent = getIntent();

        // Used if user needs to delete entry
        entryId = intent.getLongExtra(GoalsDbHelper.KEY_ID, -1);

        /*
        // Find associated EditText blocks for display
        EditText inputText = (EditText)findViewById(R.id.entryInputType);
        EditText activityText = (EditText)findViewById(R.id.entryActivityType);
        EditText dateText = (EditText)findViewById(R.id.entryDateTime);
        EditText durationText = (EditText)findViewById(R.id.entryDuration);
        EditText distanceText = (EditText)findViewById(R.id.entryDistance);
        EditText caloriesText = (EditText)findViewById(R.id.entryCalories);
        EditText heartRateText = (EditText)findViewById(R.id.entryHeartRate);
        EditText commentText = (EditText)findViewById(R.id.entryComment);

        // Set text according to entry
        inputText.setText(intent.getStringExtra(HistoryFragment.INPUT_FLAG));
        */
    }

    // Make sure delete option is in toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goal_menu, menu);
        return true;
    }

}
