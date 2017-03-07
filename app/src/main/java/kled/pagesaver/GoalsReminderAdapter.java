package kled.pagesaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yu Liu on 3/6/2017.
 */

public class GoalsReminderAdapter extends ArrayAdapter<GoalEntry> {
    private Context mContext;
    private ArrayList<GoalEntry> goalsList;
    private static LayoutInflater inflater = null;

    public GoalsReminderAdapter(Context c, ArrayList<GoalEntry> viewedList) {
        super(c, R.layout.goal_reminder_row, viewedList);
        mContext = c;
        goalsList = viewedList;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return goalsList.size();
    }

    @Override
    public GoalEntry getItem(int position) {
        return goalsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Refresh list
     */
    public void addToAdapter(GoalEntry entry) {
        goalsList.add(entry);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.goal_reminder_row, null);
        }

        TextView title = (TextView) v.findViewById(R.id.goal_reminder_title);
        TextView dailyGoal = (TextView) v.findViewById(R.id.goal_reminder_daily);
        TextView status = (TextView) v.findViewById(R.id.goal_reminder_status);

        title.setText("Book title: " + goalsList.get(position).getBookTitle());
        dailyGoal.setText("Today's goal is page "+ goalsList.get(position).getGoalStartPage()
                + " to page " + goalsList.get(position).getGoalEndPage() + ".");
        status.setText("Not completed yet");

        return v;
    }
}
