package com.example.myexercises;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myexercises.DataBase.App;
import com.example.myexercises.DataBase.Collection;
import com.example.myexercises.DataBase.Exercise;
import com.example.myexercises.dialog.NameDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.functions.Action;


public class MainActivity extends AppCompatActivity implements NameDialog.NameDialogListener {

    public ImageView imgview;
    public ConstraintLayout myLayout;
    ConstraintSet set;
    //ArrayList<Button> AppPoints;//хранятся все кнопки
    private Exercise exercise;
    //public AppDataBase dateBase;
    public float x,y,wight=720,hight=1120;
    public Boolean create = false;
    public String CollectionName = "";

    List<Button> allbutton = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppPoints = new ArrayList<Button>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);//PORTRAIT

        myLayout = (ConstraintLayout) findViewById(R.id.backLayout);
        set = new ConstraintSet();

        imgview = (ImageView) findViewById(R.id.faceImageView);
        imgview.setOnTouchListener(iNeedLongTouch);
       // dateBase = App.getInstance().getDataBase();//получение базы данных
        setStartExerciseData(getBaseContext());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getViewModelStore();
        Boolean coincidence = false;//совпадение
        ArrayList<View> allButtons;
        //все  кнопки в лейауте достать
        allButtons =  ((ConstraintLayout) findViewById(R.id.backLayout)).getTouchables();
        List<Collection> collections = App.getInstance().getAllCollection();
        if(allButtons.size() > collections.size()) {
            for (View b : allButtons) {
                for (Collection col : collections) { int t = b.getId();
                    if (b.getId() == col.button_id) {coincidence = true;break;}
                } if(!coincidence)
                    b.setVisibility(View.GONE);
                coincidence = false;
            }
        }

    }

//длительное касание экрана - добавляет точку с коллекцией
    private View.OnTouchListener iNeedLongTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //координаты
             x = event.getX();
             y = event.getY();
            //размер окна
             hight = v.getHeight();
             wight = v.getWidth();
            //время первое касание и последне действие
            long g = event.getEventTime();
            long r = event.getDownTime();
            //если держим долго
            if (event.getEventTime() - event.getDownTime() > 800) {
                //вызвать окно с добавлением названия комплекса
                new NameDialog().show(getSupportFragmentManager(), "collectname");
                //добавление идет по возврату
            }
            return true;
        }
    };

    //перемещение точек по стартовому экрану
    View.OnTouchListener move_otl = new OnTouchListener() {
        private float mx;
        private float my;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final float startX = event.getX();
            final float startY = event.getY();
            int t =v.getId();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                    mx = startX - params.leftMargin;
                    my = startY - params.topMargin;
                    break;
                case MotionEvent.ACTION_MOVE:
                    ConstraintLayout.LayoutParams lparams = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                    lparams.leftMargin = (int) (startX - mx);
                    lparams.topMargin = (int) (startY - my);
                    //изменть координаты (b_id,b_y),  а так же айди кнопки?
                    v.setLayoutParams(lparams);
                    break;
            }
            //смотрю сколько времени , если это просто клик то его надо обработать
            if(event.getEventTime()- event.getDownTime() < 200)
                return false;
            return true;
        }
    };


    //он вообще фурыкает??
    View.OnLongClickListener long_ocl = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//        Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
//        intent.putExtra("exercise", "dsikj");//отправлять надо айди колллекции
//        startActivity(intent);
            return false;
        }
    };

//при нажатии на кнопку отрывает CollectionActivity
    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default: {
                    Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
                    Button cur = ((Button) findViewById(v.getId()));
                    Collection collection = App.getInstance().getButtonCol(cur.getId());
                    intent.putExtra("collection", collection);//отправлять надо айди колллекции
                    startActivity(intent);

                }
            }
        }
    };

