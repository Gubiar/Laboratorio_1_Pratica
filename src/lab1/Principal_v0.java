/**
 * Lab0: Leitura de Base de Dados  N�o-Distribuida
 * 
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 20/02/2023
 * 
 * Alunos: Gustavo Lázaro e Yuri Getaruck
 * 
 * Referencias: 
 * https://docs.oracle.com/javase/tutorial/essential/io
 * 
 */
package lab1;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths.get("src\\fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				} // fim while

				System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm) throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
//					System.out.println(fortune.toString());
//
//					System.out.println(lineCount);
				} // fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}
		
/*
 * Explicação e lógica: Como o método parse faz a leitura do arquivo txt e adiciona cada fortuna em um hashMap:
 * 
 * O método read lê um fortuna aleatória desse mapa (utilizando um randomico entre 0 e o número máximo de 
 * fortunas -1;
 * 
 * O método write ficamos com umaa dúvida. É para inserir no hashMap ou no arquivo txt?
 * 	A nossa solução foi inserir no arquivo txt e no mapa hm.
 * 
 * */

		public void read(HashMap<Integer, String> hm) throws FileNotFoundException {
			SecureRandom random = new SecureRandom();
			int randomIndex = random.nextInt(NUM_FORTUNES);

			String randomFortune = hm.get(randomIndex);

			System.out.println("Fortuna lida: " + randomFortune);
		}

		public void write(HashMap<Integer, String> hm) throws FileNotFoundException {
			String newFortune = "É dureza assaltar uma loja de armas, tem armas lá!\n\t--Malvo, seriado Todo Mundo Odeia O Chris\n%";

//			Inserção no mapa
			int newIndex = NUM_FORTUNES;
			hm.put(newIndex, newFortune);
			NUM_FORTUNES++;
			
//			Inserção no .txt
			
		    try (FileWriter fw = new FileWriter(path.toString(), true)) {
		        fw.write("\n" + newFortune);
		        NUM_FORTUNES++;
		    } catch (IOException e) {
		        System.out.println("SHOW: Exceção na escrita do arquivo.");
		    }

			System.out.println("Nova fortuna adicionada: " + newFortune + "\n");
		}
	}

	public void iniciar() {
		FileReader fr = new FileReader();
		try {
			NUM_FORTUNES = fr.countFortunes();
			HashMap<Integer, String> hm = new HashMap<>();
			fr.parser(hm);
			fr.read(hm);
			fr.write(hm);
			System.out.println(hm.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Principal_v0 aux = new Principal_v0();
		aux.iniciar();
	}

}
