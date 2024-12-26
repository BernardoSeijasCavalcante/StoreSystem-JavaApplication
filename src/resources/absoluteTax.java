package resources;

public class absoluteTax implements Tax{
	public double calcTax(double amount) {
		if(amount > 10 && amount < 100) {
			return amount * 0.02;
		}else if(amount >= 100) {
			return amount * 0.1;
		}else {
			return amount * 0.01;
		}
	}
	
	@Override
	public String toString() {
		return "AbsoluteTax";
	}
}
