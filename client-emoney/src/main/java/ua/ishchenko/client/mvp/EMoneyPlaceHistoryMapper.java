package ua.ishchenko.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import ua.ishchenko.client.mvp.place.TransactionsPlace;
import ua.ishchenko.client.mvp.place.UsersPlace;
import ua.ishchenko.client.mvp.place.WalletsPlace;

@WithTokenizers({ WalletsPlace.Tokenizer.class, UsersPlace.Tokenizer.class,
		TransactionsPlace.Tokenizer.class })
public interface EMoneyPlaceHistoryMapper extends PlaceHistoryMapper {
}
