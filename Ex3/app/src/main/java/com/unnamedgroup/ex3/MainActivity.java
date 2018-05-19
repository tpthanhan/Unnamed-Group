package com.unnamedgroup.ex3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Service.Job;
import Service.JobService;
import Service.SearchCriteria;


public class MainActivity extends AppCompatActivity {
    JobService jobService=new JobService();
    ProgressDialog pd;
    List<Job> jobList ;

    EditText jobName;
    EditText location;
    CheckBox fullTime;
    Button search;
    RecyclerView recyclerView;

    private SearchCriteria searchCriteria;

    List<String> savedJob;
    JobAdapter jobAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedJob = readFromFile(MainActivity.this);

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        //set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        jobName = findViewById(R.id.jobName);
        location = findViewById(R.id.location);
        fullTime = findViewById(R.id.fullTime);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = jobName.getText().toString();
                String loca = location.getText().toString();
                boolean ff = fullTime.isChecked();
                searchCriteria = new SearchCriteria(name,loca,ff);
                new JsonTask().execute();
            }
        });

    }

    //use for searching jobs
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            if (searchCriteria!=null) {
                try {
                    setJobList(jobService.searchCriteria(searchCriteria));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                setJobList(jobService.getAllJob());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            jobAdapter = new JobAdapter(MainActivity.this, jobList,savedJob);
            recyclerView.setAdapter(jobAdapter);
            savedJob=jobAdapter.getSavedJob();
            if (pd.isShowing()){
                pd.dismiss();
            }
            Toast.makeText(MainActivity.this,"Search Jobs Completed",Toast.LENGTH_LONG).show();
        }
    }




    public List<String> readFromFile(Context context){
        List<String> result=new ArrayList<>();
        String line;
        try{
            // FileReader reads text files in the default encoding.
            File tmpDir = new File(context.getFilesDir().getPath().toString() + "/SavedJobs.txt");
            if (tmpDir.exists()) {
                FileReader fileReader =
                        new FileReader(context.getFilesDir().getPath().toString() + "/SavedJobs.txt");

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {
                    result.add(line);
                }
                // Always close files.
                bufferedReader.close();
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            "SavedJobs" + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + "SavedJobs" + "'");
        }
        return result;
    }

    public JobService getJobService() {
        return jobService;
    }

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
