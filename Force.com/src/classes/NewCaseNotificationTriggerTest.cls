@isTest
private class NewCaseNotificationTriggerTest {

    static testMethod void testPushNotification(){
       
        
        Profile p = [select id from profile where name='Standard User'];
        User u = new User(  alias = 'standt', email='standarduser@testorg.com',
                            emailencodingkey='UTF-8', lastname='Testing', languagelocalekey='en_US',
                            localesidkey='en_US', profileid = p.Id,
                            timezonesidkey='America/Los_Angeles', username='testPushNotification@testorg.com');
        insert u;          
        
        Mobile_Device__c m = new Mobile_Device__c(name = 'test',
                                                 user__c = u.Id);
        insert m;
        
        Case c = new Case (OwnerId = u.Id);
        insert c;                        
        
        User u2 = new User(  alias = 'standt', email='standarduser2@testorg.com',
                            emailencodingkey='UTF-8', lastname='Testing', languagelocalekey='en_US',
                            localesidkey='en_US', profileid = p.Id,
                            timezonesidkey='America/Los_Angeles', username='testPushNotification2@testorg.com');
        insert u2;          
        
        Mobile_Device__c m2 = new Mobile_Device__c(name = 'test',
                                                 user__c = u2.Id);
        insert m2;

                         
        c.ownerId = u2.Id;
        update c;
    }
}