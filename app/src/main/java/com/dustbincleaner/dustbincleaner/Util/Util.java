package com.dustbincleaner.dustbincleaner.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Interface.GetJsonArrayReesult;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Object.CleanerList;
import com.dustbincleaner.dustbincleaner.RequestHandler.CustomRequest;
import com.dustbincleaner.dustbincleaner.RequestHandler.JsonarryRequest;
import com.dustbincleaner.dustbincleaner.SharedPrefrence.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;


public class Util {

    public static final String BASEURL = "http://www.myprojectshub.com/dustbinbackend/";
    public static final String LOGINURL = "login.php";
    public static final String ADDCLEANERURL =  "addcleaner.php";
    public static final String GETDUSTBINDATAURL =  "senddustbindata.php";
    public static final String GETCLEANERDATAURL =  "cleanerlist.php";
    public static final String ALLOCATEURL ="allocatedustbin.php";
    public static final String CLEANERLIST = "cleanerlist.php";
    public static final String ALLOCATEDDUSTBIN = "allocatelist.php";



    public boolean check_connection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showSuccessmessage(Context context,String message){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Great.....")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }


    public void showerrormessage(Context context,String message){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public boolean emptyvalidate(String username, String password) {
        return !username.trim().equals("") && !password.trim().equals("");
    }

    public boolean emptyvalidatethree(String name,String username, String password) {
        return !username.trim().equals("")
                && !name.trim().equals("")
                && !password.trim().equals("");
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void checkinternet(final Context activity){

        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("No Internent Connection")
                .setContentText("Won't be able to login!")
                .setConfirmText("Go to Settings!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                        sDialog.cancel();
                    }
                })
                .show();
    }


    public void loginvalidate(Map args, final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + LOGINURL;
        System.out.println("Response" + args.toString()+ url);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, args, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }


    public void getdustbindata(final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + GETDUSTBINDATAURL;

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }

    public void addcleaner(Map args, final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + ADDCLEANERURL;

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, args, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }


    public void getcleanerdata(final Context context, final GetJsonArrayReesult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + GETCLEANERDATAURL;

        JsonarryRequest jsObjRequest = new JsonarryRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }

    public void allocatedustbin(Map args, final Context context, final GetResult getResult){

  //      final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
//        dialog.show();

        String url = BASEURL + ALLOCATEURL;

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, args, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response" + response.toString());
              //  dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                //    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                  //  dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                //    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                  //  dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                  //  dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                  //  dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                //    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }


    public void getcleanerid(final Context context, final GetResult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + CLEANERLIST;

        JsonarryRequest jsObjRequest = new JsonarryRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response" + response.toString());
                JSONObject jsonObject = new JSONObject();
                String id = cleanerid(response,context);
                try {
                    jsonObject.put("id",id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                getResult.done(jsonObject);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }


    String cleanerid(JSONArray jsonArray, Context context){

        UserLocalStore userLocalStore = new UserLocalStore(context);
        String username = userLocalStore.getUsername();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                String id = json.getString("id");
                String name  = json.getString("username");

                if(name.equals(username))
                    return id;


            } catch (JSONException e) {
            }


        }
        return null;
    }



    public void getallocateddustbin(final Context context, final GetJsonArrayReesult getResult){

        final AlertDialog dialog = new SpotsDialog(context, R.style.Custom);
        dialog.show();

        String url = BASEURL + ALLOCATEDDUSTBIN;

        JsonarryRequest jsObjRequest = new JsonarryRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response" + response.toString());
                dialog.dismiss();
                getResult.done(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.data!=null) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                }

                else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    dialog.hide();
                    showerrormessage(context, "Time Out Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());

                } else if (error instanceof AuthFailureError) {
                    dialog.hide();
                    showerrormessage(context, "Authentication Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {
                    dialog.hide();
                    showerrormessage(context,"Server Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {
                    dialog.hide();
                    showerrormessage(context, "Network Error.....Try Later!!!");
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {
                    dialog.dismiss();
                    System.out.println("Resonse" + error.toString());
                    showerrormessage(context, "Unknown Error.....Try Later!!!");
                }
                else{
                    System.out.println("Resonse" + error.toString());
                    dialog.dismiss();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }

}
