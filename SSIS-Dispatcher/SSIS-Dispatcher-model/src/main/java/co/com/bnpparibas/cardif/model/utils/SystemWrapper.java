package co.com.bnpparibas.cardif.model.utils;

import javax.annotation.Nonnull;

import com.google.inject.Singleton;

@Singleton
public class SystemWrapper {

	public @Nonnull String getProperty(@Nonnull String key){
		
		String value = System.getProperty(key);
		
		if(value == null)
			throw new IllegalStateException("Es necesario definir " + key + " en los parametros de ejecución.");
		
		return value;
	}
	
}
