package com.example.myexercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexercises.DataBase.App;
import com.example.myexercises.DataBase.Collection;
import com.example.myexercises.DataBase.Exercise;
import com.example.myexercises.dialog.DeleteDialog;

import java.util.List;

public class CollectionActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener {

    private TextView _toolbarTextView;
    private RecyclerView _exeRecyclerView;
    private CollectionAdapter _exeAdapter;
    private List<Exercise> exens;
    private Collection collection;
    protected static CollectionActivity currentActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ActionBar actBAr = getSupportActionBar();
        actBAr.setHomeButtonEnabled(true);
        actBAr.setDisplayHomeAsUpEnabled(true);

        currentActivity = this;

        Bundle intent = getIntent().getExtras();
        collection = (Collection) intent.getSerializable("collection");
        //String name_collection = (String) intent.getSerializable("collection");
        String name_collection = collection.name;
        _toolbarTextView = findViewById(R.id.toolbar_text_view);
        _toolbarTextView.setText(name_collection);
        _exeRecyclerView = findViewById(R.id.actors_recycler_view);
        exens = App.getInstance().getElemInCol(collection.id);
       adapterSettings();

    }

    private void adapterSettings()
    {
        _exeAdapter = new CollectionAdapter(getApplicationContext(), new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise item) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                intent.putExtra("exercise", item.id);//отправлять надо айди колллекции
                intent.putExtra("cols",collection.id);
                startActivity(intent);
            }
        });
        _exeAdapter.setItems(exens);
        _exeRecyclerView.setLayoutManager(new GridLayoutManager(currentActivity, 2));
        _exeRecyclerView.setAdapter(_exeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exens = App.getInstance().getElemInCol(collection.id);
        adapterSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.collection_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()== R.id.collect_add_settings)//Добавить
        {
            Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
            intent.putExtra("exercise",0);
            intent.putExtra("cols",collection.id);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()== R.id.collect_delete_settings)
        {
            new DeleteDialog().show(getSupportFragmentManager(), "collectnamet");
            return true;}

        //Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();//выводит кружочек с выбраным действием внизу экрана
        return super.onOptionsItemSelected(item);
    }

//подтверждено удаление комплекса
    @Override
    public void onDialogPositiveClick() {
        App.getInstance().DeleteCol(collection);
        finish();
    }
}
