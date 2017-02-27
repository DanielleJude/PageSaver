package kled.pagesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewCurrentBookActivity extends AppCompatActivity {
    public final static String ID_BUNDLE_KEY = "_idbundle key";
    private long mEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to edit activity
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_mark_complete);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to mark as complete activity
            }
        });

        Bundle bundle = getIntent().getExtras();
        mEntryId = bundle.getLong(ID_BUNDLE_KEY);

        //TODO QUERY FOR ENTRY
    }

    //TODO ORIENTATION CHANGES

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.current_book_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.share_menu_item:
                //TODO SHARE FUNCTIONALITY
                break;

            case R.id.delete_menu_item:
                EntryDatastoreHelper datastoreHelper = new EntryDatastoreHelper(this);
                datastoreHelper.deleteEntry(""+mEntryId);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
