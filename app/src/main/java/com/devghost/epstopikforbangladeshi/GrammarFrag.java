package com.devghost.epstopikforbangladeshi;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class GrammarFrag extends Fragment {

    ProgressBar progressBar;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap <String,String> hashMap;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grammar, container, false);

        listView=view.findViewById(R.id.grammar_listview);
        progressBar=view.findViewById(R.id.grammar_progressbar);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://sampledev007.000webhostapp.com/apps/grammar.json";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            progressBar.setVisibility(View.GONE);
            try {

                for(int x= 0 ; x<response.length(); x++){
                    JSONObject jsonObject = response.getJSONObject(x);
                    String words = jsonObject.getString("t");
                    String link = jsonObject.getString("p");


                    hashMap = new HashMap<>();
                    hashMap.put("title",words);
                    hashMap.put("link",link);
                    arrayList.add(hashMap);
                }
                listAdapter = new ListAdapter();
                listView.setAdapter(listAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> progressBar.setVisibility(View.GONE));

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);


        return view;
    }

    private class ListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = (LayoutInflater) requireActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.design_for_grammar_lay,viewGroup,false);


            TextView number = myView.findViewById(R.id.grammar_num_id);
            TextView vocab = myView.findViewById(R.id.grammar_txt_id);


            HashMap<String,String> hashMap= arrayList.get(position) ;
            String title = hashMap.get("title");
            String words = hashMap.get("link");

            vocab.setText(words);
            number.setText(title);

          //  number.setText(MessageFormat.format("{0}", position));

            return myView;
        }
    }
}