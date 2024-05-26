package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.data.Discipline;
import com.example.myapplication.data.DisciplineData;

import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private DisciplineData disciplineData;
    private TableLayout tableLayout;
    private List<Discipline> disciplineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);


        disciplineData = new DisciplineData(this);/////////////////////

        Button btnReport = findViewById(R.id.btnCreateReport);
        Button btnBack = findViewById(R.id.btnBack);
        tableLayout = findViewById(R.id.table);
        tableLayout.removeAllViews();

        disciplineList = disciplineData.findAllDiscipline();////////////////////

        btnReport.setOnClickListener(v->{
            tableLayout.removeAllViews();


            TableRow headerRow = new TableRow(this);
            TableRow.LayoutParams headerLayoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            headerRow.setLayoutParams(headerLayoutParams);

            TextView nameHeader = new TextView(this);
            nameHeader.setText("Name");

            TextView semesterHeader = new TextView(this);
            semesterHeader.setText("Semester");

            TextView teacherIdHeader = new TextView(this);
            teacherIdHeader.setText("Teacher ID");

            // Добавляем заголовки в headerRow
            headerRow.addView(nameHeader);
            headerRow.addView(semesterHeader);
            headerRow.addView(teacherIdHeader);

            // Добавляем headerRow в таблицу
            tableLayout.addView(headerRow);


            int targetSemester = 2;//////////////////


            for (Discipline discipline : disciplineList) {


                if (discipline.getSemester() == targetSemester) {///////////////////////


                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(layoutParams);

                    TextView nameTextView = new TextView(this);
                    nameTextView.setText(discipline.getName());

                    TextView semesterTextView = new TextView(this);
                    semesterTextView.setText(String.valueOf(discipline.getSemester()));

                    TextView teacherIdTextView = new TextView(this);
                    teacherIdTextView.setText(String.valueOf(discipline.getTeacherId()));

                    row.addView(nameTextView);
                    row.addView(semesterTextView);
                    row.addView(teacherIdTextView);

                    tableLayout.addView(row);
                }
            }
        });

        btnBack.setOnClickListener(v->{
            finish();
        });

    }
}