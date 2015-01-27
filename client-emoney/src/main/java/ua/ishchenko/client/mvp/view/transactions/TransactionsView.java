package ua.ishchenko.client.mvp.view.transactions;

import ua.ishchenko.client.mvp.view.ITransactionsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TransactionsView extends Composite implements ITransactionsView {
	interface TransactionsViewUiBinder extends
			UiBinder<Widget, TransactionsView> {
	}

	private static TransactionsViewUiBinder uiBinder = GWT
			.create(TransactionsViewUiBinder.class);

	private ITransactionsPresenter presenter;

	public TransactionsView()

	{

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(ITransactionsPresenter presenter) {

		this.presenter = presenter;
	}
}
