package com.yama;

import com.yama.utilities.Config;

public class Constants {

	/* c2dm parameters */
	public final static String C2DM_EMAIL = Config.C2DM_EMAIL;
	public final static String SERVER_URL = Config.SERVER_URL;
	public final static String REGISTRATION_URL = SERVER_URL + "/c2dmregister";
	public final static String USRLIST_URL = SERVER_URL + "/userlist";
	public final static String SEND_MESSAGE_URL = SERVER_URL + "/sendMorseMessage";

	/* Preference files parameters */
	public final static String PREF_FILE = "UserPref";
	public final static String C2DM_ID_KEY = "c2dm_id";
	public final static String USER_ID_KEY = "UUID";
	
	/* Notification Id */
	public final static int NOTIFICATION_NEW_MESSAGE_ID = 1 ;
	
	/* Youtube tutorial Address */
	public final static String TUTO_VID_ADDRESS = "http://www.youtube.com/watch?v=DQj74Y2H8xQ&feature=share&list=PL3EAE5C2180CE2F0D";
}
