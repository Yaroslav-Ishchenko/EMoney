package ua.ishchenko.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class UsersPlace extends Place {
	private static final String VIEW_HISTORY_TOKEN = "users";
	
	public UsersPlace() { }
	
	@Prefix(value = VIEW_HISTORY_TOKEN)
	public static class Tokenizer implements PlaceTokenizer<UsersPlace> {
		@Override
		public UsersPlace getPlace(String token) {
			return new UsersPlace();
		}

		@Override
		public String getToken(UsersPlace place) {
			return "";
		}
	}
}
