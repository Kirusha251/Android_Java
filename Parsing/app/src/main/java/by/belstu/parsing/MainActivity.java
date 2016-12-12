package by.belstu.parsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public Elements content;
    public ArrayList<News> newsList = new ArrayList<News>();
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView lv;
    public ProgressDialog progressDialog;

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            progressDialog = new ProgressDialog(MainActivity.this);
            this.progressDialog.setMessage("Connecting...");
            this.progressDialog.show();
            ConnectivityManager conMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conMngr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                lv = (ListView) findViewById(R.id.listView);
                new NewThread().execute();
                adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.pro_item, titleList);
            } else {
                Toast.makeText(this, "Internet access denied!", Toast.LENGTH_SHORT).show();
                String _settingsActivityAction = Settings.ACTION_WIRELESS_SETTINGS;
                if (_settingsActivityAction != null)
                    startActivity(new Intent(_settingsActivityAction));
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class NewThread extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            Document doc;
            try{
                doc = Jsoup.connect("https://news.yandex.by/?lang=ru").get();
                content = doc.select(".story__content");



                String link = doc.select("a[href]").first().attr("href");


                news = new News(content.toString(), Uri.parse(link));
                titleList.clear();

                for (org.jsoup.nodes.Element contents: content) {
                    titleList.add(contents.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String result){
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, news.getUrl());
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
