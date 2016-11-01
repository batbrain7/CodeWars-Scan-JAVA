package com.example.mohitkumar.identitiyscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PopUpActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        button = (Button) findViewById(R.id.okbutton);
        editText = (EditText) findViewById(R.id.barcodenumber);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpActivity.this,MainActivity.class);
                intent.putExtra("texted",editText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
