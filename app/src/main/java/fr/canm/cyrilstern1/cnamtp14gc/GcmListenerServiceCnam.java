package fr.canm.cyrilstern1.cnamtp14gc;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by cyrilstern1 on 18/06/2016.
 */

public class GcmListenerServiceCnam extends com.google.android.gms.gcm.GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Vibrator v = (Vibrator) this.getSystemService(getApplication().VIBRATOR_SERVICE);
        v.vibrate(300);

    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
    }

    public GcmListenerServiceCnam() {
        super();
    }
}
