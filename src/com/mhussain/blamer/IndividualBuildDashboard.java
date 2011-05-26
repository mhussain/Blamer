package com.mhussain.blamer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualBuildDashboard extends Activity {
	
	private String contactText = "SMS ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Build build = (Build) getIntent().getExtras().get("build");
        
        BuildJSON lastBuild = new BuildJSON();
        
        String lastCommitBy = build.getLastCommitBy();
        String lastCommitId = build.getLastCommitId();
        String lastCommitDate = build.getLastCommitDateTime().toString();
        
        final String SMS_MESSAGE = "You broke '" + build.getName() + "' on '" + lastCommitDate + "' with commit = '" + lastCommitId + "'";
        
        final String person_name = lastCommitBy;
        
        String contactId  = this.getContactID(person_name);
       
        
        JSONObject lastBuildData = null; 
        
        try {
			lastBuildData = lastBuild.getLastBuild(build.getUrl());
        } 
        catch (Exception e) {
			System.err.println("Could not get last Build information from " + build.getUrl() + ":" + e.toString());
		}
        if (lastBuildData != null) {
        	try {
        		lastCommitBy = lastBuildData.getJSONObject("author").get("name").toString();
        		lastCommitId = lastBuildData.getJSONObject("id").toString();
        		lastCommitDate = lastBuildData.getJSONObject("date").toString();
        	} 
        	catch (JSONException e) {
        		e.printStackTrace();
        	}
        	
        }
        
        contactText = contactText.concat("'"+lastCommitBy+"'");
        
        setContentView(R.layout.individual_build_dashboard);
        TextView name = (TextView)this.findViewById(R.id.build_name);
        name.setText(build.getName());
        
        TextView desc = (TextView)this.findViewById(R.id.build_desc);
        desc.setText(build.getDescription());
			
		TextView who = (TextView)this.findViewById(R.id.last_committer);
		TextView what = (TextView)this.findViewById(R.id.last_commit_id);
		TextView when = (TextView)this.findViewById(R.id.last_commit_date);

		Button contact = (Button)this.findViewById(R.id.contact);
		
		
		if (null != contactId) {
			
			contact.setText(contactText);
			final String phone = this.getPhoneNumberForContact(contactId);
			
			contact.setOnClickListener(new OnClickListener() {
	
				public void onClick(View v) {

					SmsManager smsManager = SmsManager.getDefault();
					
					PendingIntent sentPI = PendingIntent.getBroadcast(IndividualBuildDashboard.this, 0, new Intent("sms_sent"), 0);
					PendingIntent deliveredPI = PendingIntent.getBroadcast(IndividualBuildDashboard.this, 0,
	                        new Intent("sms_delivered"), 0);
	
					Toast.makeText(IndividualBuildDashboard.this, "Sending SMS to " + person_name, Toast.LENGTH_SHORT).show();
					smsManager.sendTextMessage(phone, null, SMS_MESSAGE, sentPI, deliveredPI);
					Toast.makeText(IndividualBuildDashboard.this, "SMS Sent", Toast.LENGTH_SHORT).show();
				}
			});
		}
		else {
			contact.setText("'" + person_name + "' does not exist in your contacts!");
			contact.setEnabled(false);
		}
		
        who.setText(lastCommitBy);
        what.setText(lastCommitId);
        when.setText(lastCommitDate);
		
	}
	
	protected String getPhoneNumberForContact(String id) {
		String phoneNumber = "";
		ContentResolver cr = getContentResolver();
		Cursor phoneNumbers = cr.query(
	 		    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
	 		    null, 
	 		    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
	 		    new String[]{id}, null
	 		);
			
			while(phoneNumbers.moveToNext()) {
				phoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
				break;
			}
			
			phoneNumbers.close();
			return phoneNumber;
	}
	
	protected String getContactID(String name) {
		ContentResolver cr = getContentResolver();
        Cursor contacts = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
       
        if (contacts.getCount() > 0) {
        	while (contacts.moveToNext()) {
        		String id = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
        		
        		String contactName = contacts.getString(
        			contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        		);
        		
        		/* Name exists? */
        		if (contactName.equalsIgnoreCase(name) != true) {
        			continue;
        		}
        		else {
        			
        			/* Has phone number? */
        			if (Integer.parseInt(contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
        				return id;
        			}
        		}
            }
        }
        contacts.close();
        return null;
	}
}
