package com.dev.hazhanjalal.haxhantools.utils.download;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;


public class CustomNotification {
    private int DOWNLOAD_NOTIFICATION_ID = -1;
    private String title = "";
    private String contentText = "";
    private boolean isOngoing = true;
    private Context context = null;
    private NotificationManager notificationManager;
    private NotificationManagerCompat compactNotificationManager;
    private int maxProgress = 0;
    private Bitmap largeIcon = null;
    private int color = 0;
    private int smallIconResource = 0;
    private NotificationCompat.Builder notiBuilder;
    
    public CustomNotification(Context context) {
        this.context = context;
    }
    
    public CustomNotification(Context ctx, String title, boolean isOngoing, int maxProgress, int ID) {
        this.DOWNLOAD_NOTIFICATION_ID = ID;
        this.title = title;
        this.context = ctx;
        this.maxProgress = maxProgress;
        this.isOngoing = isOngoing;
        sendNotification();
    }
    
    public CustomNotification(String title, boolean isOngoing, int maxProgress, int ID) {
        this.DOWNLOAD_NOTIFICATION_ID = ID;
        this.title = title;
        this.context = Utils.activeContext;
        this.maxProgress = maxProgress;
        this.isOngoing = isOngoing;
        sendNotification();
    }
    
    public CustomNotification(String title, int maxProgress, int ID) {
        this.DOWNLOAD_NOTIFICATION_ID = ID;
        this.title = title;
        this.context = Utils.activeContext;
        this.maxProgress = maxProgress;
        this.isOngoing = true;
        sendNotification();
    }
    
    public CustomNotification(Context ctx, String title, int maxProgress, int ID) {
        this.DOWNLOAD_NOTIFICATION_ID = ID;
        this.title = title;
        this.context = ctx;
        this.maxProgress = maxProgress;
        this.isOngoing = true;
        sendNotification();
    }
    
    public CustomNotification setTitle(String title) {
        this.title = title;
        
        if (notiBuilder != null) {
            notiBuilder.setContentTitle(title);
        }
        
        return this;
    }
    
    public CustomNotification setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }
    
    public CustomNotification setOngoing(boolean ongoing) {
        isOngoing = ongoing;
        return this;
    }
    
    public CustomNotification setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }
    
    public CustomNotification setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
        return this;
    }
    
    public CustomNotification setColor(int color) {
        this.color = color;
        return this;
    }
    
    public CustomNotification setSmallIconResource(int smallIconResource) {
        this.smallIconResource = smallIconResource;
        return this;
    }
    
    public CustomNotification setNotification_ID(int DOWNLOAD_NOTIFICATION_ID) {
        this.DOWNLOAD_NOTIFICATION_ID = DOWNLOAD_NOTIFICATION_ID;
        return this;
    }
    
    public CustomNotification sendNotification() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        } /*else {
            compactNotificationManager = NotificationManagerCompat.from(context);
        }*/
        
        //this is for settings notification [one more same as this is needed in createNotificationChannel() ] - they should be same ID.
        notiBuilder = new NotificationCompat.Builder(context, "download_manager_31415");
        
        if (contentText == null || contentText.isEmpty()) {
            contentText = Downloader.default_progress_text;
        }
        
        if (smallIconResource == 0) {
            smallIconResource = R.drawable.ic_error;
        }
        
        if (largeIcon == null) {
            // largeIcon = Utils.drawableToBitmap(Utils.getDrawable(R.mipmap.ic_launcher));
        }
        
        if (color == 0) {
            color = Utils.getColor(R.color.white);
        }
        
        notiBuilder.setColor(color);
        
        notiBuilder.setVibrate(new long[]{0});
        
        notiBuilder.setContentTitle(title)
                .setContentText(contentText)
                // below is for in-case download description is long, can have same for title.
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                //LOW for no vibration
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(isOngoing)
                .setAutoCancel(false)
                .setLargeIcon(largeIcon)
                .setProgress(maxProgress, 0, false)
                .build();
    
        try {
            notiBuilder.setSmallIcon(smallIconResource);
        } catch (Exception e) {
        
        }
    
        notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
        return this;
    }
    
    public void finishNotification(boolean success) {
        notiBuilder.setOngoing(false);
        notiBuilder.setProgress(100, 100, false);
        notiBuilder.setAutoCancel(true);
        
        if (success) {
            notiBuilder.setContentText("بە سەركەوتووی داگیرا");
            notiBuilder.setColor(Utils.getColor(R.color.colorGreenChosen));
        } else {
            notiBuilder.setContentText("نەتوانرا داگیرێت");
            notiBuilder.setColor(Utils.getColor(R.color.colorRedChosen));
        }
    
    
        
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
            //notificationManager.cancel(DOWNLOAD_NOTIFICATION_ID);
        } else {
            compactNotificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
            //compactNotificationManager.cancel(DOWNLOAD_NOTIFICATION_ID);
        }*/
        
        //This ID is for deciding if the notification should be separated or not. each ID sent is for a separate notification
        // eg./ each surah download has a separate download notification.
        notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
    }
    
    public void updateProgress(final int max, final int percentage) {
        Utils.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notiBuilder.setProgress(max, percentage, false);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
                } else {
                    compactNotificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
                }*/
                notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, notiBuilder.build());
                
            }
        });
    }
    
    public void updateProgress(int percentage) {
        updateProgress(maxProgress, percentage);
    }
    
    private void createNotificationChannel() {
        
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            
            //this is for settings notification. should be same as notiBuilder = new NotificationCompat.Builder(..., id)
            // to have one unified Id for the download job.
            NotificationChannel channel = new NotificationChannel("download_manager_31415", "داگرتن",
                                                                  NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("تایبەت بە داگرتنی فایلەكان");
            
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
    }
    
}
