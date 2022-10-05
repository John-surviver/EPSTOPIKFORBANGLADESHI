package com.devghost.epstopikforbangladeshi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeFrag extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout grammar,dialogs,topik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        assignIds();

        grammar.setOnClickListener(this);
        dialogs.setOnClickListener(this);
        topik.setOnClickListener(this);

        return view;
    }

    private void assignIds() {
        grammar=view.findViewById(R.id.grammer_lay);
        dialogs=view.findViewById(R.id.dialog_lay);
        topik=view.findViewById(R.id.test_lay);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.grammer_lay){

            FragmentManager fragment = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragment.beginTransaction();
            fragmentTransaction.replace(R.id.mainFrag,new GrammarFrag());
            fragmentTransaction.commit();
        }
        else if (v.getId()==R.id.dialog_lay){
            FragmentManager fragment = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragment.beginTransaction();
            fragmentTransaction.replace(R.id.mainFrag,new DialogFrag());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (v.getId()==R.id.test_lay){
            LoadWebFrag.loadWeb="https://bdepstopik.blogspot.com/2022/09/eps-test.html";
            FragmentManager fragment = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragment.beginTransaction();
            fragmentTransaction.replace(R.id.mainFrag,new LoadWebFrag());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}