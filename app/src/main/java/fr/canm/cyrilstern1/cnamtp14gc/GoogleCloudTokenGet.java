package fr.canm.cyrilstern1.cnamtp14gc;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;


public class GoogleCloudTokenGet extends IntentService{




    public GoogleCloudTokenGet() {
        super("GoogleCloudTokenGet");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        try {

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = instanceID.getToken(getString(R.string.SenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("blim",token);

        }catch (Exception e){

        }


    }


}