//задает старовые данные

    private void setStartExerciseData(Context context) {
        List<Collection> collections = App.getInstance().getAllCollection();
        if(collections.size()==0)
        {  long ColId=0;
        int[] x = new int[] {135,380};
        int[] y = new int[] {215,500};
        List<Long> colID = new ArrayList<>();
        int i=0;
        String[] ResButName = getResources().getStringArray(R.array.front_button_name);//имена кнопок
        String[] ButText = getResources().getStringArray(R.array.button_text);//имена кнопок
        for (String item : ResButName) {//добавление стандартных кнопок  их слушателей
        Button b = new Button(context);
        int n = b.getId();
        b.setId(x[i]);//айди
        b.setText(ButText[i]);//текст
        Collection col = new Collection(ButText[i], x[i],y[i]);//инициализаця коллекции
        b.setLayoutParams(new ViewGroup.LayoutParams(30, 40));//15 20
        b.setOnClickListener(ocl);//слушатель
        b.setOnTouchListener(move_otl);
        //добавление в базу где то должно быт
        ColId = App.getInstance().InsertCol(col);//в БД
        colID.add(ColId);//список начальных каллекций
        myLayout.addView(b);//добавление кнопки на экран
        allbutton.add(b);
        set.clone(myLayout);
        setConnectSettings(b, x[i], y[i], wight, hight);
        set.applyTo(myLayout);
        i++;
        }
        //заполнение базы данных(ВЫЛЕТАЕТ надо в не в этом потоке? как?)
        //Collection col = new Collection("Упраженения на пресс", "pressBit");
        App.getInstance().InsertEl(new Exercise("Планка", "https://ferrum-body.ru/wp-content/uploads/2017/02/ibj0Zj.jpg",0), colID.get(1));
        App.getInstance().InsertEl(new Exercise("Скручивания", "https://cross.expert/wp-content/uploads/2017/09/kosye-skruchivaniya.jpg",0), colID.get(1));
        App.getInstance().InsertEl(new Exercise("Подъем гантелей", "https://moniteur.ru/wp-content/uploads/images/stories/217/3.jpg",0),colID.get(0));
    }
        else
            for(Collection collection: collections)
            {
                Button b = new Button(getBaseContext());
                b.setId(collection.button_id);//айди
                b.setText(collection.name);//текст
                b.setLayoutParams(new ViewGroup.LayoutParams(30, 40));//15 20
                b.setOnClickListener(ocl);//слушатель
                b.setOnTouchListener(move_otl);
                //добавление в базу где то должно быт
                myLayout.addView(b);//добавление кнопки на экран
                allbutton.add(b);
                set.clone(myLayout);
                setConnectSettings(b, collection.button_id,collection.button_y, wight, hight);
                set.applyTo(myLayout);
            }
//        Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                dateBase.collectionDao().insert(new Collection(1001,"Упраженения на пресс", "pressBit"));
//            }
//        }).subscribe();

    }
//присоединяет точку и устанавливает ее положение
    private void setConnectSettings(Button b, float x, float y, float w, float h) {
        set.connect(b.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        set.connect(b.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        set.connect(b.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        set.connect(b.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        set.setVerticalBias(b.getId(), y / h);
        set.setHorizontalBias(b.getId(), x / w);
    }

    @Override// Пользователь коснулся положительной кнопки диалога
    public void onDialogPositiveClick(int color, String s) {
        create = true;
        CollectionName = s;
        Button b = new Button(getBaseContext());//(v.getContext());
        b.setId((int) x);
        b.setLayoutParams(new ViewGroup.LayoutParams(30, 40));//15 20
        b.setOnClickListener(ocl);
        //добавление в базу где то должно быть
        Collection collection = new Collection(CollectionName,(int)x,(int)y);
        App.getInstance().InsertCol(collection);
        //отображение
        myLayout.addView(b);
        //задаем текущие парамерты лэйаута
        set.clone(myLayout);
        //делаю привязку
        setConnectSettings(b, x, y, wight, hight);
        //выполнить
        set.applyTo(myLayout);
    }

    @Override// Пользователь коснулся отрицательной кнопки диалога
    public void onDialogNegativeClick(boolean need) {
        create = false;
    }

}
