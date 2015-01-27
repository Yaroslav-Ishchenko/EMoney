package ua.ishchenko.common.enumerations;

public enum ETransactionStatus {
 Success(1), Failed(2);
 int status;
 ETransactionStatus(int status)
 {
	 this.status = status;
 }
 
 public ETransactionStatus getETransactionStatus(int status)
 {
	 for (ETransactionStatus iterable_element : ETransactionStatus.values()) {
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
