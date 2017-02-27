package kled.pagesaver;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GoalsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<GoalEntry>> {

    public ArrayList<GoalEntry> entries;
    private GoalsAdapter goalsAdapter;
    private GoalsDbHelper goalsDatabase;

    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goalsDatabase = new GoalsDbHelper(this);
        entries = new ArrayList<GoalEntry>();

        // Load entries
        loaderManager = this.getLoaderManager();
        loaderManager.initLoader(1, null, this);

        // Set up list view
        ListView entryView = (ListView) findViewById(R.id.goals_list);
        goalsAdapter = new GoalsAdapter(this, entries);
        entryView.setAdapter(goalsAdapter);

        entryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ViewGoalActivity.class);
                GoalEntry viewedEntry = entries.get(position);

                intent.putExtra(GoalsDbHelper.KEY_ID, viewedEntry.getId());
                intent.putExtra(GoalsDbHelper.KEY_TITLE, viewedEntry.getBookTitle());
                intent.putExtra(GoalsDbHelper.KEY_DESCRIPTION, viewedEntry.getDescription());
                intent.putExtra(GoalsDbHelper.KEY_GOALS_PAGES,
                        Integer.toString(viewedEntry.getPagesToComplete()));
                intent.putExtra(GoalsDbHelper.KEY_PAGES_INCREMENT,
                        Integer.toString(viewedEntry.getDailyPages()));
                intent.putExtra(GoalsDbHelper.KEY_PROGRESS,
                        Integer.toString(viewedEntry.getReadPages()));

                if (viewedEntry.getEndTime() != null) {
                    intent.putExtra(GoalsDbHelper.KEY_END_TIME, viewedEntry.getEndTime());
                }

                startActivity(intent);
            }
        });
    }

    // Toolbar to add
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // Add Entry Page
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu_item:
                Intent intent = new Intent(this, AddGoalActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
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
        goalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GoalEntry>> loader) {
        // Clear all data
        entries.clear();
        goalsAdapter.notifyDataSetChanged();
    }
}
