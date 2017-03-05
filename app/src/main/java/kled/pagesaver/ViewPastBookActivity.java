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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewPastBookActivity extends AppCompatActivity {
    public final static String ID_BUNDLE_KEY = "_idbundle key";
    private long mEntryId;
    private BookEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_book_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        mEntryId = bundle.getLong(ID_BUNDLE_KEY);

        entry = new BookEntryDbHelper(this).fetchEntryByIndex(mEntryId);

        setUpUI();

    }

    public void setUpUI() {
        ((TextView)findViewById(R.id.past_book_view_title)).setText(entry.getTitle());
        ((TextView)findViewById(R.id.past_book_view_author)).setText(entry.getAuthor());
        ((TextView)findViewById(R.id.past_book_view_genre)).setText(Search.getAllGenres().get(entry.getGenre()));
        ((RatingBar)findViewById(R.id.past_book_view_rating_bar)).setRating(entry.getRating());
        ((TextView)findViewById(R.id.past_book_view_comments)).setText(entry.getComment());

        if(entry.getLocationList().size() == 0) {
            ((Button)findViewById(R.id.show_map_view_button)).setVisibility(View.GONE);
        }

    }

    //TODO ADD PROGRESS GRAPH

    public void onShowMapViewClick(View view) {
        //TODO CHECK
        Intent intent = new Intent(this, PSMapActivity.class);
        Bundle extras = new Bundle();
        extras.putString(PSMapActivity.MAP_MODE, PSMapActivity.VIEW_SINGLE_ENTRY);
        extras.putByteArray(PSMapActivity.LOCATIONS_LIST, entry.getLocationByteArray());
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.past_book_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.delete_menu_item:
                //Delete from datastore
                EntryDatastoreHelper datastoreHelper = new EntryDatastoreHelper(this);
                datastoreHelper.deleteEntry(""+mEntryId);

                //Delete from local db
                new BookEntryDbHelper(this).removeEntry(mEntryId);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
