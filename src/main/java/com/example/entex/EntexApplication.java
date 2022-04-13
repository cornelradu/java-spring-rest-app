package com.example.entex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;



@SpringBootApplication
public class EntexApplication {
  	public static String countyCsvFile;
        public static String localityCsvFile;
        public static boolean countiesAdded = false;
        public static boolean localitiesAdded = false;

	public static void main(String[] args) {
                countyCsvFile = args[0];
                localityCsvFile = args[1];
		SpringApplication.run(EntexApplication.class, args);
	}

}
