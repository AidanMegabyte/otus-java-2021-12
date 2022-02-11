package ru.homework.aop;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import ru.homework.annotations.Log;

import java.util.Objects;

/**
 * "Посетитель" метода класса, который проверяет, помечен ли текущий метод аннотацией @Log.
 * Если помечен, то в конец метода добавляется код вывода названия метода и значений его параметров.
 * Если нет, то код метода остается без изменений.
 */
public class LogMethodVisitor extends AdviceAdapter {

    private final String methodName;

    private boolean annotated = false;

    public LogMethodVisitor(int api,
                            MethodVisitor methodVisitor,
                            int access,
                            String name,
                            String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.methodName = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        // Проверяем, помечен ли текущий метод аннотацией @Log
        annotated = annotated || Objects.equals(Log.class.descriptorString(), descriptor);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitInsn(int opcode) {
        // Если происходит возврат из метода, и он помечен аннотацией @Log, то выводим информацию о нем
        if (opcode >= Opcodes.LRETURN && opcode <= Opcodes.RETURN && annotated) {
            printMethodInfo();
        }
        super.visitInsn(opcode);
    }

    /**
     * Вывод информации о методе и его аргументах в консоль
     */
    private void printMethodInfo() {

        var argTypes = getArgumentTypes();

        printMessage(String.format("executed method: \"%s\", param(s): ", methodName));
        if (argTypes.length == 0) {
            printMessage("no\n");
        }

        // Если метод не статический, то первый его аргумент - это всегда this, его пропускаем
        var argNum = getAccess() == Opcodes.ACC_STATIC ? 0 : 1;
        for (var i = 0; i < argTypes.length; i++) {
            var isObject = argTypes[i].getSort() == Type.OBJECT;
            var argOpcode = isObject ? Opcodes.ALOAD : argTypes[i].getOpcode(Opcodes.ILOAD);
            var argDescriptor = isObject ? Object.class.descriptorString() : argTypes[i].getDescriptor();
            printArg(argNum, argOpcode, argDescriptor);
            printMessage(i == argTypes.length - 1 ? "\n" : ", ");
            // Типы long и double занимают вдвое больше места относительно других типов,
            // поэтому это надо учесть при переходе к следующему аргументу
            argNum += argTypes[i].getSize();
        }
    }

    /**
     * Вывод сообщения в консоль
     *
     * @param message сообщение
     */
    private void printMessage(String message) {
        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visitLdcInsn(message);
        visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "print",
                "(Ljava/lang/String;)V",
                false
        );
    }

    /**
     * Вывод аргумента метода в консоль
     *
     * @param argNum        номер аргумента
     * @param argOpcode     код операции
     * @param argDescriptor описание типа
     */
    private void printArg(int argNum, int argOpcode, String argDescriptor) {
        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visitVarInsn(argOpcode, argNum);
        visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "print",
                String.format("(%s)V", argDescriptor),
                false
        );
    }
}
