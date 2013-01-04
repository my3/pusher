package com.pusher.app;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    static final String SERVER_URL = "https://openid.mythrikiran.com/gcm-demo";

    static final String SENDER_ID = "264741303687";

    static final String TAG = "PuSHeR";


    static final String DISPLAY_MESSAGE_ACTION =
            "com.pusher.app.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}