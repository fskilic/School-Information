package com.example.fsk.hw2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fsk.hw2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FragmentDuyuru extends Fragment {
    private String TAG = "MainAct1";
    private String cont = "";
    Activity act;
    TextView textView;
    ListView listView;
    ArrayList<Memory> memoriesList;
    ArrayAdapter<Memory> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_2, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);
        act = getActivity();
        listView =  rootView.findViewById(R.id.lv_duyuru_ve_haber);
        memoriesList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder builder = new StringBuilder();
                    Document doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();

                    Elements div = doc.select("div.contentAnnouncements div.caContent div.cncItem a"); //select the first found table

                    for (int i = 0; i < div.size(); i++) {
                        memoriesList.add(new Memory(div.get(i).text(), div.get(i).absUrl("href")));
                    }
                    cont = builder.toString();

                    /*Elements rows = div.select("cncItem");

                    for (int i = 2; i < rows.size(); i++) //first and second rows are not important
                    {
                        Element row = rows.get(i);
                        Elements cols = row.select("cacArrow");

                        builder.append(cols.get(0).text()).append("\n\n");

                    }

                    cont = builder.toString();*/

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSoup error", e);
                }

                act.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        arrayAdapter = new ArrayAdapter<Memory>(getContext(), android.R.layout.simple_list_item_1, memoriesList);
                        listView.setAdapter(arrayAdapter);

                    }
                });


            }
        }).start();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory duyuru = (Memory) arrayAdapter.getItem(position);

                Intent sayfa = new Intent(Intent.ACTION_VIEW);
                sayfa.setData(Uri.parse(duyuru.link));
                startActivity(sayfa);
            }
        });

        return rootView;
    }
}