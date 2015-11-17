package com.example.hamao.todoapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hamao on 11/16/15.
 */
public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText editText;
    private EditText editDate;
    private String itemName;
    private String dueDate;

    public interface EditTodoItemDialogListener {
        void onFinishEditDialog(String inputText, String Date);
    }

    @SuppressLint("ValidFragment")
    public EditItemDialog(String itemName, String dueDate) {
        this.itemName = itemName;
        this.dueDate = dueDate;
    }

    public EditItemDialog(){

    }

    public static EditItemDialog newInstance(String title, String itemName, String dueDate) {
        EditItemDialog frag = new EditItemDialog(itemName, dueDate);
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_item, container);
        editText = (EditText) view.findViewById(R.id.editTodoName);
        editText.setText(itemName);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editDate = (EditText) view.findViewById(R.id.editTodoDate);
        editDate.setText(dueDate);
        editDate.setOnEditorActionListener(this);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        editText.requestFocus();

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Return input text to activity
            EditTodoItemDialogListener listener = (EditTodoItemDialogListener) getActivity();
            listener.onFinishEditDialog(editText.getText().toString(), editDate.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }
}

