package begnardi.luca.thesis_test;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by begno on 29/07/15.
 */
public class MyTextWatcher implements TextWatcher {

    private MyEditText editText;
//    private String oldText;
    private int charCount;
    private int backCount;
    private int correction;
    private int completion;

    //constructor
    public MyTextWatcher(MyEditText et){
        super();
        editText = et;
        charCount = 0;
        backCount = 0;
        completion = 0;
        correction = 0;
    }

    public void afterTextChanged(Editable s) {

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after){

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(count == before + 1 && !editText.isCorrection())
            charCount++;

        if(count == before - 1 && !editText.isCorrection())
            backCount++;

        if( (count - before) > 1 && !editText.isCorrection())
            completion++;

        if(editText.isCorrection()) {
            correction++;
            System.out.println(correction);
            editText.setCorrection(false);
        }
    }

    public int getCharCount() {
        return charCount;
    }

    public int getBackCount() {
        return backCount;
    }

    public int getCompletion() {
        return completion;
    }

    public int getCorrection() {
        return correction;
    }
}