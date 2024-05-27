package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.data.Client;
import com.example.myapplication.data.ClientData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    ClientData clientData;
    ArrayAdapter<Client> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientData = new ClientData(this);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpd = findViewById(R.id.btnUpd);
        Button btnReport = findViewById(R.id.btnReport);
        Button btnExport = findViewById(R.id.btnExport);
        Button btnImport = findViewById(R.id.btnImport);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnDel = findViewById(R.id.btnDel);



        btnRefresh.setOnClickListener(v->{
            adapter.notifyDataSetChanged();
        });

        ListView listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<Client>(this, android.R.layout.simple_list_item_1,
                clientData.findAllClient());
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            showEditFragment(null);
            adapter.notifyDataSetChanged();
        });

        btnDel.setOnClickListener(v->{
            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            for (int i = 0; i < listView.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    clientData.updateClient(adapter.getItem(i).getId(),adapter.getItem(i).getName(),
                            adapter.getItem(i).getTour(), 0);
                }
            }
            adapter.notifyDataSetChanged();
            listView.clearChoices();
        });

        btnUpd.setOnClickListener(v -> {
            int client = -1;
            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            for (int i = 0; i < listView.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    client = adapter.getItem(i).getId();
                    showEditFragment(adapter.getItem(i));
                }
            }
            if (client == -1){
                return;
            }
            adapter.notifyDataSetChanged();
            listView.clearChoices();
        });

        btnReport.setOnClickListener(v->{
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        });

        btnExport.setOnClickListener(v -> {
            exportDataToJson();
        });

        btnImport.setOnClickListener(v -> {
            importDataFromJson();
        });

    }

    private void showEditFragment(Client client){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpdateFragment updateFragment = new UpdateFragment();

        if (client!=null) {
            updateFragment.setClient(client);
        }

        fragmentTransaction.replace(R.id.fragmentContainer, updateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        adapter.notifyDataSetChanged();
    }

    private void exportDataToJson() {
        List<Client> clientList = clientData.findAllClient();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(clientList);
        try {
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "client_data.json"));
            fos.write(jsonString.getBytes());
            fos.close();
            Toast.makeText(this, "Data exported successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to export data", Toast.LENGTH_SHORT).show();
        }
    }

    private void importDataFromJson() {
        try {
            FileInputStream fis = new FileInputStream(new File(getFilesDir(), "client_data.json"));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fis.close();
            String jsonString = stringBuilder.toString();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> clientList = gson.fromJson(jsonString, listType);
            clientData.deleteAll();
            for (Client client : clientList) {
                clientData.addClient(client.getName(), client.getTour(), client.getIsClient());

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Data imported successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to import data", Toast.LENGTH_SHORT).show();
        }
    }
}