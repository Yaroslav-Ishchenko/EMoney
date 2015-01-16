package ua.ishchenko.rest.enumerations;

public enum EOperationType {
	Deposit(1), Withdraw(2);
	 int status;
	 EOperationType(int status)
	 {
		 this.status = status;
	 }
	 
	 public EOperationType getEOperationType(int status)
	 {
		 for (EOperationType iterable_element : EOperationType.values()) {
			if(iterable_element.getValue()==status)
				return iterable_element;
		}
		 return null;
	 }
	 public int getValue()
	 {
		 return this.status;
	 }
}
