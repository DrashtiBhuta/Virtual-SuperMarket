package net.sourceforge.zbar.android.CameraTest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main2Activity extends Activity {
    MainActivity2 m1=new MainActivity2();
    GlobalClass g=GlobalClass.getInstance();
    String custno=g.getData();
    InputStream is=null;
    String result=null;
    String line=null;
    int code,total,sizeval,count;
    int bid=1;
    String updateval,quantity;
    TableLayout tl;
    TextView txt4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        display();
    }
    public void display()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("cid", custno));

                    try
                    {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.1.102/cart1.php");
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
                            try
                            {

                                result = result.replaceFirst("<br", "");
                                int jsonStart = result.indexOf("{");
                                int jsonEnd = result.lastIndexOf("}");
                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    result = result.substring(jsonStart, jsonEnd + 1);
                                }
                                JSONObject response = new JSONObject(result);
                                JSONArray values=response.getJSONArray("cart");
                                final int size[]=new int[values.length()+1];
                                Log.e("pass 3", String.valueOf(code));
                                for(int i=0;i<values.length();i++)
                                {

                                    final JSONObject json_data=values.getJSONObject(i);   ;
                                    tl = (TableLayout) findViewById(R.id.tableLayout1);
                                    count=i;
/*                                  Create a new row to be added. */
                                    TableRow tr = new TableRow(Main2Activity.this);
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                    tr.setBackgroundColor(Color.WHITE);

                                    final TextView txt1 = new TextView(Main2Activity.this);
                                    txt1.setText(json_data.getString("pid"));
                                    txt1.setId(bid);
                                    txt1.setTextColor(Color.RED);
                                    tr.addView(txt1);

                                    TextView txt2 = new TextView(Main2Activity.this);
                                    txt2.setText(json_data.getString("pname"));
                                    txt2.setTextColor(Color.RED);
                                    tr.addView(txt2);

                                    final TextView txt3 = new TextView(Main2Activity.this);
                                    total=total+json_data.getInt("prodprice");
                                    txt3.setText(String.valueOf(json_data.getInt("prodprice")));
                                    txt3.setTextColor(Color.RED);
                                    tr.addView(txt3);

                                    size[i]=json_data.getInt("prodsize");
                                    Log.e("increate-size", Integer.toString(size[i]));
                                    final Button bqty=new Button(Main2Activity.this);
                                    String temp=Integer.toString(json_data.getInt("prodqty"));
                                    bqty.setText(temp);
                                    bqty.setTextColor(Color.RED);
                                    bqty.setBackgroundColor(Color.WHITE);
                                    bqty.setLayoutParams(new TableRow.LayoutParams(30, TableRow.LayoutParams.WRAP_CONTENT));
                                    bqty.setId(bid);
                                    bqty.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.e("inupdate", (String) txt1.getText());
                                            updateval=(String)txt1.getText();
                                            sizeval=size[count];
                                            Log.e("inupdate-size", Integer.toString(sizeval));
                                            initiatePopupWindow();

                                        }
                                    });
                                    tr.addView(bqty);

                                   // tr.addView(txt1)
                                    final Button bdel=new Button(Main2Activity.this);
                                    bdel.setText("X");
                                    bdel.setTextColor(Color.RED);
                                    bdel.setBackgroundColor(Color.WHITE);

                                    bdel.setId(bid);
                                    bdel.setLayoutParams(new TableRow.LayoutParams(20, TableRow.LayoutParams.WRAP_CONTENT));

                                    bdel.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                                Log.e("indelete", Integer.toString(bdel.getId()));
                                                Log.e("indeletetxt", (String) txt1.getText());
                                                Log.e("indeletetxt-price", (String) txt3.getText());
                                                String sendid = (String) txt1.getText();
                                                String sendpr = (String) txt3.getText();
                                            try {
                                                String sendsize = Integer.toString(json_data.getInt("prodsize"));
                                                String sendqty=Integer.toString(json_data.getInt("prodqty"));
                                                String sendorderedqty=bqty.getText().toString();
                                                Log.e("indelete-size",sendsize);
                                                Log.e("indelete-order",sendorderedqty);
                                                delete(sendid, sendpr, sendsize,sendorderedqty,v);
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }

                                            }
                                    });
                                    tr.addView(bdel);

                                    /*Add row to TableLayout. */
                                    //tr.setBackgroundResource(R.drawable.sf_gradient_03);

                                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                    bid=bid+1;
                                }
                                tl = (TableLayout) findViewById(R.id.tableLayout1);

/*                                  Create a new row to be added. */
                                TableRow tr = new TableRow(Main2Activity.this);
                                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                tr.setBackgroundColor(Color.WHITE);

                                final TextView txt1 = new TextView(Main2Activity.this);
                                txt1.setText("total");
                                txt1.setTextColor(Color.RED);
                                tr.addView(txt1);

                                txt4 = new TextView(Main2Activity.this);
                                txt4.setText(Integer.toString(total));
                                txt4.setTextColor(Color.RED);
                                tr.addView(txt4);
                                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

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


    }//create closed
    public void delete(String id,String pr, final String size,final String ordered,final View v1)
    {
        final String pid=id;
        final int price=Integer.parseInt(pr);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();

                    nameValuePairs1.add(new BasicNameValuePair("cid", custno));
                    nameValuePairs1.add(new BasicNameValuePair("size", size));

                    nameValuePairs1.add(new BasicNameValuePair("id", pid));
                    nameValuePairs1.add(new BasicNameValuePair("order",ordered));
                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.1.102/delete1.php");
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

                                if (code == 1)
                                {
                                    Toast.makeText(getBaseContext(), "deleted Successfully",
                                            Toast.LENGTH_SHORT).show();
                                    total=total-price;
                                    txt4.setText(Integer.toString(total));
                                    String order1 = (json_data.getString("order"));
                                    Log.e("orderupdate",order1);

                                    final TableRow parent = (TableRow) v1.getParent();
                                    tl.removeView(parent);

                                }
                                else {
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

    }//delete close

    private PopupWindow pwindo;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Main2Activity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button update = (Button) layout.findViewById(R.id.btn_close_popup);
            update.setOnClickListener(update_button_click_listener);
            Button btnClosePopup = (Button) layout.findViewById(R.id.button4);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };
    private View.OnClickListener update_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {


            Log.e("in popup","hello");

        }
    };
}











