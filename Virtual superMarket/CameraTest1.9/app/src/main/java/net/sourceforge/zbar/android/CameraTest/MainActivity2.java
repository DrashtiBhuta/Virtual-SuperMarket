package net.sourceforge.zbar.android.CameraTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity {
    String id;
    CameraTestActivity ct=new CameraTestActivity();

    GlobalClass g = GlobalClass.getInstance();
    String custno=g.getData();

    String name,price,quantity,avail,str1;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    List<String> spinnerArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        CameraTestActivity ca = new CameraTestActivity();

        Intent intent = getIntent();
        id = intent.getStringExtra("message");
        select();
        Button insert=(Button)findViewById(R.id.button);
        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText eq = (EditText) findViewById(R.id.editText);
                quantity = eq.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    Toast.makeText(getBaseContext(), "pls enter quatity",
                            Toast.LENGTH_SHORT).show();
                    Log.e("inside quantity","blank");
                }
                else if(Integer.parseInt(quantity)>Integer.parseInt(avail))
                {
                    Toast.makeText(getBaseContext(), "sorry,quantity over stock",
                            Toast.LENGTH_SHORT).show();
                    Log.e("inside quantity2","stock");
                }
                else {
                    insert();
                }
            }

            });

            Button shop=(Button)findViewById(R.id.button2);
            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent int1=new Intent(MainActivity2.this,CameraTestActivity.class);
                    startActivity(int1);
                }
            });
        Button cart=(Button)findViewById(R.id.button3);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2=new Intent(MainActivity2.this,Main2Activity.class);
                startActivity(int2);
            }
        });

    }

    public void select()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("id", id));

                    try
                    {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.1.102/select1.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                    } catch(Exception e)
                    {
                        Log.e("Fail 1", e.toString());

                    }

                    try
                    {
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(is,"iso-8859-1"),8);
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("pass 2", "connection success ");
                    }
                    catch(Exception e)
                    {
                        Log.e("Fail 2", e.toString());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                result = result.replaceFirst("<br", "");
                                int jsonStart = result.indexOf("{");
                                int jsonEnd = result.lastIndexOf("}");
                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    result = result.substring(jsonStart, jsonEnd + 1);
                                }
                                JSONObject response = new JSONObject(result);
                                JSONArray values = response.getJSONArray("cart");
                                spinnerArray =  new ArrayList<String>();
                                for (int i = 0; i < values.length(); i++) {

                                    JSONObject json_data = values.getJSONObject(i);


                                    TextView txt1 = (TextView) findViewById(R.id.textView2);
                                    name=(json_data.getString("pname"));
                                    txt1.setText(name);

                                    TextView txt2 = (TextView) findViewById(R.id.textView12);
                                    txt2.setText(json_data.getString("desc"));
                                    String size=Integer.toString(json_data.getInt("psize"));

                                    spinnerArray.add(size);


                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_spinner_item, spinnerArray);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                final Spinner sItems = (Spinner) findViewById(R.id.spinner);
                                sItems.setAdapter(adapter);
                                sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                               int position, long id) {
                                        // TODO Auto-generated method stub
                                        int pos1 = position;
                                        str1 = sItems.getSelectedItem().toString();

                                        int selected=Integer.parseInt(str1);
                                        populate(selected);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                });

                            }


                            catch(Exception e)
                            {
                                Log.e("Fail 3", e.toString());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
    }//select close
public void populate(final int qty){
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("size",Integer.toString(qty)));

                try
                {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://192.168.1.102/select.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                } catch(Exception e)
                {
                    Log.e("Fail 1", e.toString());

                }

                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("pass 2", "connection success ");
                }
                catch(Exception e)
                {
                    Log.e("Fail 2", e.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {

                            result = result.replaceFirst("<br", "");
                            int jsonStart = result.indexOf("{");
                            int jsonEnd = result.lastIndexOf("}");
                            if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                result = result.substring(jsonStart, jsonEnd + 1);
                            }
                            JSONObject json_data = new JSONObject(result);
                            TextView txt3=(TextView)findViewById(R.id.textView4);
                            price=(json_data.getString("price"));
                            txt3.setText(price);


                            TextView txt4=(TextView)findViewById(R.id.textView10);
                            avail=(json_data.getString("quant"));
                            txt4.setText(avail);

                        }
                        catch(Exception e)
                        {
                            Log.e("Fail 3", e.toString());
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });

    thread.start();
}//populate close

    public void insert()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
                    int cost=Integer.parseInt(price)*Integer.parseInt(quantity);
                    price=Integer.toString(cost);
                    nameValuePairs1.add(new BasicNameValuePair("cid", custno));
                    Log.e("printcuston",custno);
                    nameValuePairs1.add(new BasicNameValuePair("name", name));
                    nameValuePairs1.add(new BasicNameValuePair("price",price));
                    nameValuePairs1.add(new BasicNameValuePair("qty", quantity));
                    nameValuePairs1.add(new BasicNameValuePair("id", id));
                    nameValuePairs1.add(new BasicNameValuePair("size", str1));
                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.1.102/insert1.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 1", e.toString());
                        Toast.makeText(getApplicationContext(), "Invalid IP Address",
                                Toast.LENGTH_LONG).show();
                    }

                    try {
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("pass 2", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 2", e.toString());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                result = result.replaceFirst("<br", "");
                                int jsonStart = result.indexOf("{");
                                int jsonEnd = result.lastIndexOf("}");
                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    result = result.substring(jsonStart, jsonEnd + 1);
                                }
                                JSONObject json_data = new JSONObject(result);
                                code = (json_data.getInt("code"));

                                if (code == 1) {
                                    Toast.makeText(getBaseContext(), "Inserted Successfully",
                                            Toast.LENGTH_SHORT).show();
                                    TextView txt4=(TextView)findViewById(R.id.textView10);
                                    avail=(json_data.getString("avail"));
                                    txt4.setText(avail);

                                } else {
                                    Toast.makeText(getBaseContext(), "Sorry, Try Again",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Log.e("Fail 3", e.toString());
                            }
                        }
                    });//run ui end

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });//run end

        thread.start();

    }//insert close
    }




