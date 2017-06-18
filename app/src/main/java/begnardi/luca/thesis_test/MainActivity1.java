//package begnardi.luca.thesis_test;
//
//import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class MainActivity1 extends ActionBarActivity implements View.OnClickListener {
//
//    private LinearLayout list;
//    private FileManager fm;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        // create a File object for the parent directory
//        File dir = new File("/storage/sdcard0/Tesi");
//
//        // have the object build the directory structure, if needed.
//        dir.mkdir();
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main1);
//        RoundButton b = (RoundButton)findViewById(R.id.new_text);
//        b.setOnClickListener(this);
//        fm = new FileManager("/storage/sdcard0/Tesi/samples.csv");
//        list = (LinearLayout) findViewById(R.id.list);
//        showList(fm.fileRead());
//    }
//
//    public void showList(ArrayList<String> items) {
//        for(int i = items.size() - 1; i >= 0; i--) {
//
//            View mView = LayoutInflater.from(list.getContext()).inflate(R.layout.table_sample_item, list, false);
//
//            Sample s = new Sample();
//            s = s.fromCSV(items.get(i));
//
////            String rArray[] = items.get(i).split(",");
//
//            TextView text = (TextView) mView.findViewById(R.id.text_drunk);
////            if(rArray[8].equals("true"))
////                text.setText("Yes");
////            else
////                text.setText("No");
//            if(s.isDrunk())
//                text.setText("Yes");
//            else
//                text.setText("No");
//
//            text = (TextView) mView.findViewById(R.id.text_time);
////            text.setText(rArray[3] + "s");
//            text.setText(Double.toString(Utils.round(s.getTotalTime(), 2)) + "s");
//
//            text = (TextView) mView.findViewById(R.id.text_date);
////            text.setText(rArray[9]);
//            text.setText(s.getDate());
//
//            text = (TextView) mView.findViewById(R.id.text_ndel);
////            text.setText(rArray[0]);
//            text.setText(Integer.toString(s.getDelNum()));
//
//            text = (TextView) mView.findViewById(R.id.text_ncomp);
////            text.setText(rArray[2]);
//            text.setText(Integer.toString(s.getCompletNum()));
//
//            text = (TextView) mView.findViewById(R.id.text_ncorr);
////            text.setText(rArray[1]);
//            text.setText(Integer.toString(s.getCorrectNum()));
//
//            text = (TextView) mView.findViewById(R.id.text_cps);
////            text.setText(rArray[4]);
//            text.setText(Double.toString(Utils.round(s.getCharPerSec(), 2)));
//
//            text = (TextView) mView.findViewById(R.id.text_delps);
////            text.setText(rArray[5]);
//            text.setText(Double.toString(Utils.round(s.getDelPerSec(), 2)));
//
//            text = (TextView) mView.findViewById(R.id.text_compps);
////            text.setText(rArray[7]);
//            text.setText(Double.toString(Utils.round(s.getCompletPerSec(), 2)));
//
//            text = (TextView) mView.findViewById(R.id.text_corrps);
////            text.setText(rArray[6]);
//            text.setText(Double.toString(Utils.round(s.getCorrectPerSec(), 2)));
//
//            list.addView(mView);
//
//            mView = LayoutInflater.from(list.getContext()).inflate(R.layout.divider, list, false);
//            list.addView(mView);
//        }
//    }
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
//        switch(v.getId()) {
//            case R.id.new_text: {
//                Intent intent = new Intent(this, WritingActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 1)
//            recreate();
//    }
//}