package com.example.csyvi.medpack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * The type Measure vital signs manager.
 */
public class MeasureVitalSignsManager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    /**
     * The Vs.
     */
    VitalSigns vs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            // opens the VitalSignsFragment instead of HomeFragment to get to the InputVitalSigns UI
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new VitalSignsFragment()).commit();

            navigationView.setCheckedItem(R.id.home_page);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.home_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.medication_scheduler:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FirstFragment()).commit();
                break;

            case R.id.show_records:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SecondFragment()).commit();
                break;

            case R.id.search_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ThirdFragment()).commit();
                break;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FourthFragment()).commit();
                break;

            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FifthFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        else
            super.onBackPressed();
    }

    /**
     * This method will check and validate for all the inputted vital signs
     */
    private void checkVitalSigns()
    {
    }

    /**
     * This method will measure the pulse rate of the patient
     */
    private void measurePulseRate()
    {
    }

    /**
     * This method will measure the oxygen saturation of the patient
     */
    private void measureOxygenSaturation()
    {
    }

    /**
     * This method will measure the temperature of the patient
     */
    private void measureTemperature()
    {
    }

    /**
     * This method will measure the blood pressure of the patient
     */
    private void measureBloodPressure()
    {
    }

    /**
     * This method will measure the respiratory rate of the patient
     */
    private void measureRespiratoryRate()
    {
    }

    /**
     * This method will measure the pain scale of the patient
     */
    private void measurePainScale()
    {
    }
}
