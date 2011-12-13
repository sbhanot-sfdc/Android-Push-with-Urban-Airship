package com.salesforce.casemgmt;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;


public class MyCasesArrayAdapter extends ArrayAdapter<JSONObject> implements Filterable {
	private ArrayList<JSONObject> cases;
	private Context context;
	private int viewResourceID;
	
	public MyCasesArrayAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> objects) {
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.viewResourceID = textViewResourceId;
		this.cases = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if( v == null ) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(viewResourceID, parent, false);
		}
		JSONObject c = cases.get(position);
		if(c != null)
		{
			TextView caseNum = (TextView)v.findViewById(R.id.caseNumber);
			TextView acctName = (TextView)v.findViewById(R.id.acctName);
			try {
				caseNum.setText(c.getString("CaseNumber")+ " - ");
				Object acct = c.get("Account");
				if (acct != null && acct instanceof JSONObject)
					acctName.setText(((JSONObject)acct).getString("Name"));
				else
					acctName.setText("");	
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return v;
	}
}
