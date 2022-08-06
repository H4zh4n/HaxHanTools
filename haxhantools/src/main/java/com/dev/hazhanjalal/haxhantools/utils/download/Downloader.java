package com.dev.hazhanjalal.haxhantools.utils.download;


import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.v;

import com.dev.hazhanjalal.haxhantools.utils.print.Logger;
import com.dev.hazhanjalal.haxhantools.utils.ui.HxProgress;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.dev.hazhanjalal.haxhantools.utils.utils.UtilsFile;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;

import java.util.Random;

public class Downloader {
    
    /**
     * Don't forget to add android:usesCleartextTraffic="true"
     * <application
     * ..
     * android:usesCleartextTraffic="true"
     * ..
     * >
     */
    
    //Incase you wanted to just call files by their name and download from a set Server change below.
    //public static String MAIN_DOWNLOAD_DOMAIN = Utils.disentangle(104, 116, 116, 112, 115, 58, 47, 47, 103, 105, 116, 104, 117, 98, 46, 99, 111, 109, 47, 72, 52, 122, 104, 52, 110, 47, 84, 97, 102, 115, 101, 101, 114, 105, 78, 111, 111, 114, 47, 114, 97, 119, 47, 102, 105, 108, 101, 115, 47);
    //dis: https://github.com/H4zh4n/TafseeriNoor/raw/files/
    
    static String default_progress_text = "Download in progress";
    
    /*public static long startDownload(String subfolder, String fileName, OnCustomDownload onCustomDownload) {
        return startDownload(MAIN_DOWNLOAD_DOMAIN + subfolder + "/" + fileName, subfolder, fileName, "", false, false, false, onCustomDownload);
    }
    
    public static long startDownload(String subfolder, String fileName, boolean haveNotification, OnCustomDownload onCustomDownload) {
        return startDownload(MAIN_DOWNLOAD_DOMAIN + subfolder + "/" + fileName, subfolder, fileName, "", haveNotification, false, false, onCustomDownload);
    }
    
    public static long startDownload(String subfolder, String fileName, String title, OnCustomDownload onCustomDownload) {
        return startDownload(MAIN_DOWNLOAD_DOMAIN + subfolder + "/" + fileName, subfolder, fileName, title, false, false, false, onCustomDownload);
    }
    
    public static long startDownload(String subfolder, String fileName, String title, boolean haveNotification, OnCustomDownload onCustomDownload) {
        return startDownload(MAIN_DOWNLOAD_DOMAIN + subfolder + "/" + fileName, subfolder, fileName, title, haveNotification, false, false, onCustomDownload);
    }*/
    
    
    public static long startDownload(final String DOWNLOAD_URL, String subfolder, final String fileName, boolean haveNotification, boolean haveProgressDialog, OnCustomDownload action) {
        return startDownload(DOWNLOAD_URL, subfolder, fileName, "", haveNotification, haveProgressDialog, false, action);
    }
    
    public static long startDownload(final String DOWNLOAD_URL, String subfolder, final String fileName, boolean haveNotification, boolean haveProgressDialog, boolean downloadEvenIfFileExists, OnCustomDownload onCustomDownload) {
        return startDownload(DOWNLOAD_URL, subfolder, fileName, "", haveNotification, haveProgressDialog, downloadEvenIfFileExists, onCustomDownload);
    }
    
    public static long startDownload(final String DOWNLOAD_URL, String subfolder, final String fileName, String title, OnCustomDownload action) {
        return startDownload(DOWNLOAD_URL, subfolder, fileName, title, false, false, false, action);
    }
    
