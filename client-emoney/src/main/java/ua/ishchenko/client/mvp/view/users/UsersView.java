package ua.ishchenko.client.mvp.view.users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ua.ishchenko.client.mvp.view.IUsersView;

public class UsersView extends Composite implements IUsersView {
	interface ContactsViewUiBinder extends UiBinder<Widget, UsersView> {	}
	private static ContactsViewUiBinder uiBinder = GWT.create(ContactsViewUiBinder.class);
	
	private IContactsPresenter presenter;
	
	public UsersView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(IContactsPresenter presenter) {
		this.presenter = presenter;
	}
}
