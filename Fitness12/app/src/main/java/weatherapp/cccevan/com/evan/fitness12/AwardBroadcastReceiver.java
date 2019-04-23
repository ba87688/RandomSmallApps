package weatherapp.cccevan.com.evan.fitness12;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class AwardBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //set a  vibrator to vibrate each 2 seconds.
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        //set notification to user that they passed 1000 new steps
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Congrats!")
                .setContentText("You  done over 1000 new steps!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0,notification);
    }
}
