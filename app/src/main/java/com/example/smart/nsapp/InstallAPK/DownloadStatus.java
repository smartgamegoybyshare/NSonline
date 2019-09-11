package com.example.smart.nsapp.InstallAPK;

public class DownloadStatus {

    private FinishListener finishListener;

    public void setListener(FinishListener mFinishListener){
        finishListener = mFinishListener;
    }

    public void openAPK(String uri, String type){
        if(finishListener != null && uri != null && type != null){
            finishListener.isFinsish(uri, type);
        }
    }
}
