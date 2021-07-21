package com.orem.bashhub.fcm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.activity.SplashActivity;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.PrefStore;
import com.orem.bashhub.utils.Utils;


/**
 * Created by Dawinder on 09/08/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Utils.showLog("Token Service : " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {

            Utils.showLog("Data : " + remoteMessage.getData());
            String type = remoteMessage.getData().get("type") != null ? remoteMessage.getData().get("type") : "";
            String id = remoteMessage.getData().get("bash_id") != null ? remoteMessage.getData().get("bash_id") : "";
            String amount = remoteMessage.getData().get("amount") != null ? remoteMessage.getData().get("amount") : "";
            String order_id = remoteMessage.getData().get("order_id") != null ? remoteMessage.getData().get("order_id") : "";
            String bartender_id = remoteMessage.getData().get("bartender_id") != null ? remoteMessage.getData().get("bartender_id") : "";
//            if (type.equalsIgnoreCase("101")) {
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), id, type, amount, bartender_id, order_id);

        } else {
            if (remoteMessage.getNotification() != null) {
                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "", "", "","","");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String title, String message, String id, String type, String amount, String bartender_id, String order_id) {
        int requestCode = Utils.getUniqueNumber();
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, Const.isLoggedIn(this) ? MainActivity.class : SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Const.NOTI_ID, id);
        intent.putExtra(Const.NOTI_TYPE, type);
        intent.putExtra("notify_id", "");
        intent.putExtra("amount", amount);
        intent.putExtra("bartender_id", bartender_id);
        intent.putExtra("order_id", order_id);
        pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = getString(R.string.app_name) + "" + System.currentTimeMillis();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, getString(R.string.app_name));
        nb.setSmallIcon(R.drawable.app_icon_new);
        nb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon_new));
        nb.setContentTitle(title);
//        nb.setContentText(message);
        nb.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        nb.setAutoCancel(true);
        nb.setSound(defaultSoundUri);
        nb.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000});
        nb.setLights(Color.GREEN, 3000, 3000);
        nb.setDefaults(Notification.DEFAULT_ALL);
        nb.setPriority(Notification.PRIORITY_HIGH);
        nb.setContentIntent(pendingIntent);
        nb.setChannelId(CHANNEL_ID);

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        assert mNotificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mChannel.setShowBadge(false);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        try {
            mNotificationManager.notify(requestCode, nb.build());
        } catch (Exception e) {
            Utils.showLog("Not able to show notification : " + e.getMessage());
        }
    }
}