package com.example.nativeandroidapplicationbasic;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {
    private Pattern pattern;

    /**
     * Establece la expresión regular que puede tener un campo número para que solo vaya del 0 al 10
     * y solo se admitan 2 decimales para todos los números del 0 al 9, ambos inclusive
     */
    DecimalDigitsInputFilter() {
        pattern = Pattern.compile("[0-9](((\\.[0-9]{0,2})?)||(\\.)?)|10");
    }

    /**
     * Establece el filtrado del campo teniendo en cuenta un patrón establecido para en tal caso
     * permitir su modificación o no de acuerdo con si cumple la estructura del patrón o no
     * @param source Cadena de carácteres nueva a analizar
     * @param start Posición de inicio de la nueva cadena
     * @param end Posición de fin de la nueva cadena
     * @param dest Cadena de carácteres que había previamente
     * @param dstart Posición de inicio de la antigua cadena
     * @param dend Posición de fin de la cadena anterior
     * @return Devuelve si el cambio se admite o no
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        /*
            Si la cadena nueva no cumple el patrón, devuelve comillas dobles para indicar de que no
            es válida. En caso contrario, devuelve null.
         */
        if (!builder.toString().matches(pattern.pattern())) {
            return "";
        }
        return null;
    }
}
