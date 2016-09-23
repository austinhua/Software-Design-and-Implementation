package utility;

import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.HashMap;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.Enumeration;

/*
 * TextBot class used to send notifcations to phones via SMS text.
 */
public class TextBot {

	private String myNumber;
    private ResourceBundle myResources;
    private Account account;

    public TextBot (String resources, String accountSID, String authToken, String number) {
    	this.myNumber = number;
        myResources = ResourceBundle.getBundle(resources);
        TwilioRestClient client = new TwilioRestClient(accountSID, authToken);
        this.account = client.getAccount();
    }
    
    /*
     * sends a single text message to each of the names in the list.
     * 
     * @param	String message is the message to be sent
     * @param	List<String> names contains names of the people to send messages (their names must appear
     * 			in the properties file).
     */
    public void sendText (String message, List<String> names) throws TwilioRestException {
        MessageFactory messageFactory = account.getMessageFactory();
        for (String name : names) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", myResources.getString(name.toLowerCase()))); 
            params.add(new BasicNameValuePair("From", myNumber)); 
            params.add(new BasicNameValuePair("Body", message));
            Message sms = messageFactory.create(params);
        }
    }
    /*
     * sends a text message to all people in the properties file.
     * 
     * @param	String message is the message to be sent.
     */
    public void sendTextToAll (String message) throws TwilioRestException {
        Enumeration<String> names = myResources.getKeys();
        List<String> list = new ArrayList<String>();
        while (names.hasMoreElements()) {
            list.add(names.nextElement());
        }
        sendText(message, list);
    }

   
}
