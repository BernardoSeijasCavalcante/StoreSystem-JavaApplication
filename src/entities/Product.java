package entities;

import java.time.Instant;
import java.util.Objects;

import resources.Category;
import resources.CategoryTax;
import resources.Tax;
import resources.TaxException;
import resources.absoluteTax;

public class Product {
	private Integer barCode;
	private String description;
	private Double price;
	private Integer quantity;
	private Category category;
	private Tax tax;
	private Instant registerDate;
	private Instant updateDate;
	
	public Product(Integer barCode, String description, Double price,Integer quantity, String category, String tax, String registerDate,
			String updateDate) {
		this.barCode = barCode;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		
		this.registerDate = Instant.parse(registerDate);
		this.updateDate = Instant.parse(updateDate);
		
		try {
			this.category = Category.valueOf(category);

			if(tax.equals("CategoryTax")){
				this.tax = new CategoryTax(this.category);
			}else if(tax.equals("AbsoluteTax")) {
				this.tax = new absoluteTax();
			}else {
				throw new TaxException();
			}
			
		}catch(TaxException e) {
			System.out.println(e.getMessage());
		}catch(IllegalArgumentException e) {
			System.out.println("Erro: Categoria não identificada! : " + e.getMessage());
		}
	}

	public Integer getBarCode() {
		return barCode;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
		upUpdateDate();
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		upUpdateDate();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(String category) {
		try {
			this.category = Category.valueOf(category);
			upUpdateDate();
		}catch(IllegalArgumentException e) {
			System.out.println("Erro: Categoria não identificada! : " + e.getMessage());
		}
		
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(String tax) throws TaxException {
		if(tax.equals("CategoryTax")){
			this.tax = new CategoryTax(this.category);
			upUpdateDate();
		}else if(tax.equals("AbsoluteTax")) {
			this.tax = new absoluteTax();
			upUpdateDate();
		}else {
			throw new TaxException();
		}
		
		//
	}

	public Instant getRegisterDate() {
		return registerDate;
	}

	public Instant getUpdateDate() {
		return updateDate;
	}

	public void upUpdateDate() {
		this.updateDate = Instant.now();
	}
	
	public void down(Product p) {
		this.quantity -= p.getQuantity();
	}
	
	public void calcTax() {
		this.getTax().calcTax(getQuantity() * getPrice());
	}

	public String toStringInSale() {
		return getBarCode() + "|" + getDescription() + "|" + getPrice() + "|" + getQuantity();
	}
	
	@Override
	public String toString() {
		return getBarCode() + "," + getDescription() + "," + getPrice() + "," + getQuantity() + "," 
	+ getCategory().toString() + "," + getTax().toString() + "," + getRegisterDate().toString() + "," + getUpdateDate().toString(); 
	}
	
	
	
}
