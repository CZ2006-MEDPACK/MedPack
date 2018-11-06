package com.example.csyvi.medpack.vitalsign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.csyvi.medpack.others.HelpFragment;
import com.example.csyvi.medpack.others.HomeFragment;
import com.example.csyvi.medpack.login.LoginActivity;
import com.example.csyvi.medpack.others.MedicalEventsFragment;
import com.example.csyvi.medpack.R;
import com.example.csyvi.medpack.others.SettingFragment;
import com.example.csyvi.medpack.login.UpdateProfileFragment;
import com.example.csyvi.medpack.appointment.AppointmentFragment;
import com.google.firebase.auth.*;
//import com.google.firebase.auth.FirebaseAuth;

public class MeasureVitalSignsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.csyvi.medpack.R.layout.activity_main);

        Toolbar toolbar = findViewById(com.example.csyvi.medpack.R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(com.example.csyvi.medpack.R.id.drawer_layout);
        NavigationView navigationView = findViewById(com.example.csyvi.medpack.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                com.example.csyvi.medpack.R.string.navigation_drawer_open, com.example.csyvi.medpack.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            // opens the VitalSignsFragment instead of HomeFragment to get to the InputVitalSigns UI
            getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                    new VitalSignsFragment()).commit();

            navigationView.setCheckedItem(com.example.csyvi.medpack.R.id.home_page);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case com.example.csyvi.medpack.R.id.home_page:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case com.example.csyvi.medpack.R.id.update_profile:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new UpdateProfileFragment()).commit();
                break;

            case com.example.csyvi.medpack.R.id.show_records:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new AppointmentFragment()).commit();
                break;

            case com.example.csyvi.medpack.R.id.search_events:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new MedicalEventsFragment()).commit();
                break;

            case com.example.csyvi.medpack.R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new SettingFragment()).commit();
                break;

            case com.example.csyvi.medpack.R.id.help:
                getSupportFragmentManager().beginTransaction().replace(com.example.csyvi.medpack.R.id.fragment_container,
                        new HelpFragment()).commit();
                break;

            case R.id.logoutAccount:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(MeasureVitalSignsActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MeasureVitalSignsActivity.this, "Logging out..", Toast.LENGTH_SHORT).show();
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

}