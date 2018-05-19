package com.unnamedgroup.ex3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Service.Job;


/**
 * Created by tptha on 19/05/18.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {
    private List<Job> jobList;
    private List<String> savedJob;

    public List<String> getSavedJob() {
        return savedJob;
    }

    public void setSavedJob(List<String> savedJob) {
        this.savedJob = savedJob;
    }

    Context context;

    public JobAdapter(Context context,List<Job> jobList, List<String> savedJob) {
        this.context = context;
        this.jobList = jobList;
        this.savedJob = savedJob;
    }

    /** * View holder class * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView company;
        TextView location;
        CheckBox star;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            company = view.findViewById(R.id.company);
            location = view.findViewById(R.id.location_view);
            star = view.findViewById(R.id.saved);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items
        holder.title.setText(jobList.get(position).getTitle());
        String temp = jobList.get(position).getCompany()+" - "+jobList.get(position).getType();
        holder.company.setText(temp);
        holder.location.setText(jobList.get(position).getLocation());
        if (savedJob.contains(jobList.get(position).getId()))
            holder.star.setChecked(true);
        // implement setOnClickListener event on item view.
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.star.isChecked()){
                    if (!savedJob.contains(jobList.get(position).getId())) {
                        savedJob.add(jobList.get(position).getId());
                        writeToFile(savedJob);
                    }
                }
                else {
                    if (!savedJob.contains(jobList.get(position).getId())) {
                        savedJob.remove(jobList.get(position).getId());
                        writeToFile(savedJob);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    private void writeToFile(List<String> sBody) {
        try {
            File gpxfile = new File(context.getFilesDir().getPath().toString() + "/SavedJobs.txt");
            FileWriter writer = new FileWriter(gpxfile,false);
            for (String s:sBody){
                writer.append(s);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
