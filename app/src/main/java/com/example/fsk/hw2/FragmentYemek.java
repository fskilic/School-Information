package com.example.fsk.hw2;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fsk.hw2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;

public class FragmentYemek extends Fragment {
    private String TAG = "MainAct1";
    private String cont = "";
    TextView textView ;

            Activity act;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);

        act = getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final StringBuilder builder = new StringBuilder();
                    Document doc = Jsoup.connect("http://www.ybu.edu.tr/sks/").get();

                    Element table = doc.select("table").get(0); //select the first found table
                    Elements rows = table.select("tr");

                    for (int i = 2; i < rows.size(); i++) //first and second rows are not important
                    {
                        Element row = rows.get(i);
                        Elements cols = row.select("td");

                        builder.append(cols.get(0).text()).append("\n\n");

                    }


                    cont = builder.toString();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSoup error", e);
                }
                act.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {

                        textView.setText(cont);
                        Log.i(TAG, textView.getText().toString());
                    }
                });


            }
        }).start();

        return rootView;
    }

}