package com.lithe.service.utils;

import android.util.Patterns;
import android.widget.EditText;

import com.lithe.service.application.AppControl;


public class InputValidations {

    public static boolean isEmptyInput(EditText editText, String fieldName) {
        if (fieldName == null) {
            fieldName = editText.getHint().toString().trim();
            if (!fieldName.isEmpty()) {
                fieldName = fieldName.toLowerCase();
            }
        }
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Please Enter " + fieldName);
            editText.requestFocus();
            return true;
        }

        //is valid
        editText.setError(null);
        return false;
    }

    public static boolean isValidEmailInput(EditText editText, String fieldName) {
        if (fieldName == null) {
            fieldName = editText.getHint().toString().trim();
            if (!fieldName.isEmpty()) {
                fieldName = fieldName.toLowerCase();
            }
        }
        if (editText.getText().toString().isEmpty()) {
            AppControl.toastManager.showToast(editText.getContext(), "Please enter " + fieldName);
            editText.requestFocus();
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(editText.getText()).matches()) {
                AppControl.toastManager.showToast(editText.getContext(), "Please enter valid " + fieldName);
                editText.requestFocus();
                return false;
            }
        }

        //is valid
        editText.setError(null);
        return true;
    }

    public static boolean isValidMobileInput(EditText editText, String fieldName) {
        String inputText = editText.getText().toString().trim();
        if (fieldName == null) {
            fieldName = editText.getHint().toString().trim();
            if (!fieldName.isEmpty()) {
                fieldName = fieldName.toLowerCase();
            }
        }
        if (inputText.isEmpty()) {
            editText.setError("Please enter " + fieldName);
            editText.requestFocus();
            return false;
        } else {
            if (inputText.contains("+")) {
                if (inputText.lastIndexOf("+") != 0) {
                    editText.setError("Please enter valid " + fieldName);
                    editText.requestFocus();
                    return false;
                } else if (inputText.length() < 13) {
                    editText.setError("Please enter valid " + fieldName);
                    editText.requestFocus();
                    return false;
                }
            } else if (inputText.contains("0") && inputText.indexOf("0") == 0) {
                if (inputText.substring(1).length() < 10) {
                    editText.setError("Please enter valid " + fieldName);
                    editText.requestFocus();
                    return false;
                }
            } else if (inputText.length() < 10) {
                editText.setError("Please enter valid " + fieldName);
                editText.requestFocus();
                return false;
            }
        }

        //is valid
        editText.setError(null);
        return true;
    }

}
