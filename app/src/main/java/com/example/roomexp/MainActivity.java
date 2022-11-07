package com.example.roomexp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName, etPhone;
    private Button btnSave;
    private RecyclerView rcvView;
    private ArrayList<Model> contacts = new ArrayList<>();
    private RoomAdapter adapter;
    private ModelDatabase database;
    @Nullable
    private Model model;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = ModelDatabase.getDb(this);
        initViews();
        initAdapter();


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllContacts();
    }

    private void loadAllContacts() {
        ArrayList<Model> contact = (ArrayList<Model>) database.userDao().getSupportAll();
        if (contact != null) {
            contacts.clear();
            contacts.addAll(contact);
            adapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        rcvView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RoomAdapter(contacts, new ClickListener() {
            @Override
            public void onViewClick(View view, int position) {
                if (view.getId() == R.id.iv_more) {
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.delete_menu:
                                    database.userDao().deleteByid(contacts.get(position).getUid());
                                    contacts.remove(position);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case R.id.edit_menu:
                                    model = contacts.get(position);
                                    etName.setText(model.firstName);
                                    etPhone.setText(model.lastName);
                                    contacts.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    updateView();
                                    break;


                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }

            }

        });
        rcvView.setAdapter(adapter);

    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phones);
        btnSave = findViewById(R.id.btn_save);
        rcvView = findViewById(R.id.rcv_view);

        btnSave.setOnClickListener(this);

    }

    private void updateView() {
        if (model != null) btnSave.setText("Update Contact");
        else btnSave.setText("Add Contact");
    }

    @Override
    public void onClick(View view) {
        if (view == btnSave) {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            if (etName.getText().toString().trim().length() > 0 || etPhone.getText().toString().trim().length() > 0) {
                if (model != null) {
                    model.setFirstName(name);
                    model.setLastName(phone);

                    try {
                        database.userDao().update(model);
                        Toast.makeText(MainActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this,"phone already exists", Toast.LENGTH_LONG).show();
                    }

                    model = null;
                    updateView();
                } else {
                    try {
                        long result = database.userDao().insert(new Model(name, phone));
                        Toast.makeText(MainActivity.this, "Record Inserted", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,"phone already exists", Toast.LENGTH_LONG).show();
                    }

                }
                etName.setText("");
                etPhone.setText("");
                loadAllContacts();
            } else {
                Toast.makeText(MainActivity.this, "enter the specific detail below", Toast.LENGTH_SHORT).show();
            }


        }

    }

}