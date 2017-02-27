package kled.pagesaver;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout mTabBar;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragmentList;
    private PSFragmentPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register with the server
        new GcmRegistrationAsyncTask(this).execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set up the tabbar and viewPager!
        mTabBar = (TabLayout)findViewById(R.id.tab_bar);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        mFragmentList = new ArrayList<Fragment>(3);
        mFragmentList.add(new PreviousBooksFragment());
        mFragmentList.add(new CurrentBooksFragment());
        mFragmentList.add(new SearchFragment());
        //Add the start, history, and settings fragments

        //Bind the tab bar and the view pager for seamless transitions between tabs and fragments
        mViewPagerAdapter = new PSFragmentPagerAdapter(getFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabBar.setupWithViewPager(mViewPager);
        mTabBar.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void onResume() {
        //start to get the entries from the server!
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.add_menu_item:
                Intent intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Intent intent;
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_map:
                intent = new Intent(this, PSMapActivity.class);
                intent.putExtra(PSMapActivity.MAP_MODE,PSMapActivity.VIEW_ALL_ENTRIES);
                startActivity(intent);
                //TODO send locations and names to mapview
                break;
            case R.id.nav_analytics:
                intent = new Intent(this, AnalyticsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_goal_tracker:
                intent = new Intent(this, GoalsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
