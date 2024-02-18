package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;

public class searchFragment extends Fragment {
   WebView webView;
    public searchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      try  {
            MediaController mediaController = new MediaController(getContext());
            webView = view.findViewById(R.id.webView);
          WebSettings webSettings = webView.getSettings();
          webSettings.setJavaScriptEnabled(true);
          webSettings.setLoadWithOverviewMode(true);
          webSettings.setUseWideViewPort(true);
          // Set a WebChromeClient to enable video playback
          webView.setWebChromeClient(new WebChromeClient());
          // Load the YouTube video
          String videoUrl = "https://www.youtube.com/embed/IxhIa3eZxz8";
          String html = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
          webView.loadData(html, "text/html", "utf-8");
        }catch (Exception e) {
          e.printStackTrace();
      }
    }
}