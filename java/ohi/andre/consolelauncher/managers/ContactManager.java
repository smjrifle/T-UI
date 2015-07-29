package ohi.andre.consolelauncher.managers;

import java.util.HashMap;
import java.util.Set;

import ohi.andre.comparestring.Compare;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactManager {

	private HashMap<String, String> contacts;
	
	public ContactManager(Context c) {
		Cursor phones = c.getContentResolver()
				.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);
		
		contacts = new HashMap<>();
		while (phones.moveToNext())
		{
			String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			contacts.put(name, phoneNumber);
		}
		phones.close();
	}
	
	public Set<String> names() {
		return contacts.keySet();
	}
	
	public String getNumber(String name) {
		String mostSuitable = Compare.compare(contacts.keySet(), name);
		if(mostSuitable != null)
			return contacts.get(mostSuitable);
		return null;
	}
}
