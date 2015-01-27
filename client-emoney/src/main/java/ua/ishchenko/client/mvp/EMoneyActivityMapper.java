package ua.ishchenko.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import ua.ishchenko.client.ClientFactory;
import ua.ishchenko.client.mvp.activity.TransactionsActivity;
import ua.ishchenko.client.mvp.activity.UsersActivity;
import ua.ishchenko.client.mvp.activity.WalletsActivity;
import ua.ishchenko.client.mvp.place.TransactionsPlace;
import ua.ishchenko.client.mvp.place.UsersPlace;
import ua.ishchenko.client.mvp.place.WalletsPlace;

public class EMoneyActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;
	
	public EMoneyActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof WalletsPlace) {
			return new WalletsActivity(clientFactory);
		} else if (place instanceof UsersPlace) {
			return new UsersActivity(clientFactory);
		} else if (place instanceof TransactionsPlace) {
			return new TransactionsActivity(clientFactory);
		}
		return null;
	}
}
