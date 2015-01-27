package ua.ishchenko.client.mvp.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface IWalletsView extends IsWidget {
	public void setPresenter(IMailPresenter presenter);
	
	public interface IMailPresenter {
	}
}
