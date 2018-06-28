package com.example.viktoriasemina.liquorshop;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class InputValidation {
    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    /**
     * This method is to check whether EditText filled
     */

    public boolean isEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        String value = textInputEditText.getText().toString().trim();

        if (value.isEmpty()){
            textInputLayout.setError("This field cannot be empty");
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * This method is to check whether Mail valid
     */

    public boolean isEditTextMail(TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        String value = textInputEditText.getText().toString().trim();
        textInputLayout.setError(null);
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputLayout.setError("Email is not valid");
            hideKeyboardFrom(textInputEditText);
            return  false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }



    private void
    hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
