package fr.canm.cyrilstern1.cnamtp14gc;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSend;
    private EditText editTexttoSend;
    private ListView listView;
    private ConstraintLayout frameLayout;
    private CustomBroadcast customBroadcast;
    private CustomArrayAdaptor customArrayAdaptor;
    private ArrayList<RowItem> arraylistItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        this.arraylistItem = new ArrayList<>();
        this.customArrayAdaptor = new CustomArrayAdaptor(getApplicationContext(),arraylistItem);
        Intent intent = new Intent();
        //intent.setAction(A)
        init();

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    private final String textSend = "taper votre message";

    private void init(){

        this.buttonSend = (Button) findViewById(R.id.button3);
        this.buttonSend.setOnClickListener(this);
        this.editTexttoSend = (EditText) findViewById(R.id.editText3);
        this.listView = (ListView) findViewById(R.id.listView);
        this.frameLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        this.frameLayout.setBackground(getDrawable(R.drawable.radialgradientback));
        this.listRegIds = new GCMListRegIds(this, GCMListRegIds.LIST_STERN);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3 :
                GetListRegIdsTask task = new GetListRegIdsTask();
                String message = editTexttoSend.getText().toString();
                Intent intent = new Intent(this, GoogleCloudTokenGet.class);
                startService(intent);
                SendMessageToCloudTask sendMessageToCloudTask = new SendMessageToCloudTask(message);
                sendMessageToCloudTask.execute();
                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                addToList(new RowItem(editTexttoSend.getText().toString()));

        }

    }
    private void addToList(RowItem rowitel){
        ArrayList<RowItem> arraylistItem = new ArrayList<>();
        arraylistItem.add(rowitel);
        Log.i("ghjgjh",rowitel.getCourseRow());

        listView.setAdapter(customArrayAdaptor);

    }
    private class SendMessageToCloudTask extends AsyncTask<String, Void, Exception> {
        private String message;

        public SendMessageToCloudTask(String message) {
            this.message = message;
        }

        protected Exception doInBackground(String... params) {
           /* final com.google.android.gcm.server.Message msg = new com.google.android.gcm.server.Message.Builder()
                    //.collapseKey("1") // avec la meme cl , le nouveau remplace l'ancien pour le meme utilisateur
                    //.timeToLive(30)  // le message est conserve  30 secondes, ne rien mettre, il est conserve  4 semaines
                    .timeToLive(60 * 60 * 24) // 24 heures
                    //.timeToLive(120)
                    //.timeToLive(0)  // maintenant ou jamais
                    .delayWhileIdle(true)
                    .addData("message", message)
                    .build();
            final List<String> abonnes = listRegIds.regIds();
            Exception cause = null;
            try {
                if(internet() && abonnes.size()>=0) {
                    MulticastResult result = sender.send(msg, abonnes, 15);
                }
            } catch (Exception e) {
                e.printStackTrace();
                cause = e;
            }*/
            return null;//cause;
        }
        @Override
        protected void onPostExecute(Exception e){
           // if(e!=null)tv.setText("Exception: " + e.getMessage());
        }
    }
    public  boolean internet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !(networkInfo.isConnected())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Internet");
            alertDialog.setMessage("Vérifiez votre connexion !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    });
            alertDialog.show();
            return false;
        }else
            return true;
    }
    private final String TAG = this.getClass().getSimpleName();
    private final static boolean I = true;

    // une sortie console minimaliste
    private TextView tv;
    // le message à envoyer
    private EditText et;
    // La liste des abonnés
    private GCMListRegIds listRegIds;
    // Envoi vers le cloud
   // private Sender sender;

    private class GetListRegIdsTask extends AsyncTask<Void, String, Void> {
        private String message;
        protected Void doInBackground(Void... params) {
            int number = listRegIds.size();
            String str = number + " subscriber" + (number>1?"s":"") + ", " + listRegIds.getName()+ "\n";

            for (String regId : listRegIds.regIds()) {  // tous les regids
                if (I) Log.i(TAG, regId);
                if(regId.startsWith("APA91")&&regId.length()>20)
                    str = str + regId.substring(0, 20) + "..." + regId.substring(regId.length()-5, regId.length()) +"\n";
                else
                    publishProgress("Regid non conforme ?!: " + regId);
            }
            publishProgress(str);
            return null;
        }
        @Override
        public void onProgressUpdate(String... values) {

//            tv.setText(values[0]);
        }

    }



}
