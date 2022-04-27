import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import entities_enum.OrderStatus;

public class Main {

	public static Double operation(Double sndDigit, Double fstDigit, String operator) {

		if (operator.equals("+"))
			return fstDigit + sndDigit;

		else if (operator.equals("-"))
			return fstDigit - sndDigit;

		else if (operator.equals("*"))
			return fstDigit * sndDigit;

		else
			return fstDigit / sndDigit;
	}

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter file path: ");
		String path = scan.nextLine();
		Stack<Double> stack = new Stack<Double>();
		ArrayList<Token> tokens = new ArrayList<Token>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();

			while (line != null) {
				//verifica se a linha equivale a algum operador
				if (line.equals("+"))
					tokens.add(new Token(TokenType.valueOf("PLUS"), line));
				else if (line.equals("-"))
					tokens.add(new Token(TokenType.valueOf("MINUS"), line));
				else if (line.equals("*"))
					tokens.add(new Token(TokenType.valueOf("STAR"), line));
				else if (line.equals("/"))
					tokens.add(new Token(TokenType.valueOf("SLASH"), line));
				
				/* Ja que a linha nao e aquivalente a um caractere unico equivalente a
				 * um operador, verificaremos cada caracter da linha para confirmar que
				 * se trata ou nao de uma sequencia equivalente a um numero */
				
				else {
					String value = "";
					for(int i = 0; i < line.length(); i++) {
						if(Character.isDigit(line.charAt(i)) || i == 0 && line.charAt(i) == '-')
							value = value + line.charAt(i);
						else
							throw new UnexpectedCharacterException("Unexpected character: " + line.charAt(i));
					}
					tokens.add(new Token(TokenType.valueOf("NUM"), value));
				} 
				line = br.readLine();
			}

			// lista de tokens sera interpretada para as operacoes com a pilha
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).type.equals(TokenType.valueOf("NUM"))) {
					stack.add(Double.parseDouble(tokens.get(i).lexeme));
					//print token
					System.out.println(tokens.get(i).toString());
				} else {
					stack.add(operation(stack.pop(), stack.pop(), tokens.get(i).lexeme));
					//print token
					System.out.println(tokens.get(i).toString());
				}
			}

			System.out.println("\nSaida: " + stack.peek());

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (UnexpectedCharacterException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		scan.close();

	}

}
