package com.aradhya.krishna.whiteboard.base.UI.ListAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.AssignmentModel;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import java.util.List;

/**
 * Created by krishna on 4/15/17.
 */

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<AssignmentModel> assignmentModelList;
    private static VClickListener myClickListener;

    public AssignmentAdapter(List<AssignmentModel> models) {
        this.assignmentModelList = models;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView subjectTextView;
        private TextView submissionDateTextView;
        private TextView infoTextView;

        public ViewHolder(View view) {
            super(view);

            subjectTextView = (TextView) view.findViewById(R.id.assignment_subject);
            submissionDateTextView = (TextView) view.findViewById(R.id.assignment_submission_date);
            infoTextView = (TextView) view.findViewById(R.id.assignment_info);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getAdapterPosition(),view);
        }
    }
    public void setOnItemClickListener(VClickListener clickListener){
        this.myClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_list_style,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AssignmentModel assignmentModel = assignmentModelList.get(position);
        holder.infoTextView.setText(assignmentModel.getInfo());
        holder.submissionDateTextView.setText(assignmentModel.getSubmissionDate());
        holder.subjectTextView.setText(assignmentModel.getSubject());
    }

    @Override
    public int getItemCount() {
        return assignmentModelList.size();
    }



}
