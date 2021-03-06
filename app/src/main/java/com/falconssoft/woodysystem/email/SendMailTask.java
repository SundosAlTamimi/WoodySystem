package com.falconssoft.woodysystem.email;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.falconssoft.woodysystem.SettingsFile;
import com.falconssoft.woodysystem.reports.AcceptanceReport;
import com.falconssoft.woodysystem.stage_one.AddNewRaw;

import java.util.Collections;
import java.util.List;

public class SendMailTask extends AsyncTask {

      private ProgressDialog statusDialog;
    private Context sendMailActivity;
    String path;
    int flag;

    public SendMailTask(Context activity, String path,int flag) {
        sendMailActivity = activity;
        this.path=path;
        this.flag=flag;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            Log.i("SendMailTask", "About to instantiate GMail...");
            publishProgress("Processing input....");
            GMail androidEmail = new GMail(args[0].toString()
                    , args[1].toString()
                    , Collections.singletonList(args[2].toString())
                    , args[3].toString()
                    ,args[4].toString()
                 , (List<String>) args[5]);
            publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
            publishProgress("Sending email....");
            androidEmail.sendEmail();
            publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
      //  sendMailActivity.clear();

        if(flag==0){//save
            AcceptanceReport acceptanceReport=(AcceptanceReport) sendMailActivity;
            acceptanceReport.deleteTempFolder(path);
            statusDialog.dismiss();
        }else if(flag==1){
            AddNewRaw addNewRaw=(AddNewRaw) sendMailActivity;
            addNewRaw.clear();
            addNewRaw.deleteTempFolder(path);
            statusDialog.dismiss();

        }

    }

}
