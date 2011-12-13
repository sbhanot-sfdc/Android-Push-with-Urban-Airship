public with sharing class AndroidPushNotification {
	
	public String[] apids;
	public String alert;
	public Map<String, String> extras;
	
	public AndroidPushNotification(String[] apids, String alert, Map<String, String> extra)
	{
		this.apids = apids;
		this.alert = alert;
		this.extras = extra;
	}

	public String toJSONString()
	{
		JSONGenerator gen = JSON.createGenerator(true);
		gen.writeStartObject();
		
		if (apids != null)
		{
			gen.writeObjectField('apids', apids);
		}
		
		gen.writeFieldName('android');
		gen.writeStartObject();
		gen.writeStringField('alert', alert);
		
		if (extras != null)
		{
			gen.writeFieldName('extra');
			gen.writeObject(extras);
		}

		gen.writeEndObject();
		gen.writeEndObject();
		
		return gen.getAsString();
	}
}