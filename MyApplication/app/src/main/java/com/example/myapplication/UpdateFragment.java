package com.example.myapplication;



import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.Discipline;
import com.example.myapplication.data.DisciplineData;

import missing.namespace.R;


public class UpdateFragment extends Fragment {

    private Discipline discipline;
    private DisciplineData disciplineData;

    public UpdateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        disciplineData = new DisciplineData(requireContext());

        // Инфлируем разметку и получаем корневую View
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);

        Button btnSubmit = rootView.findViewById(R.id.btnSubmit);
        TextView textViewName = rootView.findViewById(R.id.textViewName);
        TextView textViewSem = rootView.findViewById(R.id.textView2);
        TextView textViewTeachId = rootView.findViewById(R.id.textView3);

        if (discipline != null) {
            textViewName.setText(discipline.getName());
            textViewSem.setText(discipline.getSemester());
            textViewTeachId.setText(discipline.getTeacherId());
        }

        btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(textViewName.getText()) || TextUtils.isEmpty(textViewSem.getText()) || TextUtils.isEmpty(textViewTeachId.getText())) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }
            String name = textViewName.getText().toString();
            int semester = Integer.parseInt(textViewSem.getText().toString());
            int teachId = Integer.parseInt(textViewTeachId.getText().toString());
            if (discipline != null) {
                disciplineData.updateDiscipline(discipline.getId(), name, semester, teachId);
            } else {
                disciplineData.addDiscipline(name, semester, teachId);
            }
            requireActivity().onBackPressed();
            setDiscipline(null);
        });

        return rootView;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

}