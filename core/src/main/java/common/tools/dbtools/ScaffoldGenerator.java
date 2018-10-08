package common.tools.dbtools;

import java.util.Scanner;

import common.tools.dbtools.scaffold.ScaffoldGen;

public class ScaffoldGenerator {

	private static Scanner scanner;

	private static String readString5(String prompt) {
		scanner = new Scanner(System.in);
		System.out.print(prompt);
		String str = scanner.nextLine();
		return str;
	}

	public static void main(String[] args) {
		ScaffoldGen generator6 = new ScaffoldGen("testtable", "TestTable", "测试", "test_table");
		generator6.execute();
	
	}

}