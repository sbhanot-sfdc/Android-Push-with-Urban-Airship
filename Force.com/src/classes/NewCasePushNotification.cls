public with sharing class NewCasePushNotification {
    
    @future(callout=true)
    public static void sendNewCaseNotification(Set<Id> newCaseIds)
    {
        List<Case> cases = [select id, caseNumber, ownerId from Case where id in :newCaseIds];
        Set<Id> caseOwners = new Set<Id>();
        
        for (Case c : cases)
        {
            caseOwners.add(c.ownerId);
        }
        
        List<Mobile_Device__c> devices = [select Name, user__c from Mobile_Device__c where user__c in :caseOwners];
        
        if (devices.size() == 0)
            return;
            
        Map<Id, List<String>> ownerId2DeviceTokens = new Map<Id, List<String>> ();
        for(Mobile_Device__c device : devices)
        {
            List<String> deviceTokens = ownerId2DeviceTokens.get(device.user__c);
            if (deviceTokens != null){
                deviceTokens.add(device.name);
            }
            else {
                deviceTokens = new List<String>();
                deviceTokens.add(device.name);    
                ownerId2DeviceTokens.put(device.user__c, deviceTokens);
            }
        }
        
        List<AndroidPushNotification> notifications = new List<AndroidPushNotification>();
        for (Case c : cases)
        {
            List<String> deviceIds = ownerId2DeviceTokens.get(c.ownerId);
            
            Map<String, String> extras = new Map<String, String>();
            extras.put('caseId', c.Id);
            AndroidPushNotification notification = new AndroidPushNotification(deviceIds ,
                                                                           c.caseNumber + ' has been assigned to you',
                                                                           extras);
            notifications.add(notification);
        }
        
        AndroidUrbanShipPushNotificationSvc svc = new AndroidUrbanShipPushNotificationSvc('Push Test');
        
        try
        {
            svc.sendBatchPushNotification(notifications);
        }
        catch(UrbanAirshipException e)
        {
            System.debug('Could not send US notification');
        }   
    }

}