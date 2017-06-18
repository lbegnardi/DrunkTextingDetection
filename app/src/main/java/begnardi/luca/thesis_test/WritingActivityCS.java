//package begnardi.luca.thesis_test;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.Random;
//
//public class WritingActivity extends ActionBarActivity implements View.OnClickListener {
//
//    private MyEditText text;
//    private Sample s;
//    //private EditText text;
//    private double startTime;
//    private MyTextWatcher TW;
//    private FileManager fm;
//    private FileManager fmText;
//    private String finalText;
//    private ConnectionManager connection;
//    private ProgressDialog progress;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_writing);
//        progress = null;
//        s = new Sample();
//        text = (MyEditText) findViewById(R.id.text);
//        TextView message = (TextView) findViewById(R.id.message);
//        message.setText(getResources().getStringArray(R.array.messages)[new Random().nextInt(4)]);
//        //text = (EditText) findViewById(R.id.text);
//        TW = new MyTextWatcher(text);
//        text.addTextChangedListener(TW);
//        text.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                boolean b = (keyCode == KeyEvent.KEYCODE_DEL);
//                if (b)
//                    System.out.println("TRUE");
//                else
//                    System.out.println("FALSE");
//                return b;
//            }
//        });
//        Button back = (Button) findViewById(R.id.button_annulla);
//        back.setOnClickListener(this);
//        Button ok = (Button) findViewById(R.id.button_ok);
//        ok.setOnClickListener(this);
//        startTime = System.nanoTime();
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button_annulla: {
//                new AlertDialog.Builder(this)
//                        .setIconAttribute(android.R.attr.alertDialogIcon)
//                        .setTitle("Annulla")
//                        .setMessage("Sei sicuro di voler uscire?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //do nothing
//                            }
//                        })
//                        .show();
//            }
//            break;
//            case R.id.button_ok: {
//                finalText = text.getText().toString();
//                fm = new FileManager("/storage/sdcard0/Tesi/samples.csv");
//                fmText = new FileManager("/storage/sdcard0/Tesi/" + s.getDateForFile() + ".txt");
//                s.setTotalTime(Utils.round(Utils.secondFromNano(System.nanoTime() - startTime), 2));
//                s.setCharNum(TW.getCharCount());
//                s.setDelNum(TW.getBackCount());
//                s.setCorrectNum(TW.getCorrection());
//                s.setCompletNum(TW.getCompletion());
//                s.calcCharPerSec();
//                s.calcBackspPerSec();
//                s.calcCorrectPerSec();
//                s.calcCompletPerSec();
//                final AlertDialog.Builder endDialog = new AlertDialog.Builder(this)
//                        .setTitle("Grazie!")
//                        .setMessage("Grazie per aver contribuito.\nOra puoi tornare a bere!")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //salva in locale
//                                fm.fileWrite(s);
//                                fmText.fileWrite(finalText);
//                                finish();
//                            }
//                        })
//                        .setCancelable(false);
//                new AlertDialog.Builder(this)
//                        .setTitle("Quasi finito...")
//                        .setMessage("Pensi di essere ubriaco?")
//                        .setPositiveButton("Sì", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                s.setDrunk(true);
//                                progress = ProgressDialog.show(WritingActivity.this, "",
//                                        "Loading. Please wait...", true);
//                                new AsyncAction().execute();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                s.setDrunk(false);
//                                progress = ProgressDialog.show(WritingActivity.this, "",
//                                        "Loading. Please wait...", true);
//                                new AsyncAction().execute();
//                                System.out.println(s.toJson().toString());
//                            }
//                        })
//                        .setCancelable(false)
//                        .show();
//            }
//        }
//    }
//
//    private class AsyncAction extends AsyncTask<String, Void, String> {
//        protected String doInBackground(String... args) {
//            connection = new ConnectionManager();
//            return connection.sendSample(getResources().getString(R.string.address), s.toJson().toString());
//        }
//
//        protected void onPostExecute(final String response) {
//            String message = "Errore di comunicazione col server, per favore riprova.";
//            if(response != null) {
//                System.out.println(response);
//                if (response.equals("sober"))
//                    message = "Complimenti, sei sobrio!";
//                if (response.equals("drunk"))
//                    message = "Sei ubriaco, per il bene tuo e degli altri non metterti alla guida!";
//            }
//
//            if(progress != null)
//                progress.dismiss();
//            new AlertDialog.Builder(WritingActivity.this)
//                    .setTitle("Risultato")
//                    .setMessage(message)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            //salva in locale
//                            if(response != null) {
//                                fm.fileWrite(s);
//                                fmText.fileWrite(finalText);
//                            }
//                            finish();
//                        }
//                    })
//                    .setCancelable(false)
//                    .show();
//        }
//    }
//
//}