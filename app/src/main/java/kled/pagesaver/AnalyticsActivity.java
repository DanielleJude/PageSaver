package kled.pagesaver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

public class AnalyticsActivity extends AppCompatActivity {

    private ColumnChartView monthsChart;
    private ColumnChartData monthsData;
    private ColumnChartView hoursChart;
    private ColumnChartData hoursData;
    private ArrayList<Integer> hoursArray;
    private ArrayList<Integer> monthsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPoints();
        buildTimesGraph();
        buildMonthsGraph();
    }

    /**
     * Dummy Points - will eventually take in list of longs from database entries
     */
    public void getPoints() {

        /* Later - for getting hours and months from list of Longs
        http://stackoverflow.com/questions/907170/java-getminutes-and-gethours
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        calendar.get(Calendar.HOUR);        // gets hour in 12h format
        calendar.get(Calendar.MONTH);       // gets month number, NOTE this is zero based!
         */

        hoursArray = new ArrayList<Integer>();
        monthsArray = new ArrayList<Integer>();

        for (int i = 0; i < 50; i++) {
            hoursArray.add((int)(Math.random() * 24));
            monthsArray.add((int)(Math.random() * 12));
        }
    }

    /**
     * Draw chart of time of day
     */
    public void buildTimesGraph() {

        hoursChart = (ColumnChartView)findViewById(R.id.time_chart);

        // Create columns from entries
        int[] hours = new int[24];

        for (int i = 0; i < hoursArray.size(); i++) {
            int hourObtained = hoursArray.get(i);
            hours[hourObtained]++;
        }

        List<Column> columns = new ArrayList<Column>();

        for (int i = 0; i < hours.length; ++i) {

            List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();
            SubcolumnValue value = new SubcolumnValue(hours[i]);
            values.add(value);

            Column column = new Column(values);
            columns.add(column);

        }

        hoursData = new ColumnChartData(columns);

        // Format axes
        List<AxisValue> hoursLabels = new ArrayList<AxisValue>();

        for (int i = 0; i < hours.length; i++) {

            AxisValue value = new AxisValue(i);

            if (i < 12) {
                if (i == 0) value.setLabel("12 AM");
                else value.setLabel(i + "AM");
            } else {
                if (i == 12) value.setLabel("12 PM");
                else value.setLabel((i - 12) + "PM");
            }

            hoursLabels.add(value);
        }

        Axis axisX = new Axis(hoursLabels);
        axisX.setName("Hour of Day You Start Reading");

        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("Number of Days");

        hoursData.setAxisXBottom(axisX);
        hoursData.setAxisYLeft(axisY);

        // Set data
        hoursChart.setColumnChartData(hoursData);
    }

    /**
     * Draw chart of months of reading
     */
    public void buildMonthsGraph() {

        monthsChart = (ColumnChartView) findViewById(R.id.month_chart);

        // Create columns from entries
        int[] months = new int[12];

        for (int i = 0; i < monthsArray.size(); i++) {
            int monthObtained = monthsArray.get(i);
            months[monthObtained]++;
        }

        List<Column> columns = new ArrayList<Column>();

        for (int i = 0; i < months.length; ++i) {

            List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();
            SubcolumnValue value = new SubcolumnValue(months[i]);
            values.add(value);

            Column column = new Column(values);
            columns.add(column);

        }

        monthsData = new ColumnChartData(columns);

        // Format axes
        List<AxisValue> monthLabels = new ArrayList<AxisValue>();

        for (int i = 0; i < months.length; i++) {

            AxisValue value = new AxisValue(i);

            if (i == 0) value.setLabel("Jan");
            else if (i == 1) value.setLabel("Feb");
            else if (i == 2) value.setLabel("Mar");
            else if (i == 3) value.setLabel("Apr");
            else if (i == 4) value.setLabel("May");
            else if (i == 5) value.setLabel("Jun");
            else if (i == 6) value.setLabel("Jul");
            else if (i == 7) value.setLabel("Aug");
            else if (i == 8) value.setLabel("Sep");
            else if (i == 9) value.setLabel("Oct");
            else if (i == 10) value.setLabel("Nov");
            else if (i == 11) value.setLabel("Dec");

            monthLabels.add(value);
        }

        Axis axisX = new Axis(monthLabels);
        axisX.setName("Month");

        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("Number of Times");

        monthsData.setAxisXBottom(axisX);
        monthsData.setAxisYLeft(axisY);

        // Set data
        monthsChart.setColumnChartData(monthsData);
    }
}
