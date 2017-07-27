package game;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import networking.server.ClientConnection;

public class ServerPropertiesIO {
	
	public static void write(ClientConnection connection, ServerProperties properties, String fieldName) {
		try {
			Field field = ServerProperties.class.getDeclaredField(fieldName);
			connection.sendMessage(Communication.SERVER_PROPERTY_CHANGED + getClassType(field.getType()) + "=" 
									+ field.getName() + "=" + getGetter(field).invoke(properties));
			
		} catch(SecurityException | IllegalAccessException | InvocationTargetException | IllegalArgumentException | NoSuchFieldException | IOException  e){
			e.printStackTrace();
		}
	}
	
	public static void writeAll(ClientConnection connection, ServerProperties properties) {
		try {
			Field[] fields = ServerProperties.class.getDeclaredFields();
			for(Field field : fields) {
				connection.sendMessage(Communication.SERVER_PROPERTY_CHANGED + getClassType(field.getType()) + "=" 
									+ field.getName() + "=" + getGetter(field).invoke(properties));
			}
			
		} catch(SecurityException | IllegalAccessException | InvocationTargetException | IllegalArgumentException | IOException  e){
			e.printStackTrace();
		}
	}
	
	public static void read(GameClient connection, String message) {
		try {
			ServerProperties properties = connection.getProperties();
			
			int index = message.indexOf("=");
			String type = message.substring(0, index);
			int nextIndex = message.indexOf("=", index + 1);
			String name = message.substring(index + 1, nextIndex);
			
			getSetter(properties.getClass().getDeclaredField(name)).invoke(properties, Class.forName(type)
					.getConstructor(String.class).newInstance(message.substring(nextIndex + 1)));
			
		} catch(SecurityException | IllegalAccessException | InvocationTargetException | IllegalArgumentException | NoSuchFieldException | InstantiationException | NoSuchMethodException | ClassNotFoundException  e){
			e.printStackTrace();
		}
	}
	
	public static Method getGetter(Field field) {
		Method[] methods = ServerProperties.class.getMethods();
		for(Method method : methods) {
			String name = method.getName();
			if(name.startsWith("get")) {
				name = name.substring(3);
				if(name.equalsIgnoreCase(field.getName()))
					return method;
				continue;
			}
			
			if(name.equalsIgnoreCase(field.getName())) 
				return method;
		}
		
		return null;
	}
	
	public static Method getSetter(Field field) {
		Method[] methods = ServerProperties.class.getMethods();
		for(Method method : methods) {
			String name = method.getName();
			if(name.startsWith("set")) {
				name = name.substring(3);
				if(name.equalsIgnoreCase(field.getName()))
					return method;
				continue;
			}
		}
		
		return null;
	}
	
	public static String getClassType(Class<?> type) {
		if(type.getPackage() == null) {
			switch(type + "") {
				case "float": return Float.class.getCanonicalName() + "";
				case "double": return Double.class.getCanonicalName() + "";
				
				case "byte": return Byte.class.getCanonicalName() + "";
				case "int": return Integer.class.getCanonicalName() + "";
				case "short": return Short.class.getCanonicalName() + "";
				case "long": return Long.class.getCanonicalName() + "";
				
				case "char": return Character.class.getCanonicalName() + "";
				case "boolean": return Boolean.class.getCanonicalName() + "";
			}
		}
		
		return type.getCanonicalName();
	}
}
