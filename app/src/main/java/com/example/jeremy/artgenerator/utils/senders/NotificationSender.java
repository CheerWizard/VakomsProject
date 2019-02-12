package com.example.jeremy.artgenerator.utils.senders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.services.SampleDownloaderService;
import com.example.jeremy.artgenerator.ui.GlobalPadApplication;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationSender {
    //notification manager
    private static NotificationManager notificationManager = (NotificationManager) GlobalPadApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);

    //download notifications
    public static class Download {
        public static void sendNotification(PendingIntent pendingIntent , Sample sample) throws PendingIntent.CanceledException {
            //notification builder init
            Notification.Builder builder = new Notification.Builder(GlobalPadApplication.getInstance());
            //modify pending intent for callback
            pendingIntent.send(SampleDownloaderService.getInstance() , ProcessStates.Successful.STATUS_DOWNLOAD_FINISH , new Intent()
                    .putExtra(GlobalPadApplication.getInstance().getResources().getString(R.string.name) , sample.getSampleName())
                    .putExtra(GlobalPadApplication.getInstance().getResources().getString(R.string.priority) , sample.getSamplePriority()));
            //set all parameters
            builder.setAutoCancel(true);
            builder.setTicker(GlobalPadApplication.getInstance().getResources().getString(R.string.download_status_bar));
            builder.setContentTitle(GlobalPadApplication.getInstance().getResources().getString(R.string.download_finished));
            builder.setContentText(GlobalPadApplication.getInstance().getResources().getString(R.string.download_finished));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setNumber(1);
            builder.build();
            //add to manager
            notificationManager.notify(1 , builder.getNotification());
        }

        public static void sendError(PendingIntent pendingIntent , int processState) throws PendingIntent.CanceledException {
            //notification builder init
            Notification.Builder builder = new Notification.Builder(GlobalPadApplication.getInstance());
            //modify pending intent for callback
            pendingIntent.send(processState);
            //set all parameters
            builder.setAutoCancel(true);
            builder.setTicker(GlobalPadApplication.getInstance().getResources().getString(R.string.download_status_bar));
            builder.setContentTitle(GlobalPadApplication.getInstance().getResources().getString(R.string.download_failed));
            builder.setContentText(GlobalPadApplication.getInstance().getResources().getString(R.string.download_failed));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setNumber(1);
            builder.build();
            //add to manager
            notificationManager.notify(2 , builder.getNotification());
        }
    }

    //upload notifications
    public static class Upload {
        public static void sendNotification(PendingIntent pendingIntent , Sample sample) throws PendingIntent.CanceledException {
            //notification builder init
            Notification.Builder builder = new Notification.Builder(GlobalPadApplication.getInstance());
            //modify pending intent for callback
            pendingIntent.send(SampleDownloaderService.getInstance() , ProcessStates.Successful.STATUS_UPLOAD_FINISH , new Intent()
                    .putExtra(GlobalPadApplication.getInstance().getResources().getString(R.string.name) , sample.getSampleName())
                    .putExtra(GlobalPadApplication.getInstance().getResources().getString(R.string.priority) , sample.getSamplePriority()));
            //set all parameters
            builder.setAutoCancel(true);
            builder.setTicker(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_status_bar));
            builder.setContentTitle(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_finished));
            builder.setContentText(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_finished));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setNumber(1);
            builder.build();
            //add to manager
            notificationManager.notify(1 , builder.getNotification());
        }

        public static void sendError(PendingIntent pendingIntent , int processState) throws PendingIntent.CanceledException {
            //notification builder init
            Notification.Builder builder = new Notification.Builder(GlobalPadApplication.getInstance());
            //modify pending intent for callback
            pendingIntent.send(processState);
            //set all parameters
            builder.setAutoCancel(true);
            builder.setTicker(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_status_bar));
            builder.setContentTitle(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_failed));
            builder.setContentText(GlobalPadApplication.getInstance().getResources().getString(R.string.upload_failed));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setNumber(1);
            builder.build();
            //add to manager
            notificationManager.notify(2 , builder.getNotification());
        }
    }

}
