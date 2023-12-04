package com.example.doancs2nhom7.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.example.doancs2nhom7.MainActivity;
import com.example.doancs2nhom7.adapter.CategoryAdapter;
import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.model.CategoryModel;
import com.example.doancs2nhom7.query.DbQuery;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.

 */
public class CategoryFragment extends Fragment {

    private GridView catView;



    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

       // Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Categories");

        catView = view.findViewById(R.id.cat_Grid);

        CategoryAdapter adapter = new CategoryAdapter(DbQuery.g_catList);
        catView.setAdapter(adapter);

        return  view;
    }


}