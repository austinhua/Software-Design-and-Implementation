package utility;

import java.io.FileNotFoundException;

import com.twilio.sdk.TwilioRestException;
/*
 * Sample code that demonstrates basic method calls with CloudStorage object
 */
public class Test1 {
    
    private static final String MESSAGE = "new files have been uploaded!";
	private static final String DEVELOPER_TOKEN = "ZfoXn5Pgn83KFwvyZciGCaiGADJWB3CX";
    private static final String RESOURCE_PATH = "utility/recvlist";
    private static final String MY_NUMBER = "+12056600267";
    private static final String ACCOUNT_SID = "AC7fc35c31998ec586534822716579d284";
    private static final String AUTH_TOKEN = "09a2ae71719093f892ee57a320eba40f";

    public static void main(String[] args) throws FileNotFoundException, TwilioRestException {
        CloudStorage c = new CloudStorage(DEVELOPER_TOKEN);
        c.setUpNotifications(RESOURCE_PATH, ACCOUNT_SID, AUTH_TOKEN, MY_NUMBER);
        c.uploadFolder("src/main");
        c.uploadFolder("src/utility");
        c.goToFolder("main");
        c.uploadFolder("src/utility");
        c.uploadFile("src/utility/Test1.java", "Test4.java");
        c.listFolders();
        c.goToRootFolder();
        c.downloadFromCurrent("main", "~/Downloads");
        c.notify(MESSAGE);
    }

    
}
