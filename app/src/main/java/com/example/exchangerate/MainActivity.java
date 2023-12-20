package com.example.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<News> aa;
    ArrayList<News> news = new ArrayList<>();
    private static final String TAG = "EARTHQUAKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView = (ListView)this.findViewById(R.id.list);
        int layoutID = android.R.layout.simple_list_item_1;
        aa = new ArrayAdapter<>(this, layoutID , news);
        listView.setAdapter(aa);
        refreshrate();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Intent intent = new Intent(MainActivity.this, Exchange_Input.class);
                intent.putExtra("cur",  news.get(position).getCur());
                intent.putExtra("rate", news.get(position).getRate());
                startActivity(intent);
            }
        });

    }
    private void refreshrate() {
        // Get the XML
        URL url;
        try {
            String apiFeed = "https://www.floatrates.com/daily/thb.xml";
            url = new URL(apiFeed);
            URLConnection connection;
            connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Parse the earthquake feed.
                Document dom = db.parse(in);
                Element docEle = dom.getDocumentElement();
                // Clear the old earthquakes
                news.clear();
                Log.i("ssss" , "sssssss") ;
                // Get a list of each earthquake entry.
                NodeList nl = docEle.getElementsByTagName("item");
                if (nl != null && nl.getLength() > 0) {
                    for (int i = 0 ; i < nl.getLength(); i++) {
                        Element entry = (Element)nl.item(i);


                        String title = entry.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
                        String details = entry.getElementsByTagName("exchangeRate").item(0).getFirstChild().getNodeValue();
                        String cur = entry.getElementsByTagName("targetCurrency").item(0).getFirstChild().getNodeValue();
                        String rate = entry.getElementsByTagName("exchangeRate").item(0).getFirstChild().getNodeValue();

                        Log.i("entry" , entry.toString()) ;
                        News quake = new News(title, details , cur , Double.parseDouble(rate));
                        addNews(quake);
                    }
                }
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException", e);
        } catch (IOException e) {
            Log.d(TAG, "IOException", e);
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Log.d(TAG, "Parser Configuration Exception", e);
        } catch (SAXException e) {
            Log.d(TAG, "SAX Exception", e);
        }
        finally {

        }
    }



    private void addNews(News _quake) {
        news.add(_quake); // Add the new quake to our list of earthquakes.
        aa.notifyDataSetChanged(); // Notify the array adapter of a change.
    }
}

