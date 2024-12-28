package entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Sale {
	private List<Product> products;
	private Instant saleDate;
	private double discount;
	
	public Sale() {
		products = new ArrayList<>();
		saleDate = Instant.now();
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public Instant getSaleDate() {
		return saleDate;
	}
	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double calcTotal() {
		double soma = 0;
		
		for(Product p : products) {
			soma += p.getPrice() * p.getQuantity(); 
		}
		
		return soma - discount;
	}
	
	public void print() {
		System.out.println("---------Lista de Produtos---------");
		for(Product p : products) {
			
			System.out.println(p.getBarCode() + "|" + p.getDescription() + "|" + p.getPrice() + "|" + p.getQuantity());
			
		}
		
		if(getDiscount() != 0) {
			System.out.println("Desconto: " + getDiscount());
		}
		
		System.out.println("Total: R$" + calcTotal());
		System.out.println("-----------------------------------");
	}
	
}
