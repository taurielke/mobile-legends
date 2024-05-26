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

import com.example.myapplication.data.Discipline;
import com.example.myapplication.data.DisciplineData;
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

    DisciplineData disciplineData;
    ArrayAdapter<Discipline> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disciplineData = new DisciplineData(this);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpd = findViewById(R.id.btnUpd);
        Button btnReport = findViewById(R.id.btnReport);
        Button btnExport = findViewById(R.id.btnExport);
        Button btnImport = findViewById(R.id.btnImport);
        Button btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(v->{
            adapter.notifyDataSetChanged();
        });

        ListView listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<Discipline>(this, android.R.layout.simple_list_item_1,
                disciplineData.findAllDiscipline());
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            showEditFragment(null);
            adapter.notifyDataSetChanged();
        });

        btnUpd.setOnClickListener(v -> {
            int discipline = -1;
            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            for (int i = 0; i < listView.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    discipline = adapter.getItem(i).getId();
                    showEditFragment(adapter.getItem(i));
                }
            }
            if (discipline == -1){
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

    private void showEditFragment(Discipline discipline){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpdateFragment updateFragment = new UpdateFragment();

        if (discipline!=null) {
            updateFragment.setDiscipline(discipline);
        }

        fragmentTransaction.replace(R.id.fragmentContainer, updateFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        adapter.notifyDataSetChanged();
    }

    private void exportDataToJson() {
        List<Discipline> disciplineList = disciplineData.findAllDiscipline();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(disciplineList);
        try {
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "discipline_data.json"));
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
            FileInputStream fis = new FileInputStream(new File(getFilesDir(), "discipline_data.json"));
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
            Type listType = new TypeToken<List<Discipline>>() {}.getType();
            List<Discipline> disciplineList = gson.fromJson(jsonString, listType);
            disciplineData.deleteAll();
            for (Discipline discipline : disciplineList) {
                disciplineData.addDiscipline(discipline.getName(), discipline.getSemester(), discipline.getTeacherId());

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Data imported successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to import data", Toast.LENGTH_SHORT).show();
        }
    }
}