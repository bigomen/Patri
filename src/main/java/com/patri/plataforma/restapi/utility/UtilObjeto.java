package com.patri.plataforma.restapi.utility;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class UtilObjeto
{
	 public static void copyData(Object source, Object destination) throws Exception {
	        Method[] methSource = source.getClass().getMethods();
	        Method[] methDest = destination.getClass().getMethods();

	        for (Method ms : methSource) {
	            if (ms.getName().startsWith("get")) {
	                for (Method md : methDest) {
	                    if (md.getName().startsWith("set") &&  ms.getName().substring(3).equals(md.getName().substring(3))) {

	                        Object param = ms.invoke(source);
	                        if (param != null) {
	                            if (param instanceof String && md.getParameters()[0].getType() == String.class) {
	                                md.invoke(destination, ((String)param).trim());
	                            } else if ((param instanceof String) && md.getParameters()[0].getType() == Long.class) {
	                                param = UtilSecurity.decryptId((String)param);
	                                md.invoke(destination, param);
	                            } else if (param instanceof Long && md.getParameters()[0].getType() == String.class) {
	                                param = UtilSecurity.encryptId((Long)param);
	                                md.invoke(destination, param);
	                            } else if (param instanceof Set || param instanceof List) {
	                                // Do nothing
	                            } else {
	                                md.invoke(destination, param);
	                            }
	                        }

	                        break;
	                    }
	                }
	            }
	        }
	    }

}
