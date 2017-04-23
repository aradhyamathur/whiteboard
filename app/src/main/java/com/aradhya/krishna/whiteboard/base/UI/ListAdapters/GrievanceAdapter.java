package com.aradhya.krishna.whiteboard.base.UI.ListAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.GrievanceModel;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 4/11/17.
 */

public class GrievanceAdapter extends RecyclerView.Adapter<GrievanceAdapter.ViewHolder> {

    private List<GrievanceModel> grievanceModelList;
    private static VClickListener myClickListener;



    public GrievanceAdapter(List<GrievanceModel> grievances){
        grievanceModelList = grievances;
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView grievanceTitleTextView;
        private TextView grievanceDateTextView;
        private TextView grievanceInfoTextView;

        public ViewHolder(View view) {
            super(view);

            grievanceTitleTextView = (TextView) view.findViewById(R.id.grievance_title_textView8);
            grievanceDateTextView = (TextView) view.findViewById(R.id.grievance_date_textView9);
            grievanceInfoTextView = (TextView) view.findViewById(R.id.grievance_info_textView10);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anonymous_complaint_list_style,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GrievanceModel grievance = grievanceModelList.get(position);
        holder.grievanceInfoTextView.setText(grievance.getInfo());
        holder.grievanceTitleTextView.setText(grievance.getSubject());
        holder.grievanceDateTextView.setText(grievance.getDate_info());
    }

    @Override
    public int getItemCount() {
        return grievanceModelList.size();
    }


}
