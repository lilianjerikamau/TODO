package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity {

    private EditText eventTitle, eventLocation,eventDescription;
    private TextView   closeTextBtn, saveTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventTitle =(EditText) findViewById(R.id.event_title) ;
        eventLocation =(EditText) findViewById(R.id.event_location) ;
        eventDescription =(EditText) findViewById(R.id.event_description) ;
        closeTextBtn = (TextView) findViewById(R.id.close_event_btn);
        saveTextButton = (TextView) findViewById(R.id.add_event_btn);


        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eventTitle.getText().toString().isEmpty() && !eventLocation.getText().toString().isEmpty() && !eventDescription
                        .getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);

                    intent.putExtra(CalendarContract.Events.TITLE, eventTitle.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com, test2@yahoo.com, test3@yahoo.com");
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(AddEventActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(AddEventActivity.this, "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}