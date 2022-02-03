Timings for different heap sizes (measured by JMH):

| Heap size, MB |     Time, ms (before)      | Time, ms (after) |
|:-------------:|:--------------------------:|:----------------:|
|      256      | java.lang.OutOfMemoryError |       1916       |
|      512      |           31541            |       1913       |
|      768      |           29923            |       1913       |
|     1024      |           27494            |       1914       |
|     1280      |           27702            |       1989       |
|     1536      |           27863            |       1916       |
|     1792      |           27534            |       1919       |
|     2048      |           27722            |       1917       |