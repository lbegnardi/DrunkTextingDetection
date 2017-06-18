package begnardi.luca.thesis_test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.ml.CvSVM;

import java.util.Random;

public class WritingActivity extends ActionBarActivity implements View.OnClickListener {

    private MyEditText text;
    private Sample s;
    //private EditText text;
    private double startTime;
    private MyTextWatcher TW;
    private FileManager fm;
    private FileManager fmText;
    private String finalText;
    private ConnectionManager connection;
    private ProgressDialog progress;

    //load the opencv library
    static {
        System.loadLibrary("opencv_java");
        if (!OpenCVLoader.initDebug()) {
            System.out.println("Not yet initialized");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        progress = null;

        s = new Sample();

        text = (MyEditText) findViewById(R.id.text);

        //select a random question for the user and show it
        TextView message = (TextView) findViewById(R.id.message);
        message.setText(getResources().getStringArray(R.array.messages)[new Random().nextInt(4)]);

        //set a modified text watcher to record when the user presses the DELETE key while writing
        TW = new MyTextWatcher(text);
        text.addTextChangedListener(TW);
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_DEL;
            }
        });

        //set UI buttons
        Button back = (Button) findViewById(R.id.button_annulla);
        back.setOnClickListener(this);
        Button ok = (Button) findViewById(R.id.button_ok);
        ok.setOnClickListener(this);

        //start the test's timer
        startTime = System.nanoTime();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_annulla: {

                //advert dialog to make sure that the user really wants to quit the current test
                new AlertDialog.Builder(this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle("Back")
                        .setMessage("Are you sure you want to quit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        })
                        .show();
            }
            break;
            case R.id.button_ok: {

                //not necessary: I used it while acquiring data from drunk people because, clearly, they wrote a lot of nonsense stuff and it was quite funny to read it
                finalText = text.getText().toString();
                fmText = new FileManager(Environment.getExternalStorageDirectory().getPath()+"/DTR/" + s.getDateForFile() + ".txt");

                //get the required data to feed the classifier with
                s.setTotalTime(Utils.round(Utils.secondFromNano(System.nanoTime() - startTime), 2));
                s.setCharNum(TW.getCharCount());
                s.setDelNum(TW.getBackCount());
                s.setCorrectNum(TW.getCorrection());
                s.setCompletNum(TW.getCompletion());
                s.calcCharPerSec();
                s.calcBackspPerSec();
                s.calcCorrectPerSec();
                s.calcCompletPerSec();

                //open the sample storage file
                fm = new FileManager(Environment.getExternalStorageDirectory().getPath()+"/DTR/samples.csv");

                //advert dialog used while acquiring data
                final AlertDialog.Builder endDialog = new AlertDialog.Builder(this)
                        .setTitle("Thank you!")
                        .setMessage("Thank you for your contribution.\n")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //save the sample
                                fm.fileWrite(s);
                                fmText.fileWrite(finalText); //optional, see above
                                finish();
                            }
                        })
                        .setCancelable(false);

                //advert dialog to get user's feeling about his/her condition
                new AlertDialog.Builder(this)
                        .setTitle("Almost done...")
                        .setMessage("Do you think you're drunk?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                s.setDrunk(true);
                                progress = ProgressDialog.show(WritingActivity.this, "",
                                        "Loading. Please wait...", true);
                                new AsyncAction().execute();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                s.setDrunk(false);
                                progress = ProgressDialog.show(WritingActivity.this, "",
                                        "Loading. Please wait...", true);
                                //start a parallel thread to calculate the classifier result
                                new AsyncAction().execute();
                            }
                        })
                        .show();
            }
        }
    }

    private class AsyncAction extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... args) {
//            connection = new ConnectionManager();
//            return connection.sendSample(getResources().getString(R.string.address), s.toJson().toString());
            //instantiate the classifier and load it with the already trained one supplied as an xml file
            CvSVM svm = new CvSVM();
            svm.load(Environment.getExternalStorageDirectory().getPath()+"/DTR/svm.xml");

            //classify the acquired sample
            if(svm.predict(s.toMat()) == 1)
                return "drunk";
            else
                return "sober";
        }

        protected void onPostExecute(final String response) {
            String message = "Communication error encountered with the server, please try again.";

            if(response != null) {
                System.out.println(response);
                if (response.equals("sober"))
                    message = "Congratulations, you are sober!";
                if (response.equals("drunk"))
                    message = "You really drank too much, for your own and others' sake do not drive!";
            }

            if(progress != null)
                progress.dismiss();

            //alert dialog to show the result or eventually some error message
            new AlertDialog.Builder(WritingActivity.this)
                    .setTitle("Result")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //if everything was fine, save the sample
                            if(response != null) {
                                fm.fileWrite(s);
                                fmText.fileWrite(finalText); //optional, see above
                            }
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }
}