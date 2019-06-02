package ru.otus.homework;

import java.util.stream.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {
	
	static TestInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
    	
        private final TestInterface myClass;
        private final Set<String> methodsForLogging = new HashSet<String>();

        DemoInvocationHandler(TestInterface myClass) {
            this.myClass = myClass;
            GetMethodsForInvoking();
        }
        
        private void GetMethodsForInvoking() {
        	Method[] methods = this.myClass.getClass().getDeclaredMethods();
        	for (Method m : methods) {
        		if (m.getAnnotation(Log.class) != null) {
        			methodsForLogging.add(getMethodNameWithParametersTypes(m));
        		}
        	}
        }

        private String getMethodNameWithParametersTypes(Method m) {
        	String result = m.getName();
        	Parameter[] params = m.getParameters();
        	if (params.length != 0) {
        		result += " ";
        		result += Arrays.asList(params).stream()
								.map(x -> x.getType().toString())
								.collect(Collectors.joining(","));
        	}
        	return result;
        }
        
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        	boolean isLogging = methodsForLogging.contains(getMethodNameWithParametersTypes(method));
        	if (isLogging) {
	        	String outputMessage = String.format("Executed method: %s, params: %d",
	        		method.getName(),
	        		((Integer)args[0]).intValue()
	        	);
	            System.out.println(outputMessage);
        	}
            return method.invoke(myClass, args);
        }

    }
	
}
