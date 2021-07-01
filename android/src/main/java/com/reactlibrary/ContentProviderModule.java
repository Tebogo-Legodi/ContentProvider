package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.net.Uri;
import android.content.Context;
import android.database.Cursor;

public class ContentProviderModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    public static final String NAME = "ContentProvider";


    public ContentProviderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ContentProvider";
    }

    @ReactMethod
    public void getData(String uriEndpoint, Promise promise) {

      Uri contentUri = Uri.parse(uriEndpoint);
      String[] projection = null;
      String selection = null;
      String[] selectionArgs = null;
      String sortOrder = null;
      JSONArray resultJSONArray = new JSONArray();

      Cursor result = this.reactContext.getContentResolver().query(contentUri, projection, selection, selectionArgs, sortOrder);

      if(result == null){
        Log.d(NAME, "Result is empty from content provider");
        promise.reject("Unable to get results from content provider");
          //Toast.makeText(getApplicationContext(), "Context provider result is empty", Toast.LENGTH_LONG);
      }
      else{
          try {

              while (result != null && result.moveToNext()) {
                  JSONObject resultRow = new JSONObject();
                  int colCount = result.getColumnCount();
                  for (int i = 0; i < colCount; i++) {
                      try {
                          resultRow.put(result.getColumnName(i), result.getString(i));
                      } catch (JSONException e) {
                          resultRow = null;
                      }
                  }
                  resultJSONArray.put(resultRow);
              }
          } finally {
              if(result != null) result.close();
          }

          Log.d(NAME, "Done calling content provider");
          Log.d(NAME, resultJSONArray.toString());

          promise.resolve(resultJSONArray.toString());
      }


    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
