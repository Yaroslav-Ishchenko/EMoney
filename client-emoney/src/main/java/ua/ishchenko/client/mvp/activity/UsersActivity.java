package ua.ishchenko.client.mvp.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ua.ishchenko.client.EMoneyConstants;
import ua.ishchenko.client.ClientFactory;
import ua.ishchenko.client.mvp.view.IUsersView;
import ua.ishchenko.common.wsbeans.User;
import ua.ishchenko.common.wsbeans.WSResultCode;

import java.util.List;

public class UsersActivity extends AbstractMainActivity implements IUsersView.IContactsPresenter {
	private ClientFactory clientFactory;
    private IUsersView view;

    public UsersActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		applyCurrentLinkStyle(EMoneyConstants.USERS_LINK_ID);
		
		 view = clientFactory.getUsersView();
		view.setPresenter(this);
		container.setWidget(view.asWidget());
        getUsers();
	}

    private void getUsers() {

    clientFactory.getServiceFactory().getUserService().getUsers(new MethodCallback<List<User>>() {
        @Override
        public void onFailure(Method method, Throwable exception) {
            Window.alert(method.getResponse().getStatusText());
        }

        @Override
        public void onSuccess(Method method, List<User> response) {
            Window.alert(response.get(0).getName());
        }
    });
    }
}
