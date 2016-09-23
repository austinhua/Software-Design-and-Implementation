package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.twilio.sdk.TwilioRestException;

/**
 * CloudStorage class contains useable API that can upload and download files, and notify others about
 * access to the box account.
 */
public class CloudStorage {

    private static final String INDENT_APPEND = "    ";
	private static final String MISSING_FOLDER_MESSAGE = "This folder is not located in your box account";
	private static final String LOGIN_MESSAGE = "Thanks for logging in!";
	private String devToken;
    private BoxFolder rootFolder;
    private BoxFolder currentFolder;
    private TextBot tb;

    public CloudStorage (String dt) throws FileNotFoundException {
        this.devToken = dt;
        BoxAPIConnection api = new BoxAPIConnection(devToken);
        System.out.println(LOGIN_MESSAGE); 
        this.rootFolder = BoxFolder.getRootFolder(api);
        this.currentFolder = this.rootFolder;
        tb = null;
    }

    /**
     * Sets up text bot so that notifications can be sent.
     * 
     * @param	resources is the file path to the resource file with listings of numbers to be texted
     * 			(visit the README for speicifcations)
     * @param	accountSID is an account String that is obtained from the Twilio website.
     * @param	authToken is an authentication string that is paired with accountSID. This is also
     * 			obtained from Twilio
     * @param	String number is the Twilio number assigned to your account.
     */
    public void setUpNotifications(String resources, String accountSID, String authToken, String number){
    	this.tb = new TextBot(resources, accountSID, authToken, number);
    }
    /**
     * Non-recursive method that lists the folders in the current directory.
     */
    public void listFolders () {
        listFolder(this.currentFolder, 0);
    }
    /**
     * Recurisvely lists folders in the Box account. Primarily used for debugging.
     * 
     * @param	folder is the BoxFolder from which to start listing files.
     * @param	depth is how far the recursive listing should go.
     */
    private static void listFolder (BoxFolder folder, int depth) {
        String indent = "";
        for (BoxItem.Info itemInfo : folder) {
            for (int i = 0; i < depth; i++) {
                indent += INDENT_APPEND;
            }
            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                listFolder(childFolder, depth + 1);
            }
        }
    }

    /**
     * creates a new folder in the Box account
     * 
     * @param	String name is the name that the folder should be given.
     */
    public void createNewFolder (String name) {
        this.currentFolder.createFolder(name);
    }
    
    /**
     * Uploads a local file to the Box account in a specific folder
     * 
     * @param	String filePath determines the path to the local folder that should be uploaded
     * @param	String folderName is the name of the Box Folder that the file should be uploaded to
     * @param	String name is the new name that should be assigned to the file upon being uploaded.
     */
    public void uploadFileToFolder (String filePath,
                                    String folderName,
                                    String name) throws FileNotFoundException {
        BoxFolder f = this.getFolder(folderName);
        if (f == null) {
            throw new RuntimeException("This folder does not exist on your box");
        }
        InputStream is = new FileInputStream(new File(filePath));
        f.uploadFile(is, name);
    }

    public void uploadFile (String filePath, String name) throws FileNotFoundException {
        File f = new File(filePath);
        InputStream is = new FileInputStream(f);
        currentFolder.uploadFile(is, name);
    }
    
    /**
     * Sets the current folder back to the root of the Box account
     */
    public void goToRootFolder(){
    	this.currentFolder = this.rootFolder;
    }
    
    /**
     * Returns a specific folder in the Box account
     * 
     * @param	String name is the name of the folder that is returned
     * @return	BoxFolder with the same name that was searched for. Null if not found.
     */
    private BoxFolder getFolder (String name) {
        return this.getFolder(rootFolder, name);
    }

    /**
     * Recursively searches through the Box account in order to find a folder.
     * 
     * @param	current is the Box directory from which the search is started.
     * @param	String name is the name of the folder being searched for.
     * @return	returns null if the folder is not found.
     */
    private BoxFolder getFolder (BoxFolder current, String name) {
        if (current.getInfo().getName().equals(name)) {
            return current;
        }
        else {
            for (BoxItem.Info info : current) {
                if (info instanceof BoxFolder.Info) {
                    BoxFolder res = getFolder((BoxFolder) info.getResource(), name);
                    if (res != null) {
                        return res;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Changes the current Box directory to the folder specified.
     * 
     * @param	String name indicates which folder to move to in the Box account
     */
    public void goToFolder (String name) {
        BoxFolder f = this.getFolder(name);
        if (f == null) {
            throw new RuntimeException("This folder does not exist in your box account");
        }
        this.currentFolder = f;
    }

    /**
     * public method that the user can call in order to upload a folder.
     * 
     * @param	String folder is the name of the folder to be uploaded (full file path)
     */
    public void uploadFolder (String folder) throws FileNotFoundException, TwilioRestException {
        File f = new File(folder);
        uploadFolder(this.currentFolder, f);
    }
    
    /**
     * Sends a text message to all of the numbers stored in the properties file.
     * 
     * @param	String message is the text that will be sent.
     */
    public void notify(String message) throws TwilioRestException{
    	tb.sendTextToAll(message);
    }
    
    /**
     * Recursively uploads files to Box account
     * 
     * @param	current is the Box directory that the files should be uploaded to
     * @param	folder is the File that is to be uploaded.
     */
    private void uploadFolder (BoxFolder current, File folder) throws FileNotFoundException {
        if (folder.isDirectory()) {
            current.createFolder(folder.getName());
            for (File child : folder.listFiles()) {
                uploadFolder(this.getFolder(folder.getName()), child);
            }
        }
        else {
            current.uploadFile(new FileInputStream(folder), folder.getName());
        }
    }

    /**
     * Deletes a folder from the Box account
     * 
     * @param	name is the name of the folder to be deleted.
     */
    public void deleteFolder (String name) {
        BoxFolder f = this.getFolder(name);
        if (f == null) {
            throw new RuntimeException(MISSING_FOLDER_MESSAGE);
        }
        f.delete(true);
    }
    
    /**
     * Returns a list of names for the files and folders in the current Box directory
     * 
     * @return	List<String> contains names of the files in the Box folder. Directory names are also
     *			followed by ' -- folder' in order to differentiate them from ordinary folders.
     */
    public List<String> getCurrentFiles(){
    	List<String> files = new ArrayList<String>();
    	for(BoxItem.Info info : this.currentFolder){
    		String addition = info.getName();
    		if(info instanceof BoxFolder.Info){
    			addition += " -- folder";
    		}
    		files.add(addition);
    	}
    	return files;
    }
    
    /**
     * downloads a folder or file from the current directory
     * 
     * @param	name is the name of the file/folder to be downloaded from Box
     * @param	location is file path that indicates where the download will be saved
     */
    public void downloadFromCurrent(String name, String location) throws FileNotFoundException{
    	File f = new File(location+"/"+name);
    	for (BoxItem.Info itemInfo : this.currentFolder) {
    	    if (itemInfo instanceof BoxFile.Info && itemInfo.getName().equals(name)) {
    	        BoxFile.Info fileInfo = (BoxFile.Info) itemInfo;
    	        fileInfo.getResource().download(new FileOutputStream(f));
    	    } else if (itemInfo instanceof BoxFolder.Info && itemInfo.getName().equals(name)) {
    	        BoxFolder.Info folderInfo = (BoxFolder.Info) itemInfo;
    	        f.mkdirs();
    	        BoxFolder prev = this.currentFolder;
    	        this.goToFolder(name);
    	        for(BoxItem.Info child : folderInfo.getResource()){
    	        	this.downloadFromCurrent(child.getName(), location+"/"+name);
    	        }
    	        this.currentFolder = prev;
    	    }
    	}
    }

}
