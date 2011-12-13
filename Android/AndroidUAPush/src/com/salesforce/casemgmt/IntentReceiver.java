package com.salesforce.casemgmt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

public class IntentReceiver extends BroadcastReceiver {

	private static final String logTag = "PushSample";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(logTag, "Received intent: " + intent.toString());
		String action = intent.getAction();

		if (action.equals(PushManager.ACTION_PUSH_RECEIVED)) {

		    int id = intent.getIntExtra(PushManager.EXTRA_NOTIFICATION_ID, 0);

		    Log.i(logTag, "Received push notification. Alert: " 
		            + intent.getStringExtra(PushManager.EXTRA_ALERT)
		            + " [NotificationID="+id+"]");

		} else if (action.equals(PushManager.ACTION_NOTIFICATION_OPENED)) {

			Log.i(logTag, "User clicked notification. Message: " + intent.getStringExtra(PushManager.EXTRA_ALERT));
		    String caseId = intent.getStringExtra("caseId");
		    Log.i(logTag, "CaseID is: " + caseId);
            Intent launch = new Intent(Intent.ACTION_MAIN);
            
			launch.setClass(UAirship.shared().getApplicationContext(), CaseDetailActivity.class);
			launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			launch.putExtra("caseId", caseId);
			
            UAirship.shared().getApplicationContext().startActivity(launch);

		} else if (action.equals(PushManager.ACTION_REGISTRATION_FINISHED)) {
            Log.i(logTag, "Registration complete. APID:" + intent.getStringExtra(PushManager.EXTRA_APID)
                    + ". Valid: " + intent.getBooleanExtra(PushManager.EXTRA_REGISTRATION_VALID, false));
		}

	}
}
