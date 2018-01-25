package com.hololo.library.otpview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

public class OTPView extends LinearLayout {
    private OTPListener listener;
    private int count, inputType, textColor, hintColor, viewsPadding, textSize;
    private String hint;

    public OTPView(Context context) {
        super(context);
        init(null);
    }

    public OTPView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OTPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OTPView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public OTPView setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public OTPView setCount(int count) {
        this.count = count;
        return this;
    }

    public OTPView setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    public OTPView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public OTPView setHintColor(int hintColor) {
        this.hintColor = hintColor;
        return this;
    }

    public OTPView setViewsPadding(int viewsPadding) {
        this.viewsPadding = viewsPadding;
        return this;
    }


    public OTPView setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    private void init(AttributeSet attrs) {
        int padding = (int) dp2px(8);
        setPadding(padding, padding, padding, padding);
        setFocusableInTouchMode(true);
        TypedArray styles = getContext().obtainStyledAttributes(attrs, R.styleable.OTPView);
        count = styles.getInt(R.styleable.OTPView_count, 5);
        inputType = styles.getInt(R.styleable.OTPView_inputType, InputType.TYPE_CLASS_TEXT);
        textColor = styles.getInt(R.styleable.OTPView_textColor, -1);
        hintColor = styles.getInt(R.styleable.OTPView_hintColor, -1);
        viewsPadding = styles.getInt(R.styleable.OTPView_viewsPadding, -1);
        hint = styles.getString(R.styleable.OTPView_otpHint);
        textSize = styles.getInt(R.styleable.OTPView_textSize, -1);
        String text = styles.getString(R.styleable.OTPView_otpText);

        fillLayout();

        setOtp(text);
    }

    public void fillLayout() {
        clearLayout();
        for (int i = 0; i < count; i++) {
            OTPEditText editText = new OTPEditText(getContext(), i);
            editText.setMargin((int) dp2px(22));
            editText.setInputType(inputType);
            if (textColor != -1) {
                editText.setTextColor(textColor);
            }
            if (hintColor != -1) {
                editText.setHintTextColor(hintColor);
            }
            if (viewsPadding != -1) {
                editText.setMargin(viewsPadding);
            }

            if (textSize != -1) {
                editText.setTextSize(dp2px(textSize));
            }

            if (hint != null && hint.length() > 0) {
                editText.setHint(hint.substring(0, 1));
            }
            addView(editText);
        }
        getChildAt(0).requestFocus();
    }

    private void clearLayout() {
        removeAllViewsInLayout();
    }

    public void onBackPressed(OTPEditText view) {
        OTPEditText child = (OTPEditText) getChildAt(view.getOrder() - 1);
        if (child != null) {
            child.setText("");
            child.requestFocus();
        }
    }

    public void onKeyPressed(OTPEditText view) {
        OTPEditText child = (OTPEditText) getChildAt(view.getOrder() + 1);
        if (child != null) {
            child.requestFocus();
        } else {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
            requestFocus();
            if (listener != null) {
                listener.otpFinished(getOtp());
            }
        }
    }

    public OTPView setListener(OTPListener listener) {
        this.listener = listener;
        return this;
    }

    public OTPView setOtp(String otp) {
        if (otp != null)
            for (int i = 0; i < otp.length(); i++) {
                OTPEditText child = (OTPEditText) getChildAt(i);
                String a = String.valueOf(otp.toCharArray()[i]);
                child.setText(a);
            }
        return this;
    }

    public String getOtp() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            OTPEditText child = (OTPEditText) getChildAt(i);
            builder.append(child.getText().toString());
        }
        return builder.toString();
    }

    private float dp2px(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return dip * scale + 0.5f;
    }

    public void focusChange(View view) {
        OTPEditText editText = (OTPEditText) view;
        if (editText.getOrder() < getOtp().length() - 1) {
            setFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInputFromWindow(getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    public OTPView setFocus() {
        View view = getChildAt(getOtp().length());
        if (view != null) {
            view.requestFocus();
        } else {
            view = getChildAt(getChildCount() - 1);
            view.requestFocus();
        }
        return this;
    }
}