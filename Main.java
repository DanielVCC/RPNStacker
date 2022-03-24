import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

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

		String path = "C:\\sources\\Calc1.stk";
		Stack<Double> stack = new Stack<Double>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				// ou para considerar valores negativos
				if (Character.isDigit(line.charAt(0)) || line.length() > 1 && line.charAt(0) == '-')
					stack.add(Double.parseDouble(line));
				else
					stack.add(operation(stack.pop(), stack.pop(), line));
				line = br.readLine();
			}
			System.out.println(stack.peek());

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
