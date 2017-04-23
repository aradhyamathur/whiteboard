package com.aradhya.krishna.whiteboard.base.UI.ListAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.PlacementsModel;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import java.util.List;

/**
 * Created by krishna on 4/16/17.
 */

public class PlacementAdapter extends RecyclerView.Adapter<PlacementAdapter.ViewHolder> {

    private List<PlacementsModel> placementsModelList;
    private VClickListener myClickListener;

    public PlacementAdapter(List<PlacementsModel> models){
        placementsModelList = models;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView companyName;
//        private TextView date;
        private TextView info;
        public ViewHolder(View view) {
            super(view);
            companyName = (TextView) view.findViewById(R.id.placement_company_textView10);
//            date = (TextView) view.findViewById(R.id.placement_date_textView11);
            info = (TextView) view.findViewById(R.id.placement_info);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.placement_list_style,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlacementsModel model = placementsModelList.get(position);
        holder.companyName.setText(model.getCompany());
        holder.info.setText(model.getInfo());


    }

    @Override
    public int getItemCount() {
        return placementsModelList.size();
    }

}
