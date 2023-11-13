package com.example.assignmentwebbrowser;



import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    List<WebPageFragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        //viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);


        addTab("https://www.google.com/");


        Button btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoButtonClick(view);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fabNewTab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTab("https://www.google.com/");
            }
        });


        // Handle "Enter" key press in EditText
        EditText editTextUrl = findViewById(R.id.editTextUrl);
        editTextUrl.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                onGoButtonClick(textView);
                return true;
            }
            return false;
        });

    }
    private void addTab(String url) {

            WebPageFragment fragment = WebPageFragment.newInstance(url);
            fragments.add(fragment);

            viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
            tabLayout.setupWithViewPager(viewPager);


            // Set the tab name based on the URL
            int lastIndex = fragments.size() - 1;
            TabLayout.Tab lastTab = tabLayout.getTabAt(lastIndex);
            if (lastTab != null) {
                lastTab.setText(fragment.getHostName());
            }

            viewPager.setCurrentItem(lastIndex);


        // Clear the EditText in the URL bar
        EditText editTextUrl = findViewById(R.id.editTextUrl);
        editTextUrl.setText("");
    }
    public void onGoButtonClick(View view) {
        EditText editTextUrl = findViewById(R.id.editTextUrl);
        String url = editTextUrl.getText().toString().trim();

        if (!url.isEmpty()) {
            // Load the entered URL in the current tab
            WebPageFragment currentFragment = fragments.get(viewPager.getCurrentItem());
            Log.d("MainActivity", "Loading URL: " + url);
            currentFragment.loadUrl(url);
        }
        TabLayout.Tab currentTab = tabLayout.getTabAt(viewPager.getCurrentItem());
        if (currentTab != null) {
            WebPageFragment currentFragment = fragments.get(viewPager.getCurrentItem());
            currentTab.setText(currentFragment.getHostName());
        }

        // Load the entered URL in the current tab
       // WebPageFragment currentFragment = fragments.get(viewPager.getCurrentItem());
      //  currentFragment.loadUrl(url);
    }
}


