package com.example.myexercises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myexercises.DataBase.App;
import com.example.myexercises.DataBase.Exercise;
import com.example.myexercises.dialog.DeleteDialog;
import com.example.myexercises.dialog.NameDialog;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ExerciseActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener {
    Exercise exercise;
    int colID;
    //таймер
    Button timerButton;
    long startTime = 60000;
    long currentTime = startTime;
    boolean timerHasStarted = false;
    //картинка
    private final int pic_Img = 1;
    ImageView exenImageView;
    Bitmap selectedImage;
    //кнопки
    Button uploadChange;
    Button loadImg;

    //текстовые поля
    EditText name_text;
    EditText des_text;
    EditText count;
    EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Bundle intent = getIntent().getExtras();
        int idin = (int) intent.getSerializable("exercise");
        colID = (int) intent.getSerializable("cols");
        if (idin != 0)
            exercise = App.getInstance().getElement(idin);//(Exercise) intent.getSerializable("exercise");
        else exercise = new Exercise("Добавленный", "", colID);
        String name_ex = exercise.name;
        name_text = findViewById(R.id.nameEdit);
        des_text = findViewById(R.id.descriptEdit);
        count = findViewById(R.id.count_edit);
        time = findViewById(R.id.time_edit);
        uploadChange = findViewById(R.id.complexset);
        timerButton = findViewById(R.id.timer);
        loadImg = findViewById(R.id.uploadButton);
        if (name_ex.equals("Добавленный"))//будем создавать с нуля, то есть все поля видимы и активны кроме запуска таймера
            changeMod();
        else//открыто для просмотра
        {
            lookMod();
        }


//получили кнопку с таймером и привязали слушателя
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTime < 1000) currentTime = startTime;
                ExerciseTimer countDownTimer = new ExerciseTimer(currentTime, 10);
                if (!timerHasStarted) {
                    countDownTimer.start();
                    timerHasStarted = true;
                } else {///ни чего не работает он продоллжает отсчет ни смотря ни на что
                    timerButton.setText(String.valueOf("Поехали!"));
                    countDownTimer.cancel();
                    timerHasStarted = false;
                }

            }
        });

//при нажатии на загрузку фотографии
        exenImageView = findViewById(R.id.exe_picture_view);
        loadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, pic_Img);
            }
        });


        uploadChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise.desctiption = des_text.getText().toString();
                exercise.name = name_text.getText().toString();
                exercise.image = selectedImage;
                if (time.getText().toString().length() != 0) {
                    exercise.time = Integer.parseInt(time.getText().toString());
                    timerButton.setText((exercise.time));//вылетает
                }
                if (count.getText().toString().length() != 0)
                    exercise.repeat = Integer.parseInt(count.getText().toString());
                if (App.getInstance().getElement(exercise.id) != null)
                    App.getInstance().UpdateEl(exercise);
                else
                    App.getInstance().InsertEl(exercise, exercise.col);//col дает номер колекци в которую идет добавление
                lookMod();
            }
        });
    }

    private  void lookMod()
    {
        uploadChange.setVisibility(View.INVISIBLE);
        loadImg.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
       // if ( exercise.repeat >0) count.setText(exercise.repeat);
        if (exercise.repeat == 0) count.setVisibility(View.INVISIBLE);
        else count.setRawInputType(0x00000000);
        if(exercise.time==0) timerButton.setVisibility(View.INVISIBLE);
        else{startTime = exercise.time; timerButton.setVisibility(View.VISIBLE);}
        name_text.setText(exercise.name);
        //блокировка текстовых полей(имя и описание)
        name_text.setRawInputType(0x00000000);//0x10000000
        //if(exercise.desctiption != null)
       // des_text.setText(exercise.desctiption);
        des_text.setRawInputType(0x00000000);//0x10000000
        startTime = exercise.time;
        String actorPhotoUrl = exercise.imageUrl;//добавление фото
        exenImageView = findViewById(R.id.exe_picture_view);
        if (exercise.image==null){
            {if(actorPhotoUrl.length()!=0) {
                Picasso.with(getBaseContext())
                        .load(actorPhotoUrl)
                        .resize(500, 500)
                        .centerInside()
                        .into(exenImageView);
            }
                exenImageView.setVisibility(actorPhotoUrl != null ? View.VISIBLE : View.GONE);}}
        else exenImageView.setImageBitmap(exercise.image);

    }

    private void changeMod() {
        uploadChange.setVisibility(View.VISIBLE);
        loadImg.setVisibility(View.VISIBLE);
        timerButton.setVisibility(View.INVISIBLE);
        name_text.setRawInputType(0x10000000);//0x10000000
        des_text.setRawInputType(0x10000000);//0x10000000
        count.setVisibility(View.VISIBLE);
        count.setRawInputType(0x10000000);//0x10000000
        time.setVisibility(View.VISIBLE);
        time.setRawInputType(0x10000000);//0x10000000
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exerc_change_settings) {
            changeMod();
            return true;
        }
        if (item.getItemId() == R.id.exerc_includ_settings)
            return true;
        if (item.getItemId() == R.id.exerc_delete_settings)
        {
            new DeleteDialog().show(getSupportFragmentManager(), "collectname");
            return true;}
        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();//выводит кружочек с выбраным действием внизу экрана
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case pic_Img:
                if (resultCode == RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);//final bitmap
                        exenImageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onDialogPositiveClick() {
        App.getInstance().DeleteEl(exercise,colID);
        finish();
    }


    public class ExerciseTimer extends CountDownTimer {

        public ExerciseTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timerButton.setText(String.valueOf("Поехали!"));
            cancel();

        }

        @Override
        //таймер минута секунда милисекнды
        public void onTick(long millis) {
            timerButton.setText(String.format("%02d:%02d:%02d", millis / 1000 / 60 % 60, millis / 1000 % 60, millis / 10 % 100));
            currentTime = millis;
        }

    }

}
