package com.example.doancs2nhom7.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.doancs2nhom7.R;
import com.example.doancs2nhom7.databinding.FragmentHomeBinding;
import com.example.doancs2nhom7.fragment.AccountFragment;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FrameLayout main_frame;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //main_frame = root.findViewById(R.id.main_frame);
        //setFragement(new AccountFragment());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void setFragement(Fragment fragment) {
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(main_frame.getId(),fragment);
//        transaction.commit();
//
//    }
}