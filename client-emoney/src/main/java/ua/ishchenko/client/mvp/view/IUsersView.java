package ua.ishchenko.client.mvp.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface IUsersView extends IsWidget {
	public void setPresenter(IContactsPresenter presenter);
	
	public interface IContactsPresenter {
	}
}
