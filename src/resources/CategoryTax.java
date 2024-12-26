package resources;

public class CategoryTax implements Tax {
	private Category category;

	public CategoryTax(Category category) {
		this.category = category;
	}

	public double calcTax(double amount) {

		if (category == Category.BAKERY) {
			return amount * 0.1;
		}
		
		if(category == Category.BUTCHERY) {
			return amount * 0.1;
		}
		
		if(category == Category.DRINKS) {
			return amount * 0.05 + 1;
		}
		
		if(category == Category.FOODS) {
			return amount * 0.3;
		}

		if (category == Category.HYGIENIC) {
			return amount * 0.02;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "CategoryTax";
	}
}
