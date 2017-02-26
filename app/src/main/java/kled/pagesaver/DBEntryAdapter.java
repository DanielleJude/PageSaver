package kled.pagesaver;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danielle on 2/24/17.
 */

public class DBEntryAdapter extends BaseAdapter {
    private Activity context;
    private List<BookEntry> mList;

    public DBEntryAdapter(Activity context) {
        this.context = context;
        mList = new ArrayList<BookEntry>();
    }

    /* Clear adapter for when you want to reload fragment*/
    public void clearAdapter() {
        mList.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return mList.get(position).getRowId();
    }


    /** Update and refresh list*/
    public void addToAdapter(BookEntry entry) {
        mList.add(entry);
        notifyDataSetChanged();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView == null) {
            rowView = context.getLayoutInflater().inflate(R.layout.book_entry_row, null);
        } else {
            rowView = convertView;
        }
        TextView headerTV = (TextView)rowView.findViewById(R.id.first_tv);
        RatingBar ratingBar = (RatingBar)rowView.findViewById(R.id.rating_bar_lv);

        BookEntry entry = mList.get(position);

        String headerString = entry.getTitle() + ": " + entry.getAuthor();
        headerTV.setText(headerString);

        ratingBar.setNumStars(entry.getRating());
        ratingBar.setClickable(false);

        return rowView;
    }
}
