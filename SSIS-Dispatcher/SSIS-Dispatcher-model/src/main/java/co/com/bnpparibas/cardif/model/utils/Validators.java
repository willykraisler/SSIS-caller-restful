package co.com.bnpparibas.cardif.model.utils;

import co.com.bnpparibas.cardif.model.exception.PersistenceException;

public class Validators {

	public static void checkArgument(Boolean valid, PersistenceException exception) throws PersistenceException {
		
		if(!valid)
			throw exception;
		
	}
	
}
