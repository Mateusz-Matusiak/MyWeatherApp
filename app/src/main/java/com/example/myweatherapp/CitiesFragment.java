package com.example.myweatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitiesFragment extends Fragment {



    public interface IChangeCity{
        void onChangeCity(String cityname);
    }

    private ArrayList<SingleCity> cities = new ArrayList<>();
    private EditText enteredCity;
    private Button addButton;
    private ListView citiesList;
    private IChangeCity iChangeCity;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Map<String, String> favouriteCities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cities_layout, container, false);

        citiesList = view.findViewById(R.id.citiesList);
        addButton = view.findViewById(R.id.addCity);
        enteredCity = view.findViewById(R.id.enterCity);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mEditor = mPreferences.edit();
        favouriteCities = (Map<String, String>) mPreferences.getAll();
        mEditor.clear();

        for(String city : favouriteCities.values()){
            this.cities.add(new SingleCity(city));
        }

        CitiesListAdapter listAdapter = new CitiesListAdapter(getContext(), cities);
        citiesList.setAdapter(listAdapter);

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iChangeCity.onChangeCity(cities.get(position).cityName);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleCity cityToAdd = new SingleCity(enteredCity.getText().toString());
                cities.add(cityToAdd);
                mEditor.putString(cityToAdd.toString(), cityToAdd.cityName);
                mEditor.commit();
                enteredCity.setText("");
                listAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CitiesFragment.IChangeCity){
            iChangeCity = (CitiesFragment.IChangeCity) context;
        } else{
            throw new RuntimeException(context.toString() +
                    " must implement RefreshListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iChangeCity = null;
    }

}
