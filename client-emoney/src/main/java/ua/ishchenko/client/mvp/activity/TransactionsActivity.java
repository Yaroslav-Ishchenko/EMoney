package ua.ishchenko.client.mvp.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ua.ishchenko.client.EMoneyConstants;
import ua.ishchenko.client.ClientFactory;
import ua.ishchenko.client.mvp.view.ITransactionsView;
import ua.ishchenko.common.wsbeans.User;
import ua.ishchenko.common.wsbeans.WSResultCode;
import ua.ishchenko.common.wsbeans.Wallet;

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
        User usr = new User("GWT");
        Wallet wlt = new  Wallet();
        wlt.setBalance(2988L);
        usr.setWallet(wlt);
        clientFactory.getServiceFactory().getUserService().createUser(usr, new MethodCallback<WSResultCode>() {

            @Override
            public void onSuccess(Method method, WSResultCode response) {
                Window.alert(response.getSummary());

            }

            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert(method.getResponse().getStatusText());

            }
        });
	}
}
