package customwidget;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;

/**
 * Created by MeghaVinay on 2/24/2015.
 */
public class CustomViewUtil {

    public static void setTypeface(AttributeSet attrs, TextView textView) {
        TypedArray a = textView.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontName = a.getString(R.styleable.CustomTextView_fontName);
        if (fontName!=null) {
            Typeface myTypeface = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/"+fontName);
            textView.setTypeface(myTypeface);
        }
        a.recycle();
    }



}
