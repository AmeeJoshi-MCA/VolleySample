package com.volleySample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.volleySample.R;
import com.lithe.service.application.AppControl;
import com.lithe.service.services.ApiUrl;
import com.lithe.service.services.VolleyServiceHandler;
import com.lithe.service.utils.Constants;
import com.lithe.service.views.FrameLoaderDialog;

import org.json.JSONObject;

import java.util.LinkedHashMap;

public class LoginActivity extends AppCompatActivity {

    private FrameLoaderDialog frameLoaderDialog;
    private EditText editTextUserId, editTextPassword;
    private TextView textViewButtonSignIn;
    private AlertDialog alertDialogRequestPermission;
    private AppControl appControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appControl = new AppControl(LoginActivity.this);

        setupObjects();
        setupUpdateUI();
        setupComponents();
        performOperations();
    }

    private synchronized void setupObjects() {

        frameLoaderDialog = new FrameLoaderDialog(LoginActivity.this);
        editTextUserId = (EditText) findViewById(R.id.activity_login_edittext_user_id);
        editTextPassword = (EditText) findViewById(R.id.activity_login_edittext_password);
        textViewButtonSignIn = (TextView) findViewById(R.id.activity_login_text_button_login);
    }

    private synchronized void setupUpdateUI() {

        String colorAccentHex = AppControl.getIntColorToHexString(appControl.getColorByID(R.color.colorAccent));
        String fontColor = AppControl.getIntColorToHexString(appControl.getColorByID(R.color.bw_a));

        editTextPassword.post(new Runnable() {
            @Override
            public void run() {
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private synchronized void setupComponents() {
        textViewButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidate()) {
                    makeLoginApiCall();
                }
            }
        });
    }

    private synchronized void performOperations() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        checkPermissions();
        Log.i("RequestPermission", "ON REQUEST PERMISSION RESULT CALL");
    }

    private void checkPermissions() {

        boolean isAnyPermissionDenied = false;

        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "ACCESS_COARSE_LOCATION");
            isAnyPermissionDenied = true;
        }if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "ACCESS_FINE_LOCATION");
            isAnyPermissionDenied = true;
        }if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "READ_PHONE_STATE");
            isAnyPermissionDenied = true;
        }

        if (isAnyPermissionDenied) {
            if (alertDialogRequestPermission != null) {
                if (!alertDialogRequestPermission.isShowing()) {
                    alertDialogRequestPermission.show();
                }
            } else {
                alertDialogRequestPermission = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Notice")
                        .setMessage("This application requires some device permissions. or goto app settings to enable required permissions")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] permissionStack = {
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.READ_PHONE_STATE
                                };

                                int permissionRequireCount = 0;
                                for (String permission : permissionStack) {
                                    if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
                                        permissionRequireCount++;
                                    }
                                }
                                if (permissionRequireCount != 0) {
                                    ActivityCompat.requestPermissions(LoginActivity.this, permissionStack, 1);
                                }
                            }
                        })
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNeutralButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }

    private boolean isValidate() {

        if (editTextUserId.getText().toString().trim().isEmpty()) {
            editTextUserId.setError("Please enter name");
            return false;
        }

        if (editTextPassword.getText().toString().trim().isEmpty()) {
            editTextPassword.setError("Please enter job");
            return false;
        }

        return true;
    }

    // Login API call
    private void makeLoginApiCall() {
        showProgressDialog();

        LinkedHashMap<String, String> headers = new LinkedHashMap<>();

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("name", editTextUserId.getText().toString());
        params.put("job", editTextPassword.getText().toString().trim());

        AppControl.getVolleyServiceHandler().makeHttpCall(LoginActivity.this,
                VolleyServiceHandler.POST,
                ApiUrl.API_Login,
                headers,
                params,
                60000,
                0,
                new VolleyServiceHandler.CallBackListener() {
                    @Override
                    public void onResponse(NetworkResponse networkResponse, String response, int statusCode) {
                        hideProgressDialog();
                        try {
                            JSONObject objResponse = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, ""+objResponse.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onError(String error, NetworkResponse networkResponse, final String response,
                                        int statusCode) {
                        try {
                            if (response != null) {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.optString(Constants.KEY_JSON_STATUS);
                                if (status != null) {
                                    AppControl.toastManager.showToast(LoginActivity.this, status);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppControl.toastManager.dismissAllToasts();
    }


    public void showProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLoaderDialog.show();
            }
        });
    }

    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLoaderDialog.dismiss();
            }
        });
    }
}