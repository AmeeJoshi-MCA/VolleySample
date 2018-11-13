package com.lithe.service.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class VolleyServiceHandler {

    private final String TAG = this.getClass().getName();

    public static final String TIMEOUT_ERROR = "Connection Timeout";
    public static final String NETWORK_ERROR = "Network Connection Unavailable";
    public static final String AUTHENTICATION_ERROR = "Authentication Failure or session has been expired";
    public static final String SERVER_ERROR = "Server Error";
    public static final String PARSE_ERROR = "Parse Error";
    public static final String UNKNOWN_ERROR = "Something went wrong";

    public final static int GET = Request.Method.GET;
    public final static int POST = Request.Method.POST;
    public final static int PUT = Request.Method.PUT;
    public final static int DELETE = Request.Method.DELETE;
    public final static int HEAD = Request.Method.HEAD;
    public final static int OPTIONS = Request.Method.OPTIONS;
    public final static int TRACE = Request.Method.TRACE;
    public final static int PATCH = Request.Method.PATCH;

    private ServerTokenListener serverTokenListener;

    private static ArrayList<StringRequest> listRequests;

    public VolleyServiceHandler() {
        listRequests = new ArrayList<>();
    }

    public void makeHttpCall(final Context context,
                             int method,
                             String url,
                             final LinkedHashMap<String, String> headers,
                             final LinkedHashMap<String, String> params,
                             final int requestTimeout,
                             final int retries,
                             final CallBackListener callBackListener) {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        Log.i("VOLLEY_SERVICE_URL___", url);

        if (headers != null && !headers.isEmpty()) {
            for (int i = 0; i < headers.size(); i++) {
                String key = (String) headers.keySet().toArray()[i];
                Log.i("VOLLEY_SERVICE_HEADER", key + " : " + headers.values().toArray()[i]);
                if (headers.values().toArray()[i] == null) {
                    headers.put(key, "");
                }
            }
        }

        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                String key = (String) params.keySet().toArray()[i];
                Log.i("VOLLEY_SERVICE_PARAM_", key + " : " + params.values().toArray()[i]);
                if (params.values().toArray()[i] == null) {
                    params.put(key, "");
                }
            }
        }

        final long timeStart = System.currentTimeMillis();

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //ignore
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //ignore
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            protected Response<String> parseNetworkResponse(final NetworkResponse networkResponse) {

                listRequests.remove(this);

                final int statusCode = networkResponse.statusCode;
                byte[] responseByteString = networkResponse.data;

                String serverToken = networkResponse.headers.get(ServerTokenHandler.KEY_AUTHORIZATION);
                if (serverToken != null) {
                    Log.i("VOLLEY_SERVER_TOKEN", "RESPONSE TOKEN : " + serverToken);

                    ServerTokenHandler.updateServerToken(serverToken);
                    serverToken = ServerTokenHandler.getServerToken();

                    if (serverTokenListener != null) {
                        serverTokenListener.onTokenRefresh(false, serverToken, statusCode);
                    }
                }

                if (responseByteString != null) {

                    final String response = new String(responseByteString).trim();

                    Log.i("VOLLEY_SERVICE_RESPONSE", response);
                    Log.i("VOLLEY_SERVICE_RESPONSE", "RESPONSE CODE : " + statusCode);
                    Log.i("VOLLEY_SERVICE_TIME", "RESPONSE TIME : " + (System.currentTimeMillis() - timeStart) + " ms");

                    if (callBackListener != null) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callBackListener.onResponse(networkResponse, response, statusCode);
                                }
                            });
                        } else {
                            callBackListener.onResponse(networkResponse, response, statusCode);
                        }
                    }
                }

                return super.parseNetworkResponse(networkResponse);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {

                listRequests.remove(this);

                final String error;
                final String response;
                final int statusCode;

                final NetworkResponse networkResponse = volleyError.networkResponse;
                if (networkResponse != null) {
                    statusCode = networkResponse.statusCode;
                    byte[] responseByteString = networkResponse.data;
                    if (responseByteString != null) {
                        response = new String(responseByteString).trim();

                        try {
                            JSONObject objResponse = new JSONObject(response);
                            String status = objResponse.optString("status", null);
                            if (status != null) {
                                if ((status.equals("Expired token") || status.equals("Signature verification failed")) && statusCode == 401) {
                                    String serverToken = networkResponse.headers.get(ServerTokenHandler.KEY_AUTHORIZATION);
                                    if (serverToken != null) {
                                        Log.i("VOLLEY_SERVER_TOKEN", "RESPONSE TOKEN : " + serverToken);

                                        ServerTokenHandler.updateServerToken(serverToken);
                                        serverToken = ServerTokenHandler.getServerToken();

                                        if (serverTokenListener != null) {
                                            serverTokenListener.onTokenRefresh(true, serverToken, statusCode);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.i("VOLLEY_SERVICE_ERROR", "RESPONSE STRING : " + response);
                    } else {
                        response = null;
                    }
                    Log.i("VOLLEY_SERVICE_ERROR", "RESPONSE CODE : " + statusCode);
                } else {
                    statusCode = 0;
                    response = null;
                }

                if (volleyError instanceof TimeoutError) {
                    error = TIMEOUT_ERROR;
                } else if (volleyError instanceof NoConnectionError | volleyError instanceof NetworkError) {
                    error = NETWORK_ERROR;
                } else if (volleyError instanceof AuthFailureError) {
                    error = AUTHENTICATION_ERROR;
                } else if (volleyError instanceof ServerError) {
                    error = SERVER_ERROR;
                } else if (volleyError instanceof ParseError) {
                    error = PARSE_ERROR;
                } else {
                    error = UNKNOWN_ERROR;
                }

                Log.i("VOLLEY_SERVICE_ERROR", "ERROR : " + error);
                Log.i("VOLLEY_SERVICE_TIME", "RESPONSE TIME : " + (System.currentTimeMillis() - timeStart) + " ms");

                if (callBackListener != null) {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBackListener.onError(error, networkResponse, response, statusCode);
                            }
                        });
                    } else {
                        callBackListener.onError(error, networkResponse, response, statusCode);
                    }
                }
                return super.parseNetworkError(volleyError);
            }
        };

        stringRequest.setTag(TAG);

        listRequests.add(stringRequest);

        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(requestTimeout, retries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue.RequestFinishedListener<Object> requestFinishedListener = new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                request.cancel();
                requestQueue.removeRequestFinishedListener(this);
            }
        };

        requestQueue.addRequestFinishedListener(requestFinishedListener);
    }

    public void cancelAllRequests() {
        if (listRequests != null) {
            if (listRequests.size() > 0) {
                Log.i("VOLLEY_SERVICE_CALL", "Cancel All Running Requests , SIZE = " + listRequests.size());
                for (int i = 0; i < listRequests.size(); i++) {
                    listRequests.get(i).cancel();
                    listRequests.remove(i);
                }
            }
        }
    }

    public void setServerTokenListener(ServerTokenListener serverTokenListener) {
        this.serverTokenListener = serverTokenListener;
    }

    public interface CallBackListener {
        void onResponse(NetworkResponse networkResponse, String response, int statusCode);

        void onError(String error, NetworkResponse networkResponse, String response, int statusCode);
    }

    public interface ServerTokenListener {
        void onTokenRefresh(final boolean isExpired, final String serverToken, final int responseCode);
    }
}
