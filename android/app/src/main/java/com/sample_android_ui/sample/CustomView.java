package com.sample_android_ui.sample;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.image.ReactImageView;
import com.sample_android_ui.R;

/**
 * Created by mmpkl05 on 12/14/17.
 */

public class CustomView  extends LinearLayout{

    private Context context;
    private String message = "NOT SET";

    public CustomView(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
        init();
    }

    public void init() {
        //Part 1: Don't need to copy BONUS part, this alone already integrate Android UI to RN native.
        inflate(this.context, R.layout.multiplecamerastreamlayout, this);
        //This can be viewed in Android Studio's Log Cat.
        Log.i("Inflated XML", "ANDROID_SAMPLE_UI");

        //BONUS: Create a button that writes a toast.
        Button clickButton = (Button) findViewById(R.id.multipleCameraButton);
        final Context _context = context;
        clickButton.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log into the Logcat of android studio. Filter by Info and ANDROID_SAMPLE_UI.
                Log.i("Button get clicked", "ANDROID_SAMPLE_UI");
                Toast toast = Toast.makeText(_context, message, Toast.LENGTH_LONG);
                toast.show();

                //PART 3: This is a sample to receive callback/events from Android to RN's JS and visa versa.
                //Save to remove if don't need to care events sent
                callNativeEvent();
                //END OF PART 3
            }
        });
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //PART 3: Added Receive Event.
    public void callNativeEvent() {
        Log.i("Call Native Event", "ANDROID_SAMPLE_UI");
        //This output a message to Javascript as an event.
        WritableMap event = Arguments.createMap();
        event.putString("customNativeEventMessage", "Emitted an event"); //Emmitting an event to Javascript

        //Create a listener where that emits/send the text to JS when action is taken.
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "nativeClick",    //name has to be same as getExportedCustomDirectEventTypeConstants in MyCustomReactViewManager
                event);
    }
}
