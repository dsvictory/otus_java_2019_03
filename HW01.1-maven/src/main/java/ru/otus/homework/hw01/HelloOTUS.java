package ru.otus.homework.hw01;

import com.google.common.base.*;

public class HelloOTUS {

	public static void main(String[] args) {
		Joiner joiner = Joiner.on("; ").skipNulls();
		System.out.print(joiner.join("Harry", null, "Ron", "Hermione"));
	}

}