About
-----

This sample application implements push notifications from Force.com to Android devices using Urban Airship. This  project is a derivation of a [similar project for iOS]( https://github.com/mbotos/Urban-Airship-for-Force.com).

Further details about this application (including a short demo video) can be found here - 

Installation
-----------

1. Sign up for an [Urban Airship Account](https://go.urbanairship.com/accounts/register/). 

2. Create a new application in Urban Airship. When creating the application make sure to select the 'Push Notifications Support' checkbox. In the section that opens up when you click that option, enter 'com.salesforce.casemgmt' in the 'Android Package' field. Leave all other fields blank.  

3. Install the Force.com half of this sample application into the target Salesforce Org using this [Unmanaged Package](https://login.salesforce.com/packaging/installPackage.apexp?p0=04t50000000Lims). Note that this sample application is configured by default to connect to a Production/Developer Edition Org. Additional changes will need to be made if you want to run the sample from a Sandbox Org.

4. After the package is installed, go to Setup-->Develop-->Custom Settings. Click on the 'Push Notification Setting' link and select the 'Manage' button. Create a new Custom Setting and name it anything you'd like. For the 'Key' value, copy and paste the 'Application Key' from the Urban Airship application that you created in Step 2. This value can be found in the Details page of the UA application. For the 'Master Secret' custom setting value, copy and paste the 'Application Master Secret' from your Urban Airship application.

5. Import the sample Android application into Eclipse. You can do via File-->Import-->Existing Projects into Workspace and then pointing to the 'AndroidUAPush' directory on your machine. Note that this assumes that you've setup your Eclipse environment with the neccessary Android plugins and SDK. For more details on how to setup Eclipse for Android development, check the [Android documentation] (http://developer.android.com/sdk/installing.html).

6. Once the project is imported, open the assets/airshipconfig.properties file. Copy the 'Application Key' from the Urban Airship application that you created in Step 2 into the 'developmentAppKey' entry and the 'Application Secret' into the 'developmentAppSecret' entry. Note that you need the 'Application Secret' and not the 'Application Master Secret' that was used in Step 4.

7. Run the application in the Android Emulator using Run-->Run As-->Android Application. You should be promoted to login to your Salesforce Org when the application first boots up. After logging in, you should see a list of Cases that are assigned to the logged in user. You can click on any Case record to see further details about the Case.

8. To test the Push Notification, create a new Case in Salesforce and assign it to the same user who logged in in Step 7. A new Case notification (with the Case Number) should automatically appear in the top half of the Android Emulator. Clicking on that notification on an actual Android device should take the user to the Case Details screen.
