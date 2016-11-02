package co.com.bnpparibas.cardif.model.utils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class CastUtils {
	
	public static @CheckForNull <T> T secureCast(Object object, @Nonnull Class<T> clazz) {

		if (object == null)
			return null;
		
		Class<?> paramClass = object.getClass();

		if (clazz.isAssignableFrom(paramClass))
			return clazz.cast(object) ;

		return null;
	}

}
