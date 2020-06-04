package com.example.myexercises.DataBase;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Room;



import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static App instance;
    private AppDataBase database;
    private  static  Exercise ex;
    private static Collection col;
    private static long ID;
    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.DATABASE_NAME).allowMainThreadQueries().build();
        //создвется в другом потоке

    }
    public  static  App getInstance() {return instance;}
    public  AppDataBase getDataBase(){return database;}

    public long InsertCol( Collection collection)
    {
        col = collection;
       // new InsertColTask().execute();
        return database.collectionDao().insert(col);
    }
    public long InsertEl( Exercise exercise, long colID) {
        ex = exercise;
        long elID = database.exerciseDao().insert(ex);
        InsertLink((int)colID,(int)elID);
//        new InsertElTask().execute();
        return elID;
    }

    public void InsertLink(int col, int ex)
    {
        database.callexDao().insert(new CallExec(col,ex));
    }

    public Exercise getElement(int _id) {
        Exercise addedAnimal = database.exerciseDao().getById(_id);
        return addedAnimal;
    }
    public List<Exercise> getAllElement() {
        List<Exercise> addedAnimal = database.exerciseDao().getAll();
        return addedAnimal;
    }
    public List<Exercise> getElemInCol(long col_id)
    {
        List<Exercise> exercises = new ArrayList<>();
        List<CallExec> callExecs = database.callexDao().getAlCol((int)col_id);
        for(CallExec call: callExecs )
            exercises.add(database.exerciseDao().getById(call.ExerciseID));
                return exercises;
    }

    public  void  UpdateEl( Exercise ex)
    {
        database.exerciseDao().update(ex);
    }
    public  Collection getCollection(int _id)
    {
        Collection addCol = database.collectionDao().getById(_id);
        return  addCol;
    }
    public  Collection getButtonCol(int but_id)//поиск коллекции по айди кнопки
    {
        Collection findCol = database.collectionDao().getByName(but_id);
        return  findCol;
    }

     public void DeleteEl(Exercise exercise, int col_id)
     {
         database.exerciseDao().delete(exercise);
         database.callexDao().delete(findLink(exercise.id,col_id));
     }

     public CallExec findLink(int ex_id, int col_id)
     {
        return database.callexDao().findLink(col_id,ex_id);
     }

    public List<Collection> getAllCollection() {
        List<Collection> addCol = database.collectionDao().getAll();
        return addCol;
    }

    public void DeleteCol(Collection col)
    {
        int col_id= (int)col.id;
        database.collectionDao().delete(col);
        List<CallExec> caldel = database.callexDao().getAlCol(col_id);
        for(CallExec pair: caldel)
        {
            int count = database.callexDao().getCount(pair.ExerciseID);
            if(count<2) {Exercise ex = database.exerciseDao().getById(pair.ExerciseID); database.exerciseDao().delete(ex); }
            database.callexDao().delete(pair);
        }
    }



    class InsertElTask extends AsyncTask<Void, Void, Void> {
        Exercise new_ex;
        @Override
        protected Void doInBackground(Void... voids) {
            database.exerciseDao().insert(new_ex);
            return null;
        }
        @Override
        protected void onPreExecute() {
            new_ex = ex;
        }
    }

    class InsertColTask extends AsyncTask<Void, Void, Void> {
        Collection new_col;
        @Override
        protected Void doInBackground(Void... voids) {
            database.collectionDao().insert(new_col);
            return null;
        }
        @Override
        protected void onPreExecute() {
            new_col = col;
        }
    }

    class GetTask extends AsyncTask<Integer, Void, Exercise> {
        Exercise new_col;

        @Override
        protected Exercise doInBackground(Integer... num) {
            return database.exerciseDao().getById(num[0]);

        }

        @Override
        protected void onPostExecute(Exercise aVoid) {
            ex = aVoid;
        }

        @Override
        protected void onPreExecute() {
            new_col = new Exercise("Упраженения на пресс", "pressBit",0);
        }
    }
}
