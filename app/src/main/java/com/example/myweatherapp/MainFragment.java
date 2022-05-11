package com.example.myweatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private Button refreshButton;
    private IRefreshListener IRefreshListener;

    public interface IRefreshListener {
        void onRefreshClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        refreshButton = view.findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IRefreshListener.onRefreshClicked();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IRefreshListener){
            IRefreshListener = (IRefreshListener) context;
        } else{
            throw new RuntimeException(context.toString() +
                    " must implement RefreshListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        IRefreshListener = null;
    }
}
