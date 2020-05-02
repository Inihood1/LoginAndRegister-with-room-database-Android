package com.inihood.loginandregistermvvm.auth.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inihood.loginandregistermvvm.AddNoteActivity;
import com.inihood.loginandregistermvvm.NoteListActivity;
import com.inihood.loginandregistermvvm.R;
import com.inihood.loginandregistermvvm.auth.DashboardActivity;
import com.inihood.loginandregistermvvm.notedb.NoteDatabase;
import com.inihood.loginandregistermvvm.notedb.model.Note;

import java.lang.ref.WeakReference;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText user_name;
    private EditText email;
    private EditText password;
    private EditText conFirmPassword;
    private Button signUpBtn;
    private NoteDatabase noteDatabase;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
       // note = new Note();
        noteDatabase = NoteDatabase.getInstance(this);
    }

    private void setResult(Note note, int flag, long id){
        setResult(flag,new Intent().putExtra("note",note));
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("name", note.getDisplay_name());
        intent.putExtra("username", note.getUsername());
        startActivity(intent);
        finish();
    }

    private void validate() {
        String name = fullName.getText().toString();
        String email_in = email.getText().toString();
        String password_in = password.getText().toString();
        String re_password = conFirmPassword.getText().toString();
        String username = user_name.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email_in) && !TextUtils.isEmpty(password_in)){
            if (email_in.contains("@")){
                if (!username.contains(" ")){
                    if (password_in.equals(re_password)){
                        startRegistration(name, email_in, password_in, username);
                    }else {
                        Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, getString(R.string.no_space), Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, getString(R.string.email_not_valid), Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, getString(R.string.check_fields), Toast.LENGTH_LONG).show();
        }
    }

    private void startRegistration(String name, String email_in,
                                   String password_in, String username) {

        note = new Note(name, username, password_in, email_in);
        new InsertTask(this, note).execute();

    }

    private void startMainActivity(long id) {
       // DashboardActivity.start(this);


    }

    private void init() {
        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        conFirmPassword = findViewById(R.id.confirm_password);
        signUpBtn = findViewById(R.id.btnCreateAccount);
        user_name = findViewById(R.id.username);
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<RegisterActivity> activityReference;
        private Note note;
        public long j;

        // only retain a weak reference to the activity
        InsertTask(RegisterActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            j = activityReference.get().noteDatabase.getNoteDao().insertNote(note);
            note.setNote_id(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(note, 1, j);
                activityReference.get().finish();
            }
        }
    }

}
