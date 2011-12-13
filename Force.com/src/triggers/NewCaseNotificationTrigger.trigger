trigger NewCaseNotificationTrigger on Case (after insert, after update) {
	
	if (Trigger.isInsert)
	{
		NewCasePushNotification.sendNewCaseNotification(Trigger.newMap.keySet());
	}
	
	else
	{
		Set<Id> caseOwnerChanged = new Set<Id>();
		for (Integer i=0;i<Trigger.new.size();i++)
		{
			if (Trigger.new[i].OwnerId != Trigger.old[i].OwnerId)
			{
				caseOwnerChanged.add(Trigger.new[i].id);
			}
		}
		NewCasePushNotification.sendNewCaseNotification(caseOwnerChanged);
	}

}