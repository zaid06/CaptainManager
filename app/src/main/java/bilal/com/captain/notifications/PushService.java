package bilal.com.captain.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.Util.SaveInSharedPreference;
import bilal.com.captain.chatActivity.StartOneToOneChatting;
import bilal.com.captain.models.NotificationModel;

/**
 * Created by ikodePC-1 on 1/26/2018.
 */

public class PushService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseDatabase.getInstance().getReference().child("ChattingNotification").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    NotificationModel notificationModel = dataSnapshot.getValue(NotificationModel.class);
                    if(! (SaveInSharedPreference.getInSharedPreference(getApplicationContext()).getNotification(notificationModel.getPushkey()))){
                        SaveInSharedPreference.getInSharedPreference(getApplicationContext()).setNotification(notificationModel.getPushkey());
                        notif(notificationModel);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notif(NotificationModel notificationModel) {

        GlobalVariables.notificationModel = notificationModel;

        Intent intent = new Intent(this, StartOneToOneChatting.class);

        intent.putExtra("uid",notificationModel.getUid());

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {

            //view of notification
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notification = new Notification.Builder(this)
                    .setTicker("University")
                    .setContentTitle(notificationModel.getName())
                    .setContentText(notificationModel.getMessage())
                    .setTicker("Notification form my app")
                    .setSmallIcon(R.drawable.chat)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{500, 500})
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int randomNumber = random.nextInt(9999 - 1000) + 1000;
            notificationManager.notify(randomNumber, notification.build());
        }
    }


}
