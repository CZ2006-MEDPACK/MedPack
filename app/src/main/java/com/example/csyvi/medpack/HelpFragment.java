package com.example.csyvi.medpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.csyvi.medpack.R;

/**
 * The type Fifth fragment.
 */
public class HelpFragment extends Fragment {

    /*EditText yourName, yourEmail, yourSubject, yourMessage;
    Button postMessage;
    String name,email,subject,message;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_help, container, false);

        /*yourName = view.findViewById(R.id.your_name);
        yourEmail = view.findViewById(R.id.your_email);
        yourSubject = view.findViewById(R.id.your_subject);
        yourMessage = view.findViewById(R.id.your_message);
        postMessage = view.findViewById(R.id.post_message);
        postMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = yourName.getText().toString();
                email = yourEmail.getText().toString();
                subject = yourSubject.getText().toString();
                message = "Greetings from " + name + "! " + yourMessage.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, email);
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Choose app to send your email."));
            }
        });*/

        //return view;
    }
}
