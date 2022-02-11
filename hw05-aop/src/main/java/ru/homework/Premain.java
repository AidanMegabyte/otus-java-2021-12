package ru.homework;

import org.objectweb.asm.*;
import ru.homework.aop.LogMethodVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Premain {

    private static final int ASM_API_VERSION = Opcodes.ASM9;

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                return modifyLogMethods(classfileBuffer);
            }
        });
    }

    /**
     * Модификация методов класса, которые помечены аннотацией @Log
     *
     * @param classBytes байт-код класса
     * @return байт-код модифицированного класса
     */
    private static byte[] modifyLogMethods(byte[] classBytes) {

        var classReader = new ClassReader(classBytes);
        var classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

        var classVisitor = new ClassVisitor(ASM_API_VERSION, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access,
                                             String name,
                                             String descriptor,
                                             String signature,
                                             String[] exceptions) {
                var defaultMethodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new LogMethodVisitor(ASM_API_VERSION, defaultMethodVisitor, access, name, descriptor);
            }
        };

        classReader.accept(classVisitor, ASM_API_VERSION);

        return classWriter.toByteArray();
    }
}
