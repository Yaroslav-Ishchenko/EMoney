package ua.ishchenko.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import ua.ishchenko.client.mvp.view.ITransactionsView;
import ua.ishchenko.client.mvp.view.IUsersView;
import ua.ishchenko.client.mvp.view.IWalletsView;
public interface ClientFactory {
	
	public EventBus getEventBus();
	
	public PlaceController getPlaceController();

	public IWalletsView getWalletsView();

	public IUsersView getUsersView();

	public ITransactionsView getTransactionsView();

	ClientServiceFactory getServiceFactory();

}
