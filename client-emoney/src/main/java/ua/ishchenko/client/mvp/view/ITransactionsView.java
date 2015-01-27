package ua.ishchenko.client.mvp.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface ITransactionsView extends IsWidget {
	
	public void setPresenter(ITransactionsPresenter presenter);
	
	public interface ITransactionsPresenter {
	}
}
