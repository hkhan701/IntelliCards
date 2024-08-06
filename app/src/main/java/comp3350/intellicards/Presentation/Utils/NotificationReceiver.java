package comp3350.intellicards.Presentation.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import comp3350.intellicards.Presentation.NotificationManager;

// This essentially 'catches' broadcasts from the AlarmManager
// and uses the NotificationManager to show a notification
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String flashcardSetUUID = intent.getStringExtra("flashcardSetUUID");
        String username = intent.getStringExtra("username");
        NotificationManager.showNotification(context, flashcardSetUUID, username);
    }
}
