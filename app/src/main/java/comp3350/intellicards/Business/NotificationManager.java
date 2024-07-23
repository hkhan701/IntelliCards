package comp3350.intellicards.Business;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Presentation.AuthActivity;
import comp3350.intellicards.Presentation.FlashcardSetActivity;
import comp3350.intellicards.Presentation.Utils.NotificationReceiver;
import comp3350.intellicards.R;

public class NotificationManager {

    private static final String CHANNEL_ID = "flashcard_reminder_channel";
    private static final String CHANNEL_NAME = "Flashcard Reminders";
    private static final String CHANNEL_DESCRIPTION = "Notifications for flashcard set reminders";
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
    public static final int EXACT_ALARM_PERMISSION_REQUEST_CODE = 1002;

    private final Context context;
    private final AlarmManager alarmManager;

    public NotificationManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        createNotificationChannel();
    }

    // Create the notification channel
    // This method is called in the constructor to create the notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Check if the device is running Android Oreo or higher
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, android.app.NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.enableVibration(true);

            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleNotification(String flashcardSetUUID, long timestamp) {
        if (canScheduleExactAlarms()) {
            PendingIntent pendingIntent = createPendingIntent(flashcardSetUUID);
            try {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
                Toast.makeText(context, "Reminder set successfully", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                Toast.makeText(context, "Permission to set exact alarms is required", Toast.LENGTH_SHORT).show();
            }
        } else {
            requestExactAlarmPermission();
        }
    }

    public boolean canScheduleExactAlarms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Check if the device is running Android 12 or higher
            return alarmManager.canScheduleExactAlarms();
        }
        return true;
    }

    public void requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Check if the device is running Android 12 or higher
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, EXACT_ALARM_PERMISSION_REQUEST_CODE);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    public boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Check if the device is running Android 13 or higher
            return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public void requestNotificationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Check if the device is running Android 13 or higher
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void showDateTimePicker(Activity activity, String flashcardSetUUID) {
        if (!canScheduleExactAlarms()) {
            requestExactAlarmPermission();
            return;
        }

        Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog(activity, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(activity, (view1, hourOfDay, minute) -> {
                selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDate.set(Calendar.MINUTE, minute);
                scheduleNotification(flashcardSetUUID, selectedDate.getTimeInMillis());
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private PendingIntent createPendingIntent(String flashcardSetUUID) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        intent.putExtra("username", UserSession.getInstance(context).getUsername());
        return PendingIntent.getBroadcast(context, flashcardSetUUID.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
    public static void showNotification(Context context, String flashcardSetUUID, String username) {
        String flashcardSetName = getFlashcardSetName(flashcardSetUUID);
        if (flashcardSetName == null) {
            return; // Handle the case where the flashcard set is not found or possibly deleted
        }

        String title = "Flashcard Study Reminder";
        String message =  username + "! It's time to review your flashcard set: " + flashcardSetName;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.auth_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        Intent resultIntent = null;

        // if user is not logged in, or if user is logged in but is not the owner of the flashcard set, navigate to the AuthActivity
        if (UserSession.getInstance(context).getUsername() == null) {
            // User is not logged in, navigate to the AuthActivity
            resultIntent = new Intent(context, AuthActivity.class);
        } else {
            // Current user is logged in, navigate to the FlashcardSetActivity
            resultIntent = new Intent(context, FlashcardSetActivity.class);
            resultIntent.putExtra("flashcardSetUUID", flashcardSetUUID);
            resultIntent.putExtra("username", username);
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, flashcardSetUUID.hashCode(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(flashcardSetUUID.hashCode(), builder.build());
        }
    }


    private static String getFlashcardSetName(String flashcardSetUUID) {
        FlashcardSetManager flashcardSetManager = new FlashcardSetManager();
        FlashcardSet flashcardSet = flashcardSetManager.getFlashcardSet(flashcardSetUUID);
        if (flashcardSet == null) {
            return null;
        }
        return flashcardSet.getFlashcardSetName();
    }
}