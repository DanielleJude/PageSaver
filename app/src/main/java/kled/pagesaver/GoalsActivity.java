package kled.pagesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GoalsActivity extends AppCompatActivity implements View.OnClickListener {

    public ArrayList<GoalEntry> entries;
    private GoalsAdapter goalsAdapter;
    private GoalsDbHelper goalsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        //goalsDatabase = new GoalsDbHelper(this);
        //entries = goalsDatabase.fetchEntries();

        entries = new ArrayList<GoalEntry>();
        entries.add(new GoalEntry("Hello", "hello", 3, 1));

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
                intent.putExtra(GoalsDbHelper.KEY_GOALS_PAGES, viewedEntry.getPagesToComplete());
                intent.putExtra(GoalsDbHelper.KEY_PAGES_INCREMENT, viewedEntry.getDailyPages());

                if (viewedEntry.getEndTime() != null) {
                    intent.putExtra(GoalsDbHelper.KEY_END_TIME, viewedEntry.getEndTime());
                }

                startActivity(intent);
            }
        });
    }

    /**
     * Add goal
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                Intent intent = new Intent(this, AddGoalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    // Toolbar to add
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goal_menu, menu);
        return true;
    }
}
