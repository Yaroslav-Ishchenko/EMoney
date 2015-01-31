package ua.ishchenko.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import ua.ishchenko.client.layout.EMoneyLayout;
import ua.ishchenko.client.mvp.EMoneyActivityMapper;
import ua.ishchenko.client.mvp.EMoneyPlaceHistoryMapper;
import ua.ishchenko.client.mvp.place.WalletsPlace;

public class EMoneyEntryPoint implements EntryPoint {
	private SimplePanel containerWidget;
	private WalletsPlace defaultPlace = new WalletsPlace();
	
	@Override
	public void onModuleLoad() {
		final EMoneyLayout mainLayout = new EMoneyLayout();
		containerWidget = mainLayout.getAppContentHolder();
		
		final ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// activate activity manager and init display
		ActivityMapper activityMapper = new EMoneyActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(containerWidget);
		
		// display default view with activated history processing
		EMoneyPlaceHistoryMapper historyMapper = GWT.create(EMoneyPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		RootLayoutPanel.get().add(mainLayout);
		
		History.newItem("#wallets:");
	}
}
