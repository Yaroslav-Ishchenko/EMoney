package ua.ishchenko.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class WalletsPlace extends Place {
	private static final String VIEW_HISTORY_TOKEN = "wallets";
	
	public WalletsPlace() { }
	
	@Prefix(value = VIEW_HISTORY_TOKEN)
	public static class Tokenizer implements PlaceTokenizer<WalletsPlace> {
		@Override
		public WalletsPlace getPlace(String token) {
			return new WalletsPlace();
		}

		@Override
		public String getToken(WalletsPlace place) {
			return "";
		}
	}
}
