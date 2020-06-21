package com.example.myexercises.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.example.myexercises.R;

import java.util.HashMap;
import java.util.Map;


public class NameDialog extends DialogFragment {

    //Деятельность, которая создает экземпляр этого фрагмента диалога, должна
//реализовать этот интерфейс для получения обратных вызовов событий.
//Каждый метод передает DialogFragment в случае, если хост должен запросить его.
    public interface NameDialogListener {
        public void onDialogPositiveClick(int color, String name);
        public void onDialogNegativeClick(boolean need);
    }
    // Использовать этот экземпляр интерфейса для доставки событий действия
    NameDialogListener mListener;

    @NonNull//EditText des = getActivity().findViewById(R.id.name_edit_text);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Новый комплекс");  // заголовок
        // builder.setMessage("Введите название"); // сообщение
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.collection_name_layout, null);
         final int textColor = ContextCompat.getColor(dialog.getContext(), R.color.colorPrimary);
//        Button rad = dialog.findViewById(R.id.radButton);
//        rad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
//            }
//        });
        final EditText inputText = dialog.findViewById(R.id.name_edit_text);
        builder.setView(dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //String s =  R.id.name_edit_text
                        mListener = (NameDialogListener) getActivity();
                        //inputText.setText("Слова");
                        String d = inputText.getHint().toString();//корректно
                        String text = inputText.getText().toString();
                        if (text.length()<1) text=d;
                        mListener.onDialogPositiveClick(textColor,text);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener = (NameDialogListener) getActivity();
                mListener.onDialogNegativeClick(false);
                dialog.cancel();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }



    // Переопределите метод Fragment.onAttach (), чтобы создать экземпляр NoticeDialogListener
//    @Override
//    public void onAttach() {
//        onAttach();
//        try {
//// Создание экземпляра NoticeDialogListener, чтобы мы могли отправлять события на хост
//            mListener = (NameDialogListener) activity;
//        } catch (ClassCastException e) {
//// Деятельность не реализует интерфейс, выдает исключение
//            throw new ClassCastException(activity.toString()
//                    + " must implement NoticeDialogListener");
//    }

//    // Переопределите метод Fragment.onAttach (), чтобы создать экземпляр NoticeDialogListener
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//// Убедитесь, что действие хоста реализует интерфейс обратного вызова
//        try {
//// Создание экземпляра NoticeDialogListener, чтобы мы могли отправлять события на хост
//            mListener = (NameDialogListener) activity;
//        } catch (ClassCastException e) {
//// Деятельность не реализует интерфейс, выдает исключение
//            throw new ClassCastException(activity.toString()
//                    + " must implement NoticeDialogListener");
//
//        }
 //   }

}

