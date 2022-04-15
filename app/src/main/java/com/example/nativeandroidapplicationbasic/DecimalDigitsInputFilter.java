package com.example.nativeandroidapplicationbasic;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {
    private Pattern pattern;
    DecimalDigitsInputFilter() {
        pattern = Pattern.compile("[0-9](((\\.[0-9]{0,2})?)||(\\.)?)|10");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        if (!builder.toString().matches(pattern.pattern())) {
            return "";
        }
        return null;
    }
}
