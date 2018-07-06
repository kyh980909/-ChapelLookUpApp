package org.techtown.firstproject;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    EditText inputID;
    EditText inputPW;
    TextView result;
    Button LoginBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        inputID = (EditText) findViewById(R.id.inputID);
        inputPW = (EditText) findViewById(R.id.inputPW);
        result = (TextView) findViewById(R.id.result);
        LoginBt = (Button) findViewById(R.id.LoginBt);


        LoginBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = inputID.getText().toString().trim();
                String pw = inputPW.getText().toString().trim();

                ConnectThread thread = new ConnectThread(id, pw);
                thread.start();
            }
        });
    }

    class ConnectThread extends Thread {
        String ID;
        String PW;

        public ConnectThread(String id, String pw) {
            ID = id;
            PW = pw;
        }

        public void run() {
            try {
                int port = 9001;

                Socket sock = new Socket("210.119.129.165", port);

                ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
                outStream.writeObject(ID);
                outStream.writeObject(PW);
                outStream.flush();

                ObjectInputStream inStream = new ObjectInputStream(sock.getInputStream());
                String obj = (String) inStream.readObject();

                result.setText(obj);

                sock.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
