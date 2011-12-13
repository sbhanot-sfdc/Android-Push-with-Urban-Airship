package com.salesforce.casemgmt;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.salesforce.androidsdk.app.ForceApp;

import com.salesforce.androidsdk.ui.SalesforceR;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;
import com.urbanairship.push.PushPreferences;

public class CaseManagementApp extends ForceApp {

	SalesforceR salesforceR = new SalesforceRImpl();

	private JSONObject selectedCase;
	
	public JSONObject getSelectedCase() {
		return selectedCase;
	}

	public void setSelectedCase(JSONObject c ) {
		selectedCase = c;
	}

    @Override
    public void onCreate() {
        
        super.onCreate();
        
        AirshipConfigOptions options = AirshipConfigOptions.loadDefaultOptions(this);

        UAirship.takeOff(this, options);
        PushManager.enablePush();	
        Logger.logLevel = Log.VERBOSE;
        PushManager.shared().setIntentReceiver(IntentReceiver.class);

        PushPreferences prefs = PushManager.shared().getPreferences();
                
        Logger.info("CaseManagement onCreate - App APID: " + prefs.getPushId());
    }
    
	@Override
	public Class<? extends Activity> getMainActivityClass() {
		return MyCasesActivity.class;
	}	
		
	@Override
	public int getLockTimeoutMinutes() {
		return 0;
	}

	@Override
	protected String getKey(String name) {
		return null;
	}

	@Override
	public SalesforceR getSalesforceR() {
		return salesforceR;
	}
}
