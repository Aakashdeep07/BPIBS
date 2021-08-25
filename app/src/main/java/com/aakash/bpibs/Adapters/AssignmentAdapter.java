package com.aakash.bpibs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.ModelClass.AssignmentModel;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AssignmentAdapter extends FirebaseRecyclerAdapter<AssignmentModel, AssignmentAdapter.AssignmentViewHolder> {
    Context context;

    public AssignmentAdapter(FirebaseRecyclerOptions<AssignmentModel> options, Context applicationContext) {
        super(options);
        this.context = applicationContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position, @NonNull AssignmentModel model) {
        holder.subjectTitle.setText(model.subject);
        holder.assignedDate.setText(model.assignedDate);
        holder.dueDate.setText(model.lastDate);
        holder.descriptionAss.setText(model.description);
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder_assignment_layout, parent, false);
        return new AssignmentViewHolder(view);
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTitle;
        TextView assignedDate;
        TextView dueDate;
        TextView descriptionAss;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTitle = itemView.findViewById(R.id.subject_title);
            assignedDate = itemView.findViewById(R.id.date_assigned);
            dueDate = itemView.findViewById(R.id.date_due);
            descriptionAss = itemView.findViewById(R.id.assignment_desc);
        }
    }
}
