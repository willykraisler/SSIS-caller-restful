package co.com.bnpparibas.cardif.model.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


public class GenericType<T> {

	final Type type;
	final Class<T> rawType;

	public final Class<T> getRawType() {
		return rawType;
	}
	
	@SuppressWarnings("unchecked")
	public GenericType() {
		this.type = getSuperclassTypeParameter(getClass());
	    this.rawType = (Class<T>) getRawType(type);
	}

	static Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();
		
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		
		ParameterizedType parameterized = (ParameterizedType) superclass;
		return parameterized.getActualTypeArguments()[0];
//		return subclass.getTypeParameters()[0];
	}

	  public static Class<?> getRawType(Type type) {
		    if (type instanceof Class<?>) {
		      // type is a normal class.
		      return (Class<?>) type;

		    } else if (type instanceof ParameterizedType) {
		      ParameterizedType parameterizedType = (ParameterizedType) type;

		      // I'm not exactly sure why getRawType() returns Type instead of Class.
		      // Neal isn't either but suspects some pathological case related
		      // to nested classes exists.
		      Type rawType = parameterizedType.getRawType();
		      checkArgument(rawType instanceof Class,
		          "Expected a Class, but <%s> is of type %s", type, type.getClass().getName());
		      return (Class<?>) rawType;

		    } else if (type instanceof GenericArrayType) {
		      Type componentType = ((GenericArrayType)type).getGenericComponentType();
		      return Array.newInstance(getRawType(componentType), 0).getClass();

		    } else if (type instanceof TypeVariable) {
		      // we could use the variable's bounds, but that'll won't work if there are multiple.
		      // having a raw type that's more general than necessary is okay  
		      return Object.class;

		    } else {
		      throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
		          + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
		    }
		  }

}
