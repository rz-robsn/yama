package robsoninc.morse;

import robsoninc.morse.utilities.Config;

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
}
