package com.inihood.loginandregistermvvm.notedb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.inihood.loginandregistermvvm.util.Constants;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = Constants.TABLE_NAME_USER)
public class Note implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long note_id;

    @ColumnInfo(name = "user_content") // column name will be "note_content" instead of "display_name" in table

    private String display_name;
    private String username;
    private String password;
    private String email;
    private Date date;

    //    public Note(int note_id, String display_name, String username, Date date) {
//        this.note_id = note_id;
//        this.display_name = display_name;
//        this.username = username;
//        this.date = date;
//    }

    public Note(String display_name, String username, String password, String email) {
        this.display_name = display_name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.date = new Date(System.currentTimeMillis());
    }

    @Ignore
    public Note(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (note_id != note.note_id) return false;
        return username != null ? username.equals(note.username) : note.username == null;
    }



    @Override
    public int hashCode() {
        int result = (int)note_id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", display_name='" + display_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
