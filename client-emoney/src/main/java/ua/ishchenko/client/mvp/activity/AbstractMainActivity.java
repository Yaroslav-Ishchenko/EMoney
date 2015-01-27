package ua.ishchenko.client.mvp.activity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import ua.ishchenko.client.EMoneyConstants;

public abstract class AbstractMainActivity extends AbstractActivity {
	private static Map<String, Element> navLinks = new LinkedHashMap<String, Element>();
	static {
		navLinks.put(EMoneyConstants.WALLETS_LINK_ID, DOM.getElementById(EMoneyConstants.WALLETS_LINK_ID));
		navLinks.put(EMoneyConstants.USERS_LINK_ID, DOM.getElementById(EMoneyConstants.USERS_LINK_ID));
		navLinks.put(EMoneyConstants.TRANSACTIONS_LINK_ID, DOM.getElementById(EMoneyConstants.TRANSACTIONS_LINK_ID));
	}
	
	public void applyCurrentLinkStyle(String viewId) {
		for (String linkId : navLinks.keySet()) {
			final Element link = navLinks.get(linkId);
			if (link == null) continue;
			if (linkId.equals(viewId)) {
				link.addClassName("b-current");
			} else {
				link.removeClassName("b-current");
			}
		}
	}
}
