package com.example.myexercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexercises.DataBase.Exercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterExercise {//extends RecyclerView.Adapter<AdapterExercise.ExerciseViewHolder> {
//    private ArrayList<Exercise> _exeList;
//
//    public AdapterExercise(ArrayList<Exercise> actors) {
//        _exeList = actors;
//    }
//
//    @NonNull
//    @Override
//    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.colect_table, parent, false);
//        return new AdapterExercise.ExerciseViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
//        holder.bind(_exeList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return _exeList.size();
//    }
//
//    class ExerciseViewHolder extends RecyclerView.ViewHolder{
//        private ImageView _exeImageView;
//        private TextView _exeNameTextView;
//
//        public ExerciseViewHolder(@NonNull View itemView) {
//            super(itemView);
//            _exeImageView = itemView.findViewById(R.id.table__image_view);
//            _exeNameTextView = itemView.findViewById(R.id.table_text_view);
//        }
//
//        public void bind(Exercise exercise) {
//            _exeNameTextView.setText(exercise.name);
//            String actorPhotoUrl = exercise.imageUrl;
//            Picasso.with(itemView.getContext())
//                    .load(actorPhotoUrl)
//                    .resize(500, 500)
//                    .centerInside()
//                    .into(_exeImageView);
//            _exeImageView.setVisibility(actorPhotoUrl != null ? View.VISIBLE : View.GONE);
//        }
//
//    }
}
