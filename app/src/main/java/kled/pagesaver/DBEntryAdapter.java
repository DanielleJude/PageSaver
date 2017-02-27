package kled.pagesaver;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
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


    enum MODE {
        CURRENT, PAST, ALL
    }

    private MODE mode;

    public static final int CURRENT_MODE = 0;
    public static final int PAST_MODE = 1;
    public static final int ALL_MODE = 2;


    public DBEntryAdapter(Activity context, int mode) {
        this.context = context;
        mList = new ArrayList<BookEntry>();

        switch (mode) {
            case CURRENT_MODE:
                this.mode = MODE.CURRENT;
                break;
            case PAST_MODE:
                this.mode = MODE.PAST;
                break;
            default:
                this.mode = MODE.ALL;
                break;
        }
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
        BookEntry entry = mList.get(position);
        switch (mode) {
            case PAST:
                if(convertView == null) {
                    rowView = context.getLayoutInflater().inflate(R.layout.book_entry_row_past, null);
                } else {
                    rowView = convertView;
                }
                rowView = setupPastView(entry, rowView);
                break;

            case CURRENT:
                if(convertView == null) {
                    rowView = context.getLayoutInflater().inflate(R.layout.book_entry_row_current, null);
                } else {
                    rowView = convertView;
                }
                rowView = setupCurrentView(entry, rowView);
                break;

            default:
                if(entry.isCompleted()) {
                    rowView = context.getLayoutInflater().inflate(R.layout.book_entry_row_past, null);
                    rowView = setupPastView(entry, rowView);
                } else {
                    rowView = context.getLayoutInflater().inflate(R.layout.book_entry_row_current, null);
                    rowView = setupCurrentView(entry, rowView);
                }
                break;
        }

        return rowView;
    }

    private View setupPastView(BookEntry entry, View rowView) {
        TextView headerTV = (TextView)rowView.findViewById(R.id.first_tv);
        RatingBar ratingBar = (RatingBar)rowView.findViewById(R.id.rating_bar_lv);

        String headerString = entry.getTitle() + ": " + entry.getAuthor();
        headerTV.setText(headerString);

        ratingBar.setNumStars(entry.getRating());
        ratingBar.setMax(5);
        ratingBar.setClickable(false);

        return rowView;
    }

    private View setupCurrentView(BookEntry entry, View rowView) {
        TextView textView = (TextView)rowView.findViewById(R.id.first_tv_current);
        ProgressBar progressBar = (ProgressBar)rowView.findViewById(R.id.progress_bar_lv);

        String headerString = entry.getTitle() + ": " + entry.getAuthor();
        textView.setText(headerString);

        progressBar.setClickable(false);
        progressBar.setProgress((int)(entry.getFurthestPageRead()/entry.getTotalPages()));
        progressBar.setMax(entry.getTotalPages());

        return rowView;
    }

}
