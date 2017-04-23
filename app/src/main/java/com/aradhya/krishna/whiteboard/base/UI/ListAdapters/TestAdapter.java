package com.aradhya.krishna.whiteboard.base.UI.ListAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.TestModel;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import java.util.List;

/**
 * Created by krishna on 4/16/17.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private  List<TestModel> testModelList;
    private VClickListener myClickListener;
    private Context context;
    public TestAdapter(List<TestModel> models, Context context){
        testModelList = models;
        this.context = context;
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView testSubjectTextView;
        private TextView testDateTextView;
        private TextView testSyllabusTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            testDateTextView = (TextView) itemView.findViewById(R.id.test_date_textView11);
            testSubjectTextView = (TextView) itemView.findViewById(R.id.test_subject_textView10);
            testSyllabusTextView = (TextView) itemView.findViewById(R.id.test_syllabus_textView12);
            Typeface face= Typeface.createFromAsset(context.getAssets(), "comicbd.ttf");
            testDateTextView.setTypeface(face);
            testSubjectTextView.setTypeface(face);
            testSyllabusTextView.setTypeface(face);
            itemView.setOnClickListener(this);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_style,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestModel model = testModelList.get(position);
        holder.testSyllabusTextView.setText(model.getSyllabus());
        holder.testSubjectTextView.setText(model.getSubject());
        holder.testDateTextView.setText(model.getTest_date());

    }

    @Override
    public int getItemCount() {
        return testModelList.size();
    }

}
