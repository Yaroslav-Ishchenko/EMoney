package ua.ishchenko.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import ua.ishchenko.client.mvp.view.IUsersView;
import ua.ishchenko.client.mvp.view.IWalletsView;
import ua.ishchenko.client.mvp.view.ITransactionsView;
import ua.ishchenko.client.mvp.view.mail.WalletsView;
import ua.ishchenko.client.mvp.view.transactions.TransactionsView;
import ua.ishchenko.client.mvp.view.users.UsersView;

public class ClientFactoryImpl implements ClientFactory {
	private final EventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(
			eventBus);

	private IWalletsView mailView;
	private IUsersView usersView;
	private ITransactionsView transactionsView;
	private static ClientServiceFactory serviceFactory;

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public IWalletsView getWalletsView() {
		if (mailView == null)
			this.mailView = new WalletsView();
		return mailView;
	}

	@Override
	public IUsersView getUsersView() {
		if (usersView == null)
			this.usersView = new UsersView();
		return usersView;
	}

	@Override
	public ITransactionsView getTransactionsView() {
		if (transactionsView == null)
			this.transactionsView = new TransactionsView();
		return transactionsView;
	}

	@Override
	public ClientServiceFactory getServiceFactory() {
		if (serviceFactory == null) {
			serviceFactory = new ClientServiceFactoryImpl();
		}
		return serviceFactory;
	}
}
