package com.example.kirill.lab10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id;
    EditText region;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.idField);
        region = (EditText) findViewById(R.id.regionField);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void selectFromRemote(View view) {
        Thread thread = new Thread(new DbConnector("select", ""));
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.d("Lab10_Thread", e.getMessage());
        }
        for (String temp: DbConnector.getResult()) {
            textView.setText(textView.getText() + temp + "\n");
        }
    }

    public void updateFromRemote(View view) {
        Thread thread = new Thread(
                new DbConnector("update", "update dbo.Area set Область=\""
                        + region.getText() + "\" where КодОбласти=" + id.getText() + ";")
        );
        thread.start();
        Toast.makeText(getApplicationContext(), "UPDATE OK", Toast.LENGTH_SHORT).show();
    }

    public void insertFromRemote(View view) {
        Thread thread = new Thread(
                new DbConnector("insert", "insert into dbo.Area values(100,"
                        + region.getText() + ");")
        );
        thread.start();
        Toast.makeText(getApplicationContext(), "INSERT OK", Toast.LENGTH_SHORT).show();
    }

    public void deleteFromRemote(View view) {
        Thread thread = new Thread(
                new DbConnector("delete", "delete from dbo.Area where КодОбласти=100")
        );
        thread.start();
        Toast.makeText(getApplicationContext(), "DELETE OK", Toast.LENGTH_SHORT).show();
    }
}
