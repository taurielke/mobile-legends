package com.example.myapplication;



import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.data.Client;
import com.example.myapplication.data.ClientData;
import com.google.android.material.textfield.TextInputEditText;


public class UpdateFragment extends Fragment {

    private Client client;
    private ClientData clientData;

    public UpdateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        clientData = new ClientData(requireContext());

        // Инфлируем разметку и получаем корневую View
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);

        Button btnSubmit = rootView.findViewById(R.id.btnSubmit);

        TextInputEditText textInputName = rootView.findViewById(R.id.textInputName);
        TextInputEditText textInputTour = rootView.findViewById(R.id.textInputTour);

        if (client != null) {
            textInputName.setText(client.getName());
            textInputTour.setText(client.getTour());
        }

        btnSubmit.setOnClickListener(v -> {
            /*if (TextUtils.isEmpty(textInputName.getText()) || TextUtils.isEmpty(textInputSemester.getText()) || TextUtils.isEmpty(textInputTeach.getText())) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }*/
            String name = textInputName.getText().toString();
            String tour = textInputTour.getText().toString();
            int isClient = 1;

            if (client != null) {
                clientData.updateClient(client.getId(), name, tour, isClient);
            } else {
                clientData.addClient(name, tour, isClient);
            }
            requireActivity().onBackPressed();
            setClient(null);
        });

        return rootView;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}