package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exception.AppComponentException;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();

    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        initContainer(Arrays.asList(initialConfigClasses));
    }

    public AppComponentsContainerImpl(String initialConfigPackage) {
        var reflections = new Reflections(
                initialConfigPackage,
                Scanners.SubTypes.filterResultsBy(c -> true)
        );
        initContainer(reflections.getSubTypesOf(Object.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        var foundAppComponents = appComponents.stream()
                .filter(appComponent -> componentClass.isAssignableFrom(appComponent.getClass()))
                .toList();

        if (foundAppComponents.size() == 0) {
            throw new AppComponentException(String.format(
                    "Component of (sub)class \"%s\" not found!",
                    componentClass.getName()
            ));
        }

        if (foundAppComponents.size() > 1) {
            throw new AppComponentException(String.format(
                    "More than one component of (sub)class \"%s\" found!",
                    componentClass.getName()
            ));
        }

        return (C) foundAppComponents.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) Optional.ofNullable(appComponentsByName.get(componentName))
                .orElseThrow(() -> new AppComponentException(String.format(
                        "Component with name \"%s\" not found!",
                        componentName
                )));
    }

    private void initContainer(Collection<Class<?>> initialConfigClasses) {
        initialConfigClasses.forEach(this::checkConfigClass);
        initialConfigClasses.stream()
                .sorted(Comparator.comparingInt(config -> config.getAnnotation(AppComponentsContainerConfig.class).order()))
                .forEach(config -> createAppComponents(config, findAppComponentCreationMethods(config)));
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format(
                    "Given class is not config: %s",
                    configClass.getName()
            ));
        }
    }

    private List<Method> findAppComponentCreationMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();
    }

    private void createAppComponents(Class<?> configClass, List<Method> appComponentCreationMethods) {
        try {

            var instance = configClass.getConstructor().newInstance();

            for (Method method : appComponentCreationMethods) {

                var appComponentName = method.getAnnotation(AppComponent.class).name();
                if (appComponentsByName.containsKey(appComponentName)) {
                    throw new AppComponentException(String.format(
                            "Component with name \"%s\" already exists!",
                            appComponentName
                    ));
                }

                var args = Arrays.stream(method.getParameterTypes())
                        .map(this::getAppComponent)
                        .toArray();
                var appComponent = method.invoke(instance, args);

                appComponents.add(appComponent);
                appComponentsByName.put(appComponentName, appComponent);
            }
        } catch (Throwable e) {
            throw new AppComponentException("Component creation error!", e);
        }
    }
}
