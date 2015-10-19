# ReflectDroid
To reflect the classes which are under the specific package:
```java
ReflectDroid reflectDroid = new ReflectDroid("com.example");
Set<Class<? extends ExampleClass>> classes =
            reflectDroid.getSubTypesOf(ExampleClass.class);
```

Retrieving a set of classes with the pecific annotation:
```java
Set<Class<?>> classes = reflectDroid.getTypesAnnotatedWith(ExampleAnnotation.class);
```
