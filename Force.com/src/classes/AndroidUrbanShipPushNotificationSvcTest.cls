@isTest
private class AndroidUrbanShipPushNotificationSvcTest {
    private static String testAppName = 'Test Run';
    
    private class AndroidUrbanShipTester extends AndroidUrbanShipPushNotificationSvc {
        public AndroidUrbanShipTester(String appName){
            super(appName);
        }

        public override HttpResponse send(string path, string body, string method) {
            createRequest(path, body, method);
            return new HttpResponse();
        }
    }
 
    static testMethod void testRegisterDevice()
    {
        setupTestData();
        AndroidUrbanShipTester tester = new AndroidUrbanShipTester(testAppName);
        tester.registerDevice('1','2', new String[]{'1'});
    }
    
    static testMethod void testSendPushNotification()
    {
        setupTestData();
        AndroidUrbanShipTester tester = new AndroidUrbanShipTester(testAppName);
        AndroidPushNotification notification = new AndroidPushNotification(new String[]{'1'},
                                                                          '2',
                                                                          new Map<String, String>());
        tester.sendPushNotification(notification);
    }

    static testMethod void testSendBroadcast()
    {
        setupTestData();
        AndroidUrbanShipTester tester = new AndroidUrbanShipTester(testAppName);
        AndroidPushNotification notification = new AndroidPushNotification(null,
                                                                          '2',
                                                                          new Map<String, String>());
        tester.sendBroadcast(notification);
    }

    static testMethod void testSendBatchPushNotification()
    {
        setupTestData();
        AndroidUrbanShipTester tester = new AndroidUrbanShipTester(testAppName);
        AndroidPushNotification notification = new AndroidPushNotification(new String[]{'1'},
                                                                          '2',
                                                                          new Map<String, String>());
        List<AndroidPushNotification> batch = new List<AndroidPushNotification>();
        batch.add(notification);
        tester.sendBatchPushNotification(batch);
    }
    
    private static void setupTestData()
    {
        Push_Notification_Setting__c setting = new Push_Notification_Setting__c(Name = testAppName,
                                                                                Key__c = 'abc',
                                                                                Master_Secret__c = 'xyz');
        insert setting;                                                                     
    }   
}