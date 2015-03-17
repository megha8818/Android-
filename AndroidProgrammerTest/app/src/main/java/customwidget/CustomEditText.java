package customwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by MeghaVinay on 2/24/2015.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
       if (!isInEditMode()) {
            if (attrs!=null) CustomViewUtil.setTypeface(attrs, this);
        }
    }

}