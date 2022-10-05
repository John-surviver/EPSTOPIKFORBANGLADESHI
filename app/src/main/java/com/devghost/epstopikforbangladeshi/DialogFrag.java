package com.devghost.epstopikforbangladeshi;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DialogFrag extends Fragment {

    ProgressBar progressBar;
    ListView listView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap <String,String> hashMap;
    View view;
    ListAdapter listAdapter;
    MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog, container, false);


        progressBar=view.findViewById(R.id.progress_dialog_lay);
        listView =view.findViewById(R.id.dialog_list);
        progressBar.setVisibility(View.VISIBLE);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://sampledev007.000webhostapp.com/apps/dialog.json";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            progressBar.setVisibility(View.GONE);
            try {

                for(int x= 0 ; x<response.length(); x++){
                    JSONObject jsonObject = response.getJSONObject(x);
                    String num = jsonObject.getString("t");
                    //pic link
                    String p_link = jsonObject.getString("p");
                    //sound link
                    String s_link = jsonObject.getString("s");


                    hashMap = new HashMap<>();
                   // hashMap.put("num",num);
                    hashMap.put("p_link",p_link);
                    hashMap.put("s_link",s_link);
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
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.design_for_dialog_lay,viewGroup,false);

            ImageView pic = myView.findViewById(R.id.dialog_pic);
            LinearLayout playbtn = myView.findViewById(R.id.play_dialog_btn);
            LinearLayout linearLayout = myView.findViewById(R.id.dialog_lay_design);



            HashMap<String,String> hashMap= arrayList.get(position) ;
            //String num = hashMap.get("title");
            String p_link = hashMap.get("p_link");
            String s_link = hashMap.get("s_link");


            //  Words.setText(words);
           // number.setText(MessageFormat.format("{0}", position));

            Picasso.get()
                    .load(p_link)
                    //use a pic here for user to see if no internet connection
                    .placeholder(R.drawable.image_loading)
                    // use a pic incase if any error loading
                    .error(R.drawable.image_loading)

                    // now finally set to your imageview
                    .into(pic);


            linearLayout.setOnClickListener(view1 -> {

                Toast.makeText(getActivity(), "Loading Wait Please", Toast.LENGTH_SHORT).show();
                //change play button icon
                playbtn.setVisibility(View.INVISIBLE);

                //dialogs audio
                if(mediaPlayer!=null) mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(s_link);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }





            });

            return myView;
        }
    }
}