package aplicacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entidades.Produto;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		
		List<Produto> produtos = new ArrayList<>();
		
		System.out.println("Informe o diretório de itens vendidos: ");
		String strDiretorio = sc.nextLine();
		
		File arquivoOrigemStr = new File(strDiretorio);
		String pastaOrigemStr = arquivoOrigemStr.getParent();
		
		boolean sucesso = new File(pastaOrigemStr + "\\saida").mkdir();
		
		String arquivoDestinoStr = pastaOrigemStr + "\\saida\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(strDiretorio))) {
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				String[] fields = itemCsv.split(",");
				String nome = fields[0];
				Double valor = Double.parseDouble(fields[1]);
				Integer quantidade = Integer.parseInt(fields[2]);
				
				produtos.add(new Produto(nome, valor, quantidade));
				
				itemCsv = br.readLine();
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDestinoStr))) {
				for (Produto item : produtos) {
					bw.write(item.getNome() + ";" + String.format("%.2f", item.calculaTotal()));
					bw.newLine();
				}
				if (sucesso == true) {
					System.out.println("Diretório " + pastaOrigemStr + "\\saida criado com sucesso");
				}
				System.out.println("Arquivo summary.csv gerado com sucesso");
			}
			catch (IOException e) {
				System.out.println("Erro ao gerar o arquivo: " + e.getMessage());
			}
		}
		catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		sc.close();
	}
}