package com.devghost.epstopikforbangladeshi;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class OnlyPicFrag2 extends Fragment {
    ProgressBar progressBar;
    GridView gridView;
    ListAdapter listAdapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap <String,String> hashMap;
    View view;
    TextToSpeech textToSpeech;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_only_pic_frag2, container, false);


        assignIds();

        textToSpeech = new TextToSpeech(requireContext(), i -> {

        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://sampledev007.000webhostapp.com/apps/vocabulary2.json";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            progressBar.setVisibility(View.GONE);
            try {

                for(int x= 0 ; x<response.length(); x++){
                    JSONObject jsonObject = response.getJSONObject(x);
                    String words = jsonObject.getString("t");
                    String link = jsonObject.getString("l");


                    hashMap = new HashMap<>();
                    hashMap.put("title",words);
                    hashMap.put("link",link);
                    arrayList.add(hashMap);
                }
                listAdapter = new ListAdapter();
                gridView.setAdapter(listAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> progressBar.setVisibility(View.GONE));

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);





        return view;
    }

    private void assignIds() {
        progressBar=view.findViewById(R.id.progress_pics_only2);
        gridView =view.findViewById(R.id.pics_only_grid2);
        progressBar=view.findViewById(R.id.progress_pics_only2);
        progressBar.setVisibility(View.VISIBLE);
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
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.design_lay,viewGroup,false);

            ImageView pic = myView.findViewById(R.id.pic_id);
            TextView Words = myView.findViewById(R.id.tv_text);
            TextView  number = myView.findViewById(R.id.tv_number);
            LinearLayout linearLayout = myView.findViewById(R.id.secondLay);



            HashMap<String,String> hashMap= arrayList.get(position) ;
            String words = hashMap.get("title");
            String link = hashMap.get("link");


            //  Words.setText(words);
            number.setText(MessageFormat.format("{0}", position));
            // Words.setText(words);


            Picasso.get()
                    .load(link)
                    //use a pic here for user to see if no internet connection
                    .placeholder(R.drawable.image_loading)
                    // use a pic incase if any error loading
                    .error(R.drawable.image_loading)

                    // now finally set to your imageview
                    .into(pic);


            linearLayout.setOnClickListener(view1 -> {

                textToSpeech.speak(words,TextToSpeech.QUEUE_FLUSH,null,null);
                Toast.makeText(getActivity(), words, Toast.LENGTH_SHORT).show();



            });

            return myView;
        }
    }
}