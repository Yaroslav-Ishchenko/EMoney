package ua.ishchenko.client.mvp.view.mail;

import ua.ishchenko.client.mvp.view.IWalletsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WalletsView extends Composite implements IWalletsView {
	interface MailViewUiBinder extends UiBinder<Widget, WalletsView> {	}
	private static MailViewUiBinder uiBinder = GWT.create(MailViewUiBinder.class);
	
	private IMailPresenter presenter;

	public WalletsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(IMailPresenter presenter) {
		this.presenter = presenter;
	}
}
