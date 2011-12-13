package com.salesforce.casemgmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.salesforce.androidsdk.app.ForceApp;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.ClientManager.LoginOptions;
import com.salesforce.androidsdk.rest.ClientManager.RestClientCallback;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestClient.AsyncRequestCallback;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;
import com.salesforce.androidsdk.util.EventsObservable;
import com.salesforce.androidsdk.util.EventsObservable.EventType;
import com.urbanairship.push.PushManager;
import com.urbanairship.push.PushPreferences;

public class MyCasesActivity extends Activity{

	ProgressDialog dialog;
	ArrayList<JSONObject> caseList;
    private RestClient client;
    private String apiVersion;
    private boolean pushNotificationEnabled = false;
    
	final Handler mHandler = new Handler();
	final Runnable mDisplayProgressBar = new Runnable() {
    	public void run() {
    		DisplayProgressBar(true);
    	}
    };
    
    final Runnable mHideProgressBar = new Runnable() {
    	public void run() {
    		DisplayProgressBar(false);
    	}
    };

    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycases_activity);
		dialog = ProgressDialog.show(this, "Please wait","Retrieving Case data...", true);

		apiVersion = getString(R.string.api_version);
		ListView lv = (ListView)findViewById(R.id.my_cases);
		lv.setTextFilterEnabled(true);
	
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	
			    CaseManagementApp gs = (CaseManagementApp) getApplication();
			    gs.setSelectedCase(caseList.get(position));
			    Intent i = new Intent(MyCasesActivity.this, CaseDetailActivity.class);
				startActivity(i);
			}
		});
			
	}

	@Override
	public void onResume(){
		super.onResume();
		findViewById(R.id.mainView).setVisibility(View.INVISIBLE);
		String accountType = getString(R.string.account_type);
		LoginOptions loginOptions = new LoginOptions(
										null,  
										ForceApp.APP.getPasscodeHash(),
										getString(R.string.oauth_callback_url),
										getString(R.string.oauth_client_id),
										new String[] {"api"});
		
		new ClientManager(this, accountType, loginOptions).getRestClient(this, new RestClientCallback() {
			@Override
			public void authenticatedRestClient(RestClient client) {
				if (client == null) {
					ForceApp.APP.logout(MyCasesActivity.this);
					return;
				}
				MyCasesActivity.this.client = client;
				findViewById(R.id.mainView).setVisibility(View.VISIBLE);
				getCaseData();
				
				if (!pushNotificationEnabled)
					enablePushNotification();
			}
		});
	}	
	
    private void DisplayProgressBar(boolean b) {
		if(!b) { dialog.dismiss(); } 
		else {	
			dialog.show();
		}
	}

	private void getCaseData(){

		try {
			
			String soql = "Select Id, CaseNumber, Status, Account.Name, AccountId, Description, Priority From Case where ownerId = '"+ 
						   client.getClientInfo().userId+"' and isClosed = false order by CaseNumber limit 5";

			RestRequest request = RestRequest.getRequestForQuery(apiVersion, soql);

			client.sendAsync(request, new AsyncRequestCallback() {

				@Override
				public void onSuccess(RestResponse response) {
					try {
						if (response == null || response.asJSONObject() == null)
							return;
						
						JSONArray records = response.asJSONObject().getJSONArray("records");
	
						if (records.length() == 0)
							return;
										
						caseList = new ArrayList<JSONObject>();
						
						for (int i = 0; i < records.length(); i++){
							JSONObject caseRec = (JSONObject)records.get(i);
							//Log.w("Case Rec",caseRec.toString(5));
							caseList.add(caseRec);
						}

				    	MyCasesActivity.this.runOnUiThread(new Runnable() {
				 	         public void run() {
					 			 ListView lv = (ListView)findViewById(R.id.my_cases);
					 			 MyCasesArrayAdapter adap = new MyCasesArrayAdapter(getApplicationContext(),R.layout.case_listitem, caseList);
				 	        	 lv.setAdapter(adap); 
				 	         }
				    	});
				    	mHandler.post(mHideProgressBar);
						EventsObservable.get().notifyEvent(EventType.RenditionComplete);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onError(Exception exception) {
					EventsObservable.get().notifyEvent(EventType.RenditionComplete);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void enablePushNotification()
	{
		pushNotificationEnabled = true;
		
		PushPreferences prefs = PushManager.shared().getPreferences();		
		
        Log.w("UrbanAirship","App APID:"+prefs.getPushId());		

		String apid = prefs.getPushId();

		Map<String, Object> deviceFields = new HashMap<String, Object>(); 
        deviceFields.put("Name", apid);
		RestRequest request;
		try {
			request = RestRequest.getRequestForCreate(apiVersion, 
													  "Mobile_Device__c", 
													  deviceFields);

			client.sendAsync(request, new AsyncRequestCallback() {
				@Override
				public void onSuccess(RestResponse response) {
					Log.i("UrbanAirship","Device registered");
				}
				@Override
				public void onError(Exception exception) {
		    		Log.w("UrbanAirship","Could not register device");	
					EventsObservable.get().notifyEvent(EventType.RenditionComplete);
				}
			});
		} catch (Exception e) {
		}		
	}
}
