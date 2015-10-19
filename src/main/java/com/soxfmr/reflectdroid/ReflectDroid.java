package com.soxfmr.reflectdroid;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

public class ReflectDroid {

    private Context context;
    private String packageName;

    private List<Class<?>> classesList;

    public ReflectDroid(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;

        init();
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private void init() {
        classesList = new ArrayList<>();
        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            String className;
            for (Enumeration<String> enumeration = dexFile.entries(); enumeration.hasMoreElements(); ) {
                className = enumeration.nextElement();
                if (! className.contains( packageName )) continue;

                classesList.add(Class.forName(className));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> cls) {
        Set<Class<? extends T>> hashSet = new HashSet<>();
        for (Class<?> c : classesList) {
            if (c.getSuperclass() == cls) {
                hashSet.add((Class<? extends T>) c);
            }
        }
        return hashSet;
    }

    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        Set<Class<?>> hashSet = new HashSet<>();
        for (Class<?> c : classesList) {
            if (c.getAnnotation(annotation) != null) {
                hashSet.add(c);
            }
        }
        return hashSet;
    }

}
