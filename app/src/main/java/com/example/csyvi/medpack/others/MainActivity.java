package com.example.csyvi.medpack.others;

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

import com.example.csyvi.medpack.R;
import com.example.csyvi.medpack.appointment.AppointmentFragment;
import com.example.csyvi.medpack.login.LoginActivity;
import com.example.csyvi.medpack.login.UpdateProfileFragment;
import com.example.csyvi.medpack.medicalprescription.PrescriptionFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;

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

            /*
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ListPatient", patientList);
            homeFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();

             */

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
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

            case R.id.update_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new UpdateProfileFragment()).commit();
                    break;

            case R.id.show_records:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AppointmentFragment()).commit();
                break;

            case R.id.med_scheduler:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PrescriptionFragment()).commit();
                break;

            case R.id.search_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MedicalEventsFragment()).commit();
                break;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingFragment()).commit();
                break;

            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).commit();
                break;

            case R.id.logoutAccount:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logging out..", Toast.LENGTH_SHORT).show();
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