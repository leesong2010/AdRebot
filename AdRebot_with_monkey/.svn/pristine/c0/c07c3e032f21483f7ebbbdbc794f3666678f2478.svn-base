package com.ad.dyd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootUpReceiver extends BroadcastReceiver{     
    public void onReceive(Context context, Intent intent) {     
    	Log.d("qumi","rrrrrrrrrrrrrrrrrrrrrrr");
    	System.out.println("#############################");
        Intent i = new Intent(context, com.ad.dyd.MainActivity.class);
        i.putExtra("autorun", true);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        context.startActivity(i);         
    }     

}
