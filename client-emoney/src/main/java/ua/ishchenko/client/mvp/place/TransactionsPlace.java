package ua.ishchenko.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class TransactionsPlace extends Place {
	private static final String VIEW_HISTORY_TOKEN = "transactions";
	
	public TransactionsPlace() { }
	
	@Prefix(value = VIEW_HISTORY_TOKEN)
	public static class Tokenizer implements PlaceTokenizer<TransactionsPlace> {
		@Override
		public TransactionsPlace getPlace(String token) {
			return new TransactionsPlace();
		}

		@Override
		public String getToken(TransactionsPlace place) {
			return "";
		}
	}
}
