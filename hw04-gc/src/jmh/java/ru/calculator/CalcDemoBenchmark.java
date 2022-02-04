package ru.calculator;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Fork(
        value = 1,
        jvmArgs = {
                "-XX:+HeapDumpOnOutOfMemoryError",
                "-XX:HeapDumpPath=./build/heapdump.hprof",
                "-XX:+UseG1GC",
                "-Xlog:gc=debug:file=./build/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m"
        }
)
@Warmup(iterations = 5)
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CalcDemoBenchmark {

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms256m", "-Xmx256m"})
    public void calc_01_256() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms512m", "-Xmx512m"})
    public void calc_02_512() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms768m", "-Xmx768m"})
    public void calc_03_768() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms1024m", "-Xmx1024m"})
    public void calc_04_1024() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms1280m", "-Xmx1280m"})
    public void calc_05_1280() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms1536m", "-Xmx1536m"})
    public void calc_06_1536() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms1792m", "-Xmx1792m"})
    public void calc_07_1792() {
        CalcDemo.main();
    }

    @Benchmark
    @Fork(jvmArgsPrepend = {"-Xms2048m", "-Xmx2048m"})
    public void calc_08_2048() {
        CalcDemo.main();
    }
}
