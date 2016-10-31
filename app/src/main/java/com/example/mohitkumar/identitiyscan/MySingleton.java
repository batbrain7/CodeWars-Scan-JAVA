package com.example.mohitkumar.identitiyscan;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mohitkumar on 29/10/16.
 */

public class MySingleton {

    private static MySingleton minstance;
    private RequestQueue requestQueue;
    private static Context context;


    public MySingleton(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();

    }
    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context)
    {
        if(minstance == null)
        {
            minstance = new MySingleton(context);
        }
        return minstance;
    }

    public<T> void addtorequestqueue(Request<T> request)
    {
         requestQueue.add(request);
    }
}
