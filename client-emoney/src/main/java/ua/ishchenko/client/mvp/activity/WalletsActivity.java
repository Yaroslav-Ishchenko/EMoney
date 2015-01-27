package ua.ishchenko.client.mvp.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import ua.ishchenko.client.EMoneyConstants;
import ua.ishchenko.client.ClientFactory;
import ua.ishchenko.client.mvp.view.IWalletsView;

public class WalletsActivity extends AbstractMainActivity implements IWalletsView.IMailPresenter {
	private ClientFactory clientFactory;
	
	public WalletsActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		applyCurrentLinkStyle(EMoneyConstants.USERS_LINK_ID);
		
		final IWalletsView view = clientFactory.getWalletsView();
		view.setPresenter(this);
		container.setWidget(view.asWidget());
	}
}
