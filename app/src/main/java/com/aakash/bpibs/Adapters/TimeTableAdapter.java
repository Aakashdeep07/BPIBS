package com.aakash.bpibs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.ModelClass.TimeTableHandler;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class TimeTableAdapter extends FirebaseRecyclerAdapter<TimeTableHandler, TimeTableAdapter.TimeViewHolder> {
    Context context;

    public TimeTableAdapter(FirebaseRecyclerOptions<TimeTableHandler> options, Context applicationContext) {
        super(options);
        this.context = applicationContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull com.aakash.bpibs.Adapters.TimeTableAdapter.TimeViewHolder holder, int position, @NonNull TimeTableHandler model) {
        holder.lectureDay.setText(model.zero);
        holder.lectureOne.setText(model.one);
        holder.lectureTwo.setText(model.two);
        holder.lectureThree.setText(model.three);
        holder.lectureFour.setText(model.four);
        holder.lectureFive.setText(model.five);
        holder.lectureSix.setText(model.six);
        holder.lectureSeven.setText(model.seven);
        holder.lectureEight.setText(model.eight);
    }

    @NonNull
    @Override
    public com.aakash.bpibs.Adapters.TimeTableAdapter.TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder_time_layout, parent, false);
        return new TimeViewHolder(view);
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView lectureDay;
        TextView lectureOne;
        TextView lectureTwo;
        TextView lectureThree;
        TextView lectureFour;
        TextView lectureFive;
        TextView lectureSix;
        TextView lectureSeven;
        TextView lectureEight;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            lectureDay = itemView.findViewById(R.id.lecture_day);
            lectureOne = itemView.findViewById(R.id.lecture_one);
            lectureTwo = itemView.findViewById(R.id.lecture_two);
            lectureThree = itemView.findViewById(R.id.lecture_three);
            lectureFour = itemView.findViewById(R.id.lecture_four);
            lectureFive = itemView.findViewById(R.id.lecture_five);
            lectureSix = itemView.findViewById(R.id.lecture_six);
            lectureSeven = itemView.findViewById(R.id.lecture_seven);
            lectureEight = itemView.findViewById(R.id.lecture_eight);
        }
    }
}
