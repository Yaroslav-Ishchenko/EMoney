package ua.ishchenko.client.mvp.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ua.ishchenko.client.EMoneyConstants;
import ua.ishchenko.client.ClientFactory;
import ua.ishchenko.client.mvp.view.ITransactionsView;

public class TransactionsActivity extends AbstractMainActivity implements ITransactionsView.ITransactionsPresenter {
	private ClientFactory clientFactory;
	
	public TransactionsActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		applyCurrentLinkStyle(EMoneyConstants.TRANSACTIONS_LINK_ID);
		
		final ITransactionsView view = clientFactory.getTransactionsView();
		view.setPresenter(this);
		container.setWidget(view.asWidget());
	}
}
