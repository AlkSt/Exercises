package com.example.myexercises;

import android.content.Context;
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
import java.util.Collection;
import java.util.List;

public class CollectionAdapter  extends RecyclerView.Adapter<CollectionAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList = new ArrayList<>();
    Context mContext;
    int theaterNumber;//=0;


    public interface OnItemClickListener {
        void onItemClick(Exercise item);
    }

    private final OnItemClickListener listener;

    public CollectionAdapter(Context mContext, OnItemClickListener listener){
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.colect_table, viewGroup, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder theaterViewHolder, int index) {

        theaterViewHolder.bind(exerciseList.get(index), listener);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public void setItems(Collection<Exercise> exen) {
        exerciseList.addAll(exen);
        notifyDataSetChanged();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private ImageView _exeImageView;
        private TextView _exeNameTextView;


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            _exeImageView = itemView.findViewById(R.id.table__image_view);
            _exeNameTextView = itemView.findViewById(R.id.table_text_view);
        }

        public void bind(final Exercise item, final OnItemClickListener listener) {
            bind(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

        public void bind(final Exercise exercise) {
            _exeNameTextView.setText(exercise.name);
            String actorPhotoUrl = exercise.imageUrl;
            if(exercise.image==null)
            {Picasso.with(itemView.getContext())
                    .load(actorPhotoUrl)
                    .resize(500, 500)
                    .centerInside()
                    .into(_exeImageView);
            _exeImageView.setVisibility(actorPhotoUrl != null ? View.VISIBLE : View.GONE);}
            else _exeImageView.setImageBitmap(exercise.image);
            //из интернета
            //Picasso.with(itemView.getContext()).load(theaterPhotoUrl).into(theaterImageView);
            //theaterImageView.setVisibility(theaterPhotoUrl != null ? View.VISIBLE : View.GONE);
        }

    }

}