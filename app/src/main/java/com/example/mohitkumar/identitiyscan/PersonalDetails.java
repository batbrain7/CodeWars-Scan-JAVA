package com.example.mohitkumar.identitiyscan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class PersonalDetails extends AppCompatActivity {

    ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
    String url = "http://192.168.0.2:8000/Personal_Details/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("Personal Details");
        getSupportActionBar().setTitle("#"+s);
        imageView1 = (ImageView) findViewById(R.id.prof_img);
        imageView2 = (ImageView) findViewById(R.id.nav);
        imageView3 = (ImageView) findViewById(R.id.callicon);
        imageView4 = (ImageView) findViewById(R.id.msg_icon);
        imageView5 = (ImageView) findViewById(R.id.send_btn);
        textView1 = (TextView)findViewById(R.id.id);
        textView2 = (TextView)findViewById(R.id.name2);
        textView3 = (TextView)findViewById(R.id.address);
        textView4 = (TextView)findViewById(R.id.mother_name);
        textView5 = (TextView)findViewById(R.id.father_name);
        textView6 = (TextView)findViewById(R.id.number);
        textView7 = (TextView)findViewById(R.id.mail);

        if(s.equals("221720"))
        {
            imageView1.setImageResource(R.drawable.moh);
        }
        else
        {
            imageView1.setImageResource(R.drawable.vasu1);
        }
        url = url + s + ".json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG",url);
                           // id = response.getString("id");
                            //barcode = response.getString("Barcode");
                            textView1.setText(response.getString("id"));
                            textView2.setText(response.getString("Name"));
                            textView3.setText(response.getString("Address"));
                            textView4.setText(response.getString("Mothers_Name"));
                            textView5.setText(response.getString("Fathers_Name"));
                            textView6.setText(response.getString("Contact_No"));
                            textView7.setText(response.getString("Email"));

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
        MySingleton.getInstance(PersonalDetails.this).addtorequestqueue(jsonObjectRequest);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH,"geo:0,0?q="+textView3.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textView6.getText().toString()));
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{textView7.getText().toString()});
                Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
            }
        });

    }
}
