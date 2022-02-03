Timings for different heap sizes (measured by JMH):

| Heap size, MB |     Time, ms (before)      |     Time, ms (after)     |
|:-------------:|:--------------------------:|:------------------------:|
|      256      | java.lang.OutOfMemoryError |          28856           |
|      512      |           31541            |          26669           |
|      768      |           29923            |          25481           |
|     1024      |           27494            |          24622           |
|     1280      |           27702            |          24427           |
|     1536      |           27863            |          23427           |
|     1792      |           27534            |          23125           |
|     2048      |           27722            |          24018           |