    public static long startDownload(String DOWNLOAD_URL, String subfolder, String fileName, String title, boolean haveNotification, boolean haveProgressDialog, boolean downloadEvenIfFileExists, OnCustomDownload onCustomDownload) {
        
        DOWNLOAD_URL = Utils.replaceEasternNumbers(DOWNLOAD_URL);
        fileName = Utils.replaceEasternNumbers(fileName);
        subfolder = Utils.replaceEasternNumbers(subfolder);
        
        Logger.v("requested -> " + DOWNLOAD_URL);
        
        if (!downloadEvenIfFileExists) {
            
            if (UtilsFile.fileExist(subfolder, fileName)) {
                if (onCustomDownload != null) {
                    onCustomDownload.onDownloadComplete();
                }
                return -1;
            }
        }
        
        if (haveProgressDialog) {
            if (title != null && !title.isEmpty()) {
                Utils.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HxProgress.showProgressDialog(title);
                    }
                });
            } else {
                Utils.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HxProgress.showProgressDialog(default_progress_text);
                    }
                });
            }
            
            HxProgress.setProgressText(fileName);
        }
        
        CustomNotification notif = null;
        if (haveNotification) {
            notif = new CustomNotification(Utils.activeContext)
                    .setTitle(title)
                    .setContentText(default_progress_text)
                    .setSmallIconResource(Utils.getResourceIdByName("mipmap", "ic_launcher"))
                    .setNotification_ID(new Random().nextInt(1234567))
                    .setMaxProgress(100)
                    .sendNotification();
        } else {
            notif = null;
        }
        
        CustomNotification finalNotif = notif;
        
        long id =
                PRDownloader
                        .download(DOWNLOAD_URL,
                                  UtilsFile.getFolderFullPath(subfolder).getAbsolutePath(),
                                  fileName)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                if (onCustomDownload != null) {
                                    onCustomDownload.onStartOrResume();
                                }
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                v("Downloader onPause()");
                                
                                if (onCustomDownload != null) {
                                    onCustomDownload.onPause();
                                }
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                v("Downloader onCancel()");
                                
                                if (onCustomDownload != null) {
                                    onCustomDownload.onCancel();
                                }
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            int prevProgress = 0;
                            
                            @Override
                            public void onProgress(Progress progress) {
                                if (onCustomDownload != null) {
                                    onCustomDownload.onProgress(progress);
                                }
                                
                                int currentProgress = (int) (((double) progress.currentBytes / progress.totalBytes) * 100);
                                
                                if (haveProgressDialog) {
                                    
                                    //For lag issues. sometimes the file is 1_000_000 and it might update progress that many times,
                                    //this way max update is 100 times.
                                    if (prevProgress != currentProgress) {
                                        prevProgress = currentProgress;
                                        
                                        Utils.getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                HxProgress.setProgressText(currentProgress + "/100"/* + progress.totalBytes*/);
                                            }
                                        });
                                    }
                                }
                                
                                if (haveNotification && finalNotif != null) {
                                    if (prevProgress != currentProgress) {
                                        prevProgress = currentProgress;
                                        finalNotif.updateProgress(currentProgress);
                                    }
                                }
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                if (onCustomDownload != null) {
                                    onCustomDownload.onDownloadComplete();
                                }
                                
                                if (haveProgressDialog) {
                                    Utils.getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HxProgress.closeProgressDialog();
                                        }
                                    });
                                }
                                
                                if (haveNotification && finalNotif != null) {
                                    finalNotif.finishNotification(true);
                                }
                                
                            }
                            
                            @Override
                            public void onError(Error error) {
                                //   v("Downloader onError()");
                                // Logger.v(error.getServerErrorMessage());
                                
                                if (haveProgressDialog) {
                                    HxProgress.closeProgressDialog();
                                }
                                
                                if (haveNotification && finalNotif != null) {
                                    finalNotif.finishNotification(false);
                                }
                                
                                if (onCustomDownload != null) {
                                    try {
                                        onCustomDownload.onError(error);
                                        v("error.getServerErrorMessage()");
                                    } catch (Exception e) {
                                    
                                    }
                                }
                            }
                            
                        });
        
        return id;
    }
    
}