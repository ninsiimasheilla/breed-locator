package com.example.richardsenyange.breedlocator.Fragaments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.richardsenyange.breedlocator.R;


public class Breed_search extends Fragment {
    private View mMainView;
    public Breed_search() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_breed_search, container, false);



        // Inflate the layout for this fragment
        return mMainView;

    }
}
