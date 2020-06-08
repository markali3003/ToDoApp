package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Model[] taskarray;
    ListView listView;
    EditText editText ;
    JSONArray jsonArray = null;
    JSONObject jsonObject = null ;
    ArrayList<String> tasksdata ;
    ArrayAdapter<String> adpter ;
    String stg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_listView);
        editText = findViewById(R.id.id_editText);
       GetAllTask getAllTask = new GetAllTask();
        getAllTask.execute();
    }

public void addTask(View view){
        InsertTask insertTask = new InsertTask();
         stg = editText.getText().toString() ;
        insertTask.execute(stg) ;

}
    public class InsertTask extends  AsyncTask<String , Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://10.0.2.2/newProject/inserttask.php?task="+strings[0].replace(" ","%20")) ;
                URLConnection urlConnection = url.openConnection() ;
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream()) ;
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne ;
                while((ligne = bufferedReader.readLine()) != null){
                    jsonObject = new JSONObject(ligne) ;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                boolean insertIs = jsonObject.getBoolean("result") ;
                tasksdata.add(stg);
                adpter.notifyDataSetChanged();
                if(insertIs){
                    Toast.makeText(MainActivity.this,"task added with success",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class GetAllTask extends AsyncTask<String, Integer, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public String doInBackground(String... strings) {

            try {
                URL url = new URL("http://10.0.2.2/newProject/getalltask.php");
                URLConnection urlConnection = url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne;
                while ((ligne = bufferedReader.readLine()) != null) {
                    jsonArray = new JSONArray(ligne);
                }
                Log.d("result", jsonArray.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            taskarray = gson.fromJson(jsonArray.toString(), Model[].class);
             tasksdata = new ArrayList<>();

            for (Model model : taskarray) {
                tasksdata.add(model.getTask());
            }
                  adpter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1 ,tasksdata) ;
                 listView.setAdapter(adpter);

        }

    }
}