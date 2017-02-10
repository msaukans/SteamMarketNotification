package tk.codester.maris.steammarketnotification;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Elements content;//variable name can be anything
    public ArrayList<String> contentList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    ListView lv;



    private ProgressDialog spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_list);

        lv = (ListView) findViewById(R.id.jlist1);

        spin = new ProgressDialog(this);
        spin.setMessage("Getting Data...");
        spin.show();
        new NewThread().execute();
        adapter = new ArrayAdapter<>(this, R.layout.jlist_item,R.id.jtextItem ,contentList);

    }//end onCreate method

    public class NewThread extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... arg){
        
            Document doc;
            try{
                doc = Jsoup.connect("https://steamcommunity.com/market/search?appid=730&q=knife#p1_price_asc").get();
                content = doc.select(".market_listing_table_header > a");

                contentList.clear();
                for (Element contents: content){
                    contentList.add(contents.text());

                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            spin.dismiss();
            return null;

        }//end doInBackground method

        protected void onPostExecute(String result){
            lv.setAdapter(adapter);
        }//end onPostExecutive method

    }//end newThread class



}//end Scrape class