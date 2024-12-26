package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static Scanner sc = new Scanner(System.in);
	public static Random random = new Random();
	
	public static List<Product> products = new ArrayList<>();	
	
	public static File file = new File("C:\\Users\\Bernado\\Desktop\\REGISTROS\\CADASTROS.txt");
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		String resp = "nothing";
		
		Resgatar();
		
		while(!resp.equals("Sair")) {
			System.out.println("Digite:");
			System.out.println("'Cadastrar' - Para cadastrar produtos.");
			System.out.println("'Sair' - Para sair do sistema.");
			resp = sc.next();
			
			if(resp.equals("Cadastrar")) {
				Cadastrar();
			}
			
		}
		
		sc.close();
		
	}
	
	public static void Resgatar() {
		try(BufferedReader br = new BufferedReader(new FileReader(file.getPath()))){
			while(br.ready()) {
				String[] split = br.readLine().split(",");
				
				products.add(new Product(Integer.parseInt(split[0]),split[1],Double.parseDouble(split[2]),Integer.parseInt(split[3]),split[4],split[5],split[6],split[7]));
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void Cadastrar() {
		
		sc.nextLine();
		System.out.println("Qual é a descrição do produto?");
		String description = sc.nextLine();
		
		System.out.println("Qual é o preço do produto?");
		Double price = sc.nextDouble();
		
		System.out.println("Qual é a quantidade em estoque?");
		Integer quantity = sc.nextInt();
		
		System.out.println("Qual é a categoria do produto?");
		String category = sc.next();
		
		System.out.println("Qual é o sistema de taxação do produto?");
		String tax = sc.next();
		
		Integer barCode = random.nextInt();
		
		products.add(new Product(barCode,description,price,quantity, category, tax, Instant.now().toString(), Instant.now().toString()));
		if(products.get(products.size() - 1).getTax() != null) {
			Salvar();
		}
	}
	
	public static void Salvar() {
		try(BufferedWriter bf = new BufferedWriter(new FileWriter(file.getPath()))){
			for(Product p : products) {	
				bf.write(p.toString());
				bf.newLine();
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
