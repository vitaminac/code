package proc;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SupportedAnnotationTypes("proc.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class BuilderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            List<Element> annotatedElements = new ArrayList<>(roundEnv.getElementsAnnotatedWith(annotation));
            String className = ((TypeElement) annotatedElements.get(0).getEnclosingElement())
                    .getQualifiedName().toString();

            final Map<String, String> setterToTypeMap = new HashMap<>();
            for (var element : annotatedElements) {
                ExecutableType type = (ExecutableType) element.asType();
                if (type.getParameterTypes().size() == 1 &&
                        element.getSimpleName().toString().startsWith("set")) {
                    final String setterMethodName = element.getSimpleName().toString();
                    setterToTypeMap.put(setterMethodName, type.getParameterTypes().get(0).toString());
                } else {
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "@proc.BuilderProperty must be applied to a setXxx method " + "with a single argument", element);
                }
            }

            try {
                writeBuilderSourceCode(className, setterToTypeMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void writeBuilderSourceCode(String className, Map<String, String> setterMap) throws IOException {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Builder";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");

            setterMap.entrySet().forEach(setter -> {
                String methodName = setter.getKey();
                String argumentType = setter.getValue();

                out.println();

                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(methodName);

                out.print("(");

                out.print(argumentType);
                out.println(" value) {");
                out.print("        object.");
                out.print(methodName);
                out.println("(value);");
                out.println("        return this;");
                out.println("    }");
            });

            out.println("}");
        }
    }
}
