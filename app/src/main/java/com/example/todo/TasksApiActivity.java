package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

public class TasksApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_api);
    }
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DIALOG_ACCOUNTS:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Select a Google account");
//                final Account[] accounts = accountManager.getAccountsByType("com.google");
//                final int size = accounts.length;
//                String[] names = new String[[]size];
//                for (int i = 0; i < size; i++) {
//                    names[[]i] = accounts[[]i].name;
//                }
//                builder.setItems(names, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Stuff to do when the account is selected by the user
//                        gotAccount(accounts[[]which]);
//                    }
//                });
//                return builder.create();
//        }
//        return null;
//    }
}