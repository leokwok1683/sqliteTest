package com.example.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText et_name, et_surname, ed_marks, ed_id;
    Button btn_addData, btn_viewData, btn_update, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        et_name = findViewById(R.id.editText_name);
        et_surname = findViewById(R.id.editText_surname);
        ed_marks = findViewById(R.id.editText_mark);
        ed_id = findViewById(R.id.editText_id);
        btn_addData = findViewById(R.id.btn_add);
        btn_viewData = findViewById(R.id.btn_view);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        addData();
        viewAll();
        updateData();
        DeleteData();
    }

    public void addData() {
        btn_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = et_name.getText().toString();
                String b = et_surname.getText().toString();
                String c = ed_marks.getText().toString();


                boolean isInsertrd = myDb.insertData(et_name.getText().toString(),
                        et_surname.getText().toString(),
                        ed_marks.getText().toString());
//                Log.v("leo", et_name.getText().toString());
//
                if (isInsertrd == true) {
                    Toast.makeText(MainActivity.this, "Inserted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not inserted",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll() {
        btn_viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Surname: " + res.getString(2) + "\n");
                    buffer.append("Marks: " + res.getString(3) + "\n\n");
                }
                showMessage("Data", buffer.toString());
            }
    });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

        public void updateData() {
            btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
        boolean isUpdate = myDb.updateData(ed_id.getText().toString(),et_name.getText().toString(),
                et_surname.getText().toString(), ed_marks.getText().toString());
        if (isUpdate == true) {
            Toast.makeText(MainActivity.this, "Update",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Not update",
                    Toast.LENGTH_LONG).show();
        }
    }
        });
        }

        public void DeleteData(){
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer deleteRows = myDb.deleteData(ed_id.getText().toString());
                    if(deleteRows > 0){
                        Toast.makeText(MainActivity.this, "Delete",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Not delete",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}