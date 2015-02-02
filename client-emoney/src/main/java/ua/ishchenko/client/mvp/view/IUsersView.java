package ua.ishchenko.client.mvp.view;

import java.util.List;

import ua.ishchenko.common.wsbeans.User;

import com.google.gwt.user.client.ui.IsWidget;

public interface IUsersView extends IsWidget {
	public void setPresenter(IContactsPresenter presenter);
	
	public interface IContactsPresenter {
	}

	void setDataGridRowData(List<User> userList);
}
