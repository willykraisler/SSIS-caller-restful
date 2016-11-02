package co.com.bnpparibas.cardif.model.utils;

public class TypeNamedGeneric {
		
	public static String takeNameClass(String name){
		return name.substring(name.lastIndexOf('.') + 1);
	}
}
