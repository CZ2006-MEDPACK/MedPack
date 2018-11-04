package com.example.csyvi.medpack;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class BookAppointmentScheduleActivity extends AppCompatActivity {


    /**
     * The date display text view
     */

    TextView dateDisplay;

    /**
     * The date selector button
     */
    ImageButton dateSelect;

    /**
     * The date picker dialog
     */
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookappointment_selectdatetime);

        dateDisplay = findViewById(R.id.dateDisplayField);
        dateSelect = findViewById(R.id.pickDateIcon);

        dateSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookAppointmentScheduleActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateDisplay.setText(date);
            }
        };

        Spinner dropdown= findViewById(R.id.timeDropdown);

        String[] timings = getResources().getStringArray(R.array.array_timeslots);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.dropdown_layout,R.id.dropdownText, timings);

        dropdown.setAdapter(adapter);
    }
}
