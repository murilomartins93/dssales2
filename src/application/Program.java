package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
	
		List<Sale> sales = new ArrayList<>();
	
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String line = br.readLine();
			
			while(line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items =	Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				sales.add(new Sale(month, year, seller, items, total));		
				line = br.readLine();
			}	
					
			Set<String> names = sales.stream()
					.map(x -> x.getSeller())
					.collect(Collectors.toSet());
			
			System.out.println("\nTotal de vendas por vendedor:");
			for(String n : names) {
				double sum = sales.stream()
						.filter(x -> x.getSeller().contains(n))
						.map(x -> x.getTotal())
						.reduce(0.0, (x, y) -> x + y);					
				System.out.println(n + " - R$ " + String.format("%.2f", sum));
			}
								
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
