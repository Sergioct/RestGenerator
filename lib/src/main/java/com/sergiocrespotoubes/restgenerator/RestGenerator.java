package com.sergiocrespotoubes.restgenerator;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Sergio on 20-May-17.
 */

public class RestGenerator {

    String url;

    public RestGenerator(String url) {
        this.url = url;
    }

    public void parse(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(POST.class)) {
                post(method);
            }else if (method.isAnnotationPresent(GET.class)) {
                get(method);
            }
        }
    }

    public <T> T build(final Class<T> clazz) {

        generateClass(clazz);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (method.isAnnotationPresent(POST.class)) {
                            return post(method);
                        }else if (method.isAnnotationPresent(GET.class)) {
                            return get(method);
                        }
                        return "ERROR";
                    }
                });
    }

    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object,
                                         Object... args) throws Throwable {
        // Because the service interface might not be public, we need to use a MethodHandle lookup
        // that ignores the visibility of the declaringClass.
        Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class, int.class);
        //constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, -1 /* trusted */)
                .unreflectSpecial(method, declaringClass)
                .bindTo(object)
                .invokeWithArguments(args);
    }

    private <T> void generateClass(Class<T> clazz) {
/*
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(POST.class)) {
                post(method);
            }else if (method.isAnnotationPresent(GET.class)) {
                get(method);
            }
        }

        StringBuilder builder = new StringBuilder()
                .append("package com.stablekernel.annotationprocessor.generated;\n\n")
                .append("public class GeneratedClass {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");


        builder.append(" says hello!\\n");

        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class



        try { // write the file
            JavaFileObject source = roundEnv.getFiler().createSourceFile("com.stablekernel.annotationprocessor.generated.GeneratedClass");

            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }


        return true;*/

    }

    private String post(Method method) {
        POST post = method.getAnnotation(POST.class);

        //Class classCalled = test.expected();
        Class declaringClass = method.getDeclaringClass();
        Class theClass = method.getClass();

        String url;

        url = this.url + post.value();


        return "post";
    }

    private String get(Method method) {
        GET get = method.getAnnotation(GET.class);

        //Class classCalled = test.expected();
        Class declaringClass = method.getDeclaringClass();
        Class theClass = method.getClass();

        String url;

        url = this.url + get.value();

        String result = "get";

        return result;
    }

}
