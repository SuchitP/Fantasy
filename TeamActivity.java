package com.example.suchitpeddireddyfinalprojectfantasy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    Button homeButton;
    Button playersButton;
    Button matchButton;
    Button saveButton;
    ArrayList<Integer> players = new ArrayList<>();
    ArrayList<String> playerNames1 = new ArrayList<>();
    ArrayList<String> playerNames2 = new ArrayList<>();
    ArrayList<String> temp1 = new ArrayList<>();
    ArrayList<String> temp2 = new ArrayList<>();
    public static double score1;
    public static double score2;
    int numOfTeams;
    TextView textView;

    URL url;
    InputStream inputStream;
    URLConnection urlConnection;
    String currentString;
    JSONObject jsonObject;

    public static final String LIST1 = "list1";
    public static final String LIST2 = "list2";
    ListView listView1;
    ListView listView2;
    int b = 0;
    TextView des1;
    TextView des2;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        AsyncThread thread = new AsyncThread();
        thread.execute();

        loadData();

        players = DraftActivity.draftedPlayersPosition;
        numOfTeams = MainActivity.a;

        size = DraftActivity.draftedPlayers.size();
        Log.d("stop",""+size);
        for(int i = 0; i < DraftActivity.draftedPlayers.size(); i++){
            if(i%2==0)
                playerNames1.add(DraftActivity.draftedPlayers.get(i));
            else
                playerNames2.add(DraftActivity.draftedPlayers.get(i));
            //players.remove(0);
        }

        if (savedInstanceState != null) {
            playerNames1 = (ArrayList<String>)savedInstanceState.getSerializable(LIST1);
            playerNames2 = (ArrayList<String>)savedInstanceState.getSerializable(LIST2);
        }

        homeButton = findViewById(R.id.id_homeButton);
        //playersButton = findViewById(R.id.id_playersButton);
        matchButton = findViewById(R.id.id_matchButton);
        saveButton = findViewById(R.id.id_saveButton);
        listView1 = findViewById(R.id.id_list1);
        listView2 = findViewById(R.id.id_list2);
        des1 = findViewById(R.id.id_des1);
        des2 = findViewById(R.id.id_des2);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamActivity.this, MatchActivity.class);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        CustomAdapter customAdapter1 = new CustomAdapter(this, R.layout.adapter_layout, playerNames1);
        listView1.setAdapter(customAdapter1);

        CustomAdapter customAdapter2 = new CustomAdapter(this, R.layout.adapter_layout, playerNames2);
        listView2.setAdapter(customAdapter2);

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json1 = gson.toJson(playerNames1);
        String json2 = gson.toJson(playerNames2);
        editor.putString("task list 1", json1);
        editor.putString("task list 2", json2);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json1 = sharedPreferences.getString("task list 1", null);
        String json2 = sharedPreferences.getString("task list 2", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        playerNames1 = gson.fromJson(json1, type);
        playerNames2 = gson.fromJson(json2, type);

        if(playerNames1 == null){
            playerNames1 = new ArrayList<>();
        }
        if(playerNames2 == null){
            playerNames2 = new ArrayList<>();
        }
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        //String string = "";
        @Override
        protected Void doInBackground(String... strings) {//https://www.fantasyfootballnerd.com/service/players/json/hd5vu9i376uk/
            Log.d("TAG","haha");
            try {
                url = new URL("https://www.fantasyfootballnerd.com/service/daily/json/test/fanduel/");
                urlConnection = url.openConnection();
                URLConnection urlConnection = url.openConnection();
                inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                currentString = reader.readLine();
                jsonObject = new JSONObject(currentString);
                Log.d("TAG","hello?");
                Log.d("TAG3", jsonObject.getJSONArray("players").getJSONObject(0).getJSONObject("projections").getJSONObject("conservative").get("projectedPoints").toString());

                for(int i = 0; i < size+2; i++){
                    int rand = (int)(Math.random()*400);
                    temp2.add(jsonObject.getJSONArray("players").getJSONObject(rand).getJSONObject("projections").getJSONObject("conservative").get("projectedPoints").toString());
                    rand = (int)(Math.random()*400);
                    temp1.add(jsonObject.getJSONArray("players").getJSONObject(rand).getJSONObject("projections").getJSONObject("conservative").get("projectedPoints").toString());
                }

                /*for(int i = 1; i < (size*2)+2; i+=2){
                    temp2.add(jsonObject.getJSONArray("players").getJSONObject(i).getJSONObject("projections").getJSONObject("conservative").get("projectedPoints").toString());
                }*/
                for (int i = 0; i < temp1.size(); i++){
                    Double z = Double.parseDouble(temp1.get(i));
                    Log.d("bye bye", ""+z);
                    score1 += z;
                }
                for (int i = 0; i < temp2.size(); i++){
                    Double z = Double.parseDouble(temp2.get(i));
                    score2 += z;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.d("TAG","TAG111");
            try {
                Log.d("TAG","TAG111");
                jsonObject = new JSONObject(currentString);
                String temp;

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        ArrayList<String> playerNames1;
        Context context;
        int xmlResource;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
            playerNames1 = objects;
            xmlResource = resource;
            this.context = context;
        }
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xmlResource, null);

            TextView playerName = adapterView.findViewById(R.id.id_playerName);
            TextView playerPos = adapterView.findViewById(R.id.id_playerPos);
            Button draftButton = adapterView.findViewById(R.id.id_draftButton);

            playerName.setText(" " + playerNames1.get(position));

            draftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(b%2==0)
                        des1.setText(playerNames1.get(position) + " \nProjected Points: " + (temp1.get(position)));
                    else
                        des2.setText(playerNames2.get(position) + " \nProjected Points: " + (temp2.get(position)));
                    b++;
                }
            });

            return adapterView;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST1, playerNames1);
        outState.putSerializable(LIST2, playerNames2);
    }

}
