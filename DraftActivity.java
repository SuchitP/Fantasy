package com.example.suchitpeddireddyfinalprojectfantasy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.suchitpeddireddyfinalprojectfantasy.MainActivity.names;
import static com.example.suchitpeddireddyfinalprojectfantasy.MainActivity.positions;

public class DraftActivity extends AppCompatActivity {

    public static ArrayList<Integer> draftedPlayersPosition = new ArrayList<>();
    public static ArrayList<String> draftedPlayers = new ArrayList<>();
    ArrayList<String> availablePlayers = new ArrayList<>();
    ArrayList<String> playersPositions = new ArrayList<>();
    int a;
    int numOfPicks;
    TextView numberofTeams;
    ListView listView;
    Button endDraftButton;
    public static final String LIST = "list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        availablePlayers = names;
        playersPositions = MainActivity.positions;

        for(int i = 0; i < 3000; i++){
            availablePlayers.add(names.get(i));
            playersPositions.add(positions.get(i));
        }

        if (savedInstanceState != null) {
            availablePlayers = (ArrayList<String>)savedInstanceState.getSerializable(LIST);
        }

        a = MainActivity.a;
        numOfPicks = a*11;
        listView = findViewById(R.id.id_listView);
        endDraftButton = findViewById(R.id.id_endDraftButton);
        endDraftButton.setVisibility(View.INVISIBLE);

        numberofTeams = findViewById(R.id.id_numberOfTeamsText);
        //numberofTeams.setText(""+availablePlayers.get(300));

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.adapter_layout, availablePlayers);
        listView.setAdapter(customAdapter);

    }

    public class CustomAdapter extends ArrayAdapter<String> {
        ArrayList<String> availablePlayers;
        Context context;
        int xmlResource;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
            availablePlayers = objects;
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

            playerName.setText("" + availablePlayers.get(position));
            playerPos.setText("" + playersPositions.get(position) + "  ");

            draftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(numOfPicks!=0) {
                        if(a%2==0) {
                            numberofTeams.setText("Team 2 Pick \nLast Pick: " + playersPositions.get(position) + "  " +availablePlayers.get(position));
                            a++;
                        }
                        else{
                            numberofTeams.setText("Team 1 Pick\n Last Pick: " + playersPositions.get(position) + "  " + availablePlayers.get(position));
                            a++;
                        }
                        draftedPlayersPosition.add(position);
                        draftedPlayers.add(availablePlayers.get(position));
                        remove(availablePlayers.get(position));
                        numOfPicks--;
                    }
                    if(numOfPicks==0) {
                        Toast t = Toast.makeText(getApplicationContext(), "Exit Draft, Teams Full", Toast.LENGTH_SHORT);
                        t.show();
                        numberofTeams.setText("Teams Full");
                    }
                }
            });

            if(numOfPicks == 0) {
                endDraftButton.setVisibility(View.VISIBLE);
                endDraftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DraftActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

            return adapterView;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST, availablePlayers);
    }
}
