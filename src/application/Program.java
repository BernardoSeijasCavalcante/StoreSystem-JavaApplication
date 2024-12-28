package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import entities.Product;
import entities.Sale;
import resources.TaxException;

public class Program {

	public static Scanner sc = new Scanner(System.in);
	public static Random random = new Random();

	public static List<Product> products = new ArrayList<>();

	public static File file = new File("C:\\Users\\Bernado\\Desktop\\REGISTROS\\CADASTROS.txt");
	public static File file2 = new File("C:\\Users\\Bernado\\Desktop\\REGISTROS\\VENDAS.txt");

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);

		String resp = "nothing";

		Resgatar();

		while (!resp.equals("Sair")) {
			System.out.println("Digite:");
			System.out.println("'Cadastrar' - Para cadastrar produtos.");
			System.out.println("'Consultar' - Para consultar produtos.");
			System.out.println("'Editar' - Para editar produtos.");
			System.out.println("'Venda' - Para vender produtos.");
			System.out.println("'Sair' - Para sair do sistema.");
			resp = sc.next();

			if (resp.equals("Cadastrar")) {
				Cadastrar();
			}
			if (resp.equals("Consultar")) {
				Consultar();
			}
			if (resp.equals("Editar")) {
				Editar();
			}
			if (resp.equals("Venda")) {
				Venda();
			}

		}

		sc.close();

	}

	public static void Venda() {

		Sale sale = new Sale();
		List<Product> venda = sale.getProducts();

		String resp = "nothing";

		while (!resp.equals("Sair")) {
			System.out.println("Digite: ");
			System.out.println("'Produto' - Para adicionar um produto ao pedido.");
			System.out.println("'Desconto' - Para dar desconto.");
			System.out.println("'Fechar' - Para fechar a venda.");
			System.out.println("'Sair' Para sair e/ou cancelar a operação.");

			resp = sc.next();

			if (resp.equals("Produto")) {

				System.out.println("Insira o código de barras do produto:");
				resp = sc.next();

				String[] filter = resp.split("X");

				for (Product p : products) {
					if (filter.length == 2) {
						if (p.getBarCode().equals(Integer.parseInt(filter[1]))) {
							venda.add(new Product(p.getBarCode(), p.getDescription(), p.getPrice(),
									Integer.parseInt(filter[0]), p.getCategory().toString(), p.getTax().toString(),
									p.getRegisterDate().toString(), p.getUpdateDate().toString()));
						}
					} else {
						if (p.getBarCode().equals(Integer.parseInt(filter[0]))) {
							venda.add(new Product(p.getBarCode(), p.getDescription(), p.getPrice(), 1,
									p.getCategory().toString(), p.getTax().toString(), p.getRegisterDate().toString(),
									p.getUpdateDate().toString()));
						}
					}
				}
				sale.print();
			}
			if(resp.equals("Desconto")) {
				System.out.println("Insira a quantidade a ser descontada.");
				resp = sc.next();
				
				sale.setDiscount(Double.parseDouble(resp));
			}
			if(resp.equals("Fechar")) {
				for(Product v : venda) {
					for(Product p : products) {
						if(v.getBarCode().equals(p.getBarCode())) {
							p.down(v);
							break;
						}
					}
				}
				sale.print();
				Salvar();
				try (BufferedWriter bf = new BufferedWriter(new FileWriter(file2.getPath(),true))){
					
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
					
					bf.write(fmt.format(sale.getSaleDate()));
					
					bf.newLine();
					
					for(Product v: venda) {
						bf.write(v.getBarCode() + "|" + v.getDescription() + "|" + v.getPrice() + "|" + v.getQuantity());
						bf.newLine();
					}
					
					if(sale.getDiscount() != 0) {
						bf.write("Desconto: " + sale.getDiscount());
						bf.newLine();
					}
					
					bf.write("Total: R$" + sale.calcTotal());
					bf.newLine();
					
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}
				resp = "Sair";
			}

		}

	}

	public static void Editar() {
		System.out.println("Qual é o código de barras?");
		Integer barCode = sc.nextInt();

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

		for (Product p : products) {
			if (p.getBarCode().equals(barCode)) {
				String resp = "nothing";

				while (!resp.equals("Sair")) {

					System.out.println("----------------------------------");
					System.out.println("Código de Barras: " + p.getBarCode());
					System.out.println("Descrição: " + p.getDescription());
					System.out.println("Preço: R$" + p.getPrice());
					System.out.println("Quantidade em estoque: " + p.getQuantity());
					System.out.println("Categoria: " + p.getCategory().toString());
					System.out.println("Taxa: " + p.getTax().toString());
					System.out.println("Data de Registro: " + fmt.format(p.getRegisterDate()));
					System.out.println("Data da Última Atualização: " + fmt.format(p.getUpdateDate()));
					System.out.println("----------------------------------");

					System.out.println("Digite: ");
					System.out.println("Description - Para mudar a descrição do produto");
					System.out.println("Price - Para mudar o preço do produto");
					System.out.println("Quantity - Para mudar a quantidade do produto em estoque");
					System.out.println("Category - Para mudar a categoria do produto em estoque");
					System.out.println("Tax - Para mudar o sistema de taxação do produto");
					System.out.println("Sair - Para salvar as alterações feitas");
					System.out.println("-------------------------------------------------------");

					resp = sc.next();

					if (resp.equals("Description")) {
						sc.nextLine();
						System.out.println("Digite a nova descrição do produto:");
						p.setDescription(sc.nextLine());
					}
					if (resp.equals("Price")) {
						System.out.println("Digite o novo preço do produto:");
						p.setPrice(sc.nextDouble());
					}
					if (resp.equals("Quantity")) {
						System.out.println("Digite a nova quantidade do produto em estoque:");
						p.setQuantity(sc.nextInt());
					}
					if (resp.equals("Category")) {
						System.out.println("Digite a nova categoria do produto:");
						p.setCategory(sc.next());
					}
					if (resp.equals("Tax")) {
						System.out.println("Digite o novo sistema de taxação do produto:");
						try {
							p.setTax(sc.next());
						} catch (TaxException e) {
							System.out.println(e.getMessage());
						}
					}
				}
				break;
			}
		}
		Salvar();
	}

	public static void Consultar() {
		System.out.println("Qual é o código de barras?");
		Integer barCode = sc.nextInt();

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

		for (Product p : products) {
			if (p.getBarCode().equals(barCode)) {
				System.out.println("----------------------------------");
				System.out.println("Código de Barras: " + p.getBarCode());
				System.out.println("Descrição: " + p.getDescription());
				System.out.println("Preço: R$" + p.getPrice());
				System.out.println("Quantidade em estoque: " + p.getQuantity());
				System.out.println("Categoria: " + p.getCategory().toString());
				System.out.println("Taxa: " + p.getTax().toString());
				System.out.println("Data de Registro: " + fmt.format(p.getRegisterDate()));
				System.out.println("Data da Última Atualização: " + fmt.format(p.getUpdateDate()));
				System.out.println("----------------------------------");
				break;
			}
		}
	}

	public static void Resgatar() {
		try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
			while (br.ready()) {
				String[] split = br.readLine().split(",");

				products.add(new Product(Integer.parseInt(split[0]), split[1], Double.parseDouble(split[2]),
						Integer.parseInt(split[3]), split[4], split[5], split[6], split[7]));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void Cadastrar() {

		sc.nextLine();

		System.out.println("Qual é a descrição do produto?");
		String description = sc.nextLine();

		System.out.println("Qual é o preço do produto? (R$)");
		Double price = sc.nextDouble();

		System.out.println("Qual é a quantidade em estoque?");
		Integer quantity = sc.nextInt();

		System.out.println("Qual é a categoria do produto?");
		String category = sc.next();

		System.out.println("Qual é o sistema de taxação do produto?");
		String tax = sc.next();

		Integer barCode = random.nextInt();

		products.add(new Product(barCode, description, price, quantity, category, tax, Instant.now().toString(),
				Instant.now().toString()));

		if (products.get(products.size() - 1).getTax() != null) { // Se o produto que acabou de ser cadastrado tiver um
																	// sistema de taxação então...
			Salvar();

			System.out.println("-------------------------------");
			System.out.println("Produto cadastrado com sucesso!");
			System.out.println("-------------------------------");
		} else {
			products.remove(products.size() - 1);
		}
	}

	public static void Salvar() {
		try (BufferedWriter bf = new BufferedWriter(new FileWriter(file.getPath()))) {
			for (Product p : products) {
				bf.write(p.toString());
				bf.newLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
