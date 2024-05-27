package com.example.myapplication;

import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Client;
import com.example.myapplication.data.ClientData;

import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private ClientData clientData;
    private TableLayout tableLayout;
    private List<Client> clientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);


        clientData = new ClientData(this);

        Button btnReport = findViewById(R.id.btnCreateReport);
        Button btnBack = findViewById(R.id.btnBack);
        tableLayout = findViewById(R.id.table);

        clientList = clientData.findAllClient();

        btnReport.setOnClickListener(v->{
            tableLayout.removeAllViews();

            int isClientCheck = 0;
            int deletedCount = 0;
            int tourCount = 0;


            for (Client client : clientList) {

                if (client.getIsClient() == isClientCheck) {
                    deletedCount++;
                }

                if (!client.getTour().isEmpty()) {
                    tourCount++;
                }
            }

            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            TextView first = new TextView(this);
            first.setText("deleted clients: " + deletedCount + ", ");

            TextView second = new TextView(this);
            second.setText("given tours: " + tourCount);

            row.addView(first);
            row.addView(second);

            tableLayout.addView(row);
        });

        btnBack.setOnClickListener(v->{
            finish();
        });

    }
}