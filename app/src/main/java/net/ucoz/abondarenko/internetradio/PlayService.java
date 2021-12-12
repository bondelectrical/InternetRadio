package net.ucoz.abondarenko.internetradio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;


public class PlayService extends Service {

    private static final String CHANNEL_ID = "Channel_id_01";
    private static final int notificationId = 1;

    private ExoPlayer player;

    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String dataStream = intent.getStringExtra("linkStation");
        String nameStation = intent.getStringExtra("nameStation");
        Log.d("MyTAG", dataStream);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intentAction = new Intent(this, MainActivity.class);
        intentAction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(nameStation)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setSound(null);

        Notification notification = builder.build();
        startForeground(1, notification);

        player = new ExoPlayer.Builder(getBaseContext())
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(getBaseContext()).setLiveTargetOffsetMs(5000))
                .build();
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(dataStream)
                .setLiveConfiguration(new MediaItem.LiveConfiguration.Builder()
                        .setMaxPlaybackSpeed(1.02f)
                        .build())
                .build();
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }

    }


}