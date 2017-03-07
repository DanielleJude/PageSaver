package kled.pagesaver;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Yu Liu on 3/6/2017.
 */

public class GoalsReminderActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<GoalEntry>>{

    public ArrayList<GoalEntry> entries;
    private GoalsReminderAdapter goalsReminderAdapter;
    private GoalsDbHelper goalsDatabase;

    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_reminder);

        goalsDatabase = new GoalsDbHelper(this);
        entries = new ArrayList<GoalEntry>();

        // Load entries
        loaderManager = this.getLoaderManager();
        loaderManager.initLoader(1, null, this);

        // Set up list view
        ListView entryView = (ListView) findViewById(R.id.goals_reminder_list);
        goalsReminderAdapter = new GoalsReminderAdapter(this, entries);
        entryView.setAdapter(goalsReminderAdapter);
    }

    /**
     * Update entries after a change
     */
    @Override
    public void onResume() {
        super.onResume();
        updateEntries();
    }

    /**
     * Update entries after there has been a change
     */
    public void updateEntries() {
        loaderManager.initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<GoalEntry>> onCreateLoader(int id, Bundle args) {
        LoadGoalEntries loadGoalEntries = new LoadGoalEntries(this);
        return loadGoalEntries;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<GoalEntry>> loader, ArrayList<GoalEntry> data) {
        // Clear and reload list to be displayed
        entries.clear();
        entries.addAll(data);
        goalsReminderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GoalEntry>> loader) {
        // Clear all data
        entries.clear();
        goalsReminderAdapter.notifyDataSetChanged();
    }
}

