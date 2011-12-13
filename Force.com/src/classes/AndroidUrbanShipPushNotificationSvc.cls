public with sharing virtual class AndroidUrbanShipPushNotificationSvc{
    public static String baseUrl ='https://go.urbanairship.com/api/';
    public String applicationName;  
    
    public AndroidUrbanShipPushNotificationSvc(String appName)
    {
        applicationName = appName;
    }
    
    public void registerDevice(String apid, String alias, String[] tags )
    {
        JSONGenerator gen = JSON.createGenerator(true);
        gen.writeStartObject();
        gen.writeStringField('alias', alias);
        gen.writeObjectField('tags', tags);
        gen.writeEndObject();
        
        System.debug(gen.getAsString());
        send('apids/'+ apid, gen.getAsString(), 'PUT').getStatusCode();
    }
    
    public void sendPushNotification(AndroidPushNotification notification)
    {
        if (notification == null || notification.apids == null || notification.alert == null)
            return;
        System.debug('Output is:'+notification.toJSONString()); 
        send('push/', notification.toJSONString(), 'POST').getStatusCode();     
    }
    
    public void sendBroadcast(AndroidPushNotification notification)
    {
        if (notification == null || notification.alert == null || notification.apids != null)
            return;
        System.debug(notification.toJSONString());  
        send('push/broadcast/', notification.toJSONString(), 'POST').getStatusCode();       
        
    }
    
    public void sendBatchPushNotification(List<AndroidPushNotification> notifications)
    {
        if (notifications == null || notifications.size() == 0)
            return;
        String json = '[';
        
        for(AndroidPushNotification notification : notifications)
        {
            json += notification.toJSONString() + ',';
        }
        
        json = json.substring(0, json.length() -1) + ']';
        
        System.debug('Output is:'+json);
        send('push/batch/', json, 'POST').getStatusCode();          
    }
    
    protected HttpRequest createRequest(string path, string body, string method) {
        HttpRequest req = new HttpRequest();
        req.setEndpoint(baseUrl + path);
        req.setMethod(method);
        
        Push_Notification_Setting__c setting = [select Key__c, Master_Secret__c from Push_Notification_Setting__c where Name = :applicationName];
        if (setting == null) {
            System.debug(LoggingLevel.ERROR, 'No Push Notification Setting found for ' + applicationName);
        } else {
            req.setHeader('Authorization', 'BASIC ' + EncodingUtil.base64Encode(Blob.valueOf(setting.Key__c + ':' + setting.Master_Secret__c)));
        }
        
        if (body != null) {
            req.setHeader('Content-Length', String.valueOf(body.length()));
            req.setHeader('Content-Type', 'application/json');
            req.setBody(body);
        }
        
        return req;
    }   
    
    protected virtual HttpResponse send(string path, string body, string method) {
        Http h = new Http();    
        HttpRequest req = createRequest(path, body, method);
    
        System.debug('Sending to ' + req.getEndpoint() + ': ' + req.getBody());
        HttpResponse res = h.send(req);
        System.debug('Response: ' + res.getBody());
        
        if (res.getStatusCode() != 200)
        {
            throw new UrbanAirshipException (res.getBody());
        }

        return res;
    }
}