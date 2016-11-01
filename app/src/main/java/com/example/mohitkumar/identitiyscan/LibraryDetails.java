package com.example.mohitkumar.identitiyscan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LibraryDetails extends AppCompatActivity {

    TextView issues,fine;
    String url = "http://192.168.0.2:8000/Library/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_details);
        issues = (TextView) findViewById(R.id.issue);
        fine = (TextView) findViewById(R.id.fine);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("Library Details");
        url = url + s + ".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            issues.setText(response.getString("Books_I3sued"));
                            fine.setText("FINE DUE :   " + response.getString("Fine_Due"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(LibraryDetails.this).addtorequestqueue(jsonObjectRequest);
    }
}
