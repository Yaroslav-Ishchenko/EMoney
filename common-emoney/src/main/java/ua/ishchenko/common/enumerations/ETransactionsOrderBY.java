package ua.ishchenko.common.enumerations;

public enum ETransactionsOrderBY {
	ID_ASC(1, " id ", " ASC"), ID_DESC(2, " id ", " DESC"), USER_ID_ASC(3,
			" userid ", " ASC"), USERID_DESC(4, " userid ", " DESC"), USER_NAME_ASC(
			5, " username ", " ASC"), USER_NAME_DESC(6, " username ", " DESC"), AMOUNT_ASC(
			7, " amount ", " ASC"), AMOUNT_DESC(8, " amount ", " DESC"), DATE_TIME_ASC(
			9, " dateTime ", " ASC"), DATE_TIME_DESC(10, " dateTime ", " DESC"), STATUS_ASC(
			11, " status ", " ASC"), STATUS_DESC(12, " status ", " DESC");

	private String orderBy, order;
	private int number;

	ETransactionsOrderBY(int number, String orderBy, String order) {
		this.orderBy = orderBy;
		this.order = order;
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getOrder() {
		return order;
	}

	public static ETransactionsOrderBY getETransactionsOrderBY(Integer number) {
		if (number == null)
			return null;
		for (ETransactionsOrderBY iterable_element : ETransactionsOrderBY
				.values()) {
			if (iterable_element.getNumber() == number) {
				return iterable_element;
			}
		}
		return null;
	}

	public String toString() {
		return orderBy + order;
	}

}
