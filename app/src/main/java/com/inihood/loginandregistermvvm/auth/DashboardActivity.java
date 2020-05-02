package com.inihood.loginandregistermvvm.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inihood.loginandregistermvvm.AddNoteActivity;
import com.inihood.loginandregistermvvm.R;
import com.inihood.loginandregistermvvm.notedb.NoteDatabase;
import com.inihood.loginandregistermvvm.notedb.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Note note;
    private List<Note> notes;
    private int pos;

    public static void start(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        context.startActivity(intent);
    }

    private RelativeLayout logout_container;
    private  SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initToolbar();

        logout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        logout_container = findViewById(R.id.logout_c);
        setSupportActionBar(toolbar);
        setSystemBarColor(this, R.color.colorPrimary);

        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");

        if (username != null){
            toolbar.setSubtitle("@" + username);
        }
        if (name != null) {
            Toast.makeText(this, "Welcome " + name, Toast.LENGTH_LONG).show();
            toolbar.setTitle("Good day, " + name);
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

}
