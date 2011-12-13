package com.salesforce.casemgmt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class CaseDetailActivity extends Activity{

	private String caseId;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.case_detail_activity);

		Bundle extras = getIntent().getExtras();
		
		if (extras != null){
			caseId = extras.getString("caseId");
			Log.w("Case Rec in CaseDetailActivity",caseId);
		}
		
		if (caseId != null){
			fetchCaseData(getApplicationContext());
		}
		else{	
			CaseManagementApp gs = (CaseManagementApp) getApplication();
			JSONObject caseRec = gs.getSelectedCase();
  	  		displayCaseData(caseRec);
		}		
	}
	
	private void fetchCaseData(Context context)
	{
		String accountType = context.getString(R.string.account_type);
		LoginOptions loginOptions = new LoginOptions(
										null,  
										ForceApp.APP.getPasscodeHash(),
										context.getString(R.string.oauth_callback_url),
										context.getString(R.string.oauth_client_id),
										new String[] {"api"});

		new ClientManager(this, accountType, loginOptions).getRestClient(this, new RestClientCallback() {
			@Override
			public void authenticatedRestClient(RestClient client) {
				try {					
					String soql = "Select Id, CaseNumber, Status, Account.Name, AccountId, Description, Priority From Case where Id = '"+ caseId+"'";

					RestRequest request = RestRequest.getRequestForQuery(getString(R.string.api_version), soql);

					client.sendAsync(request, new AsyncRequestCallback() {

						@Override
						public void onSuccess(RestResponse response) {
							try {
								if (response == null || response.asJSONObject() == null)
									return;
								
								JSONArray records = response.asJSONObject().getJSONArray("records");
			
								if (records.length() == 0)
									return;
												
								final JSONObject caseRec = (JSONObject)records.get(0);
								
								CaseDetailActivity.this.runOnUiThread(new Runnable() {
						 	         public void run() {
						 	        	displayCaseData(caseRec);
						 	         }
						    	});
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
		});
	}
	
	private void displayCaseData(JSONObject caseRec)
	{
 	  	try {
	  	  	Object acct = caseRec.get("Account");
			if (acct != null && acct instanceof JSONObject)
				((TextView) findViewById(R.id.acctName)).setText(((JSONObject)acct).getString("Name"));
	  	  	((TextView) findViewById(R.id.caseNumber)).setText(caseRec.getString("CaseNumber"));
	  	  	((TextView) findViewById(R.id.caseDesc)).setText(caseRec.getString("Description"));
	  	  	((TextView) findViewById(R.id.caseStatus)).setText(caseRec.getString("Status"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
