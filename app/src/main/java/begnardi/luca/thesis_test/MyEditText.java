package begnardi.luca.thesis_test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Created by begno on 17/07/15.
 */
public class MyEditText extends EditText {

    private boolean correction;
    private boolean completion;

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context) {
        super(context);
    }

    public boolean isCorrection() {
        return correction;
    }

    public void setCorrection(boolean correction) {
        this.correction = correction;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class MyInputConnection extends InputConnectionWrapper {

        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

//        @Override
//        public boolean sendKeyEvent(KeyEvent event) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN
//                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
//                // Un-comment if you wish to cancel the backspace:
//                //return false;
//            }
//            return super.sendKeyEvent(event);
//        }
//
//        @Override
//        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
//            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
////            System.out.println(beforeLength);
////            System.out.println(afterLength);
//            if (beforeLength == 1 && afterLength == 0) {
//                // backspace
//                sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
//                sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
//            }
//
//            return super.deleteSurroundingText(beforeLength, afterLength);
//        }
//
        @Override
        public boolean commitText (CharSequence text, int newCursorPosition){
            System.out.println(text.toString());
            System.out.println(newCursorPosition);
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean commitCorrection(CorrectionInfo text) {
            System.out.println("CORRECTION");
            correction = true;
            return super.commitCorrection(text);
        }
    }

}
