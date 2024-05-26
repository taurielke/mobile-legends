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
import com.google.android.material.textfield.TextInputEditText;


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

        TextInputEditText textInputName = rootView.findViewById(R.id.textInputName);
        TextInputEditText textInputSemester = rootView.findViewById(R.id.textInputSemester);
        TextInputEditText textInputTeach = rootView.findViewById(R.id.textInputTeacherId);

        if (discipline != null) {
            textInputName.setText(discipline.getName());
            textInputSemester.setText(String.valueOf(discipline.getSemester()));
            textInputTeach.setText(String.valueOf(discipline.getTeacherId()));
        }

        btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(textInputName.getText()) || TextUtils.isEmpty(textInputSemester.getText()) || TextUtils.isEmpty(textInputTeach.getText())) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }
            String name = textInputName.getText().toString();
            int semester = Integer.parseInt(textInputSemester.getText().toString());
            int teachId = Integer.parseInt(textInputTeach.getText().toString());
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

}