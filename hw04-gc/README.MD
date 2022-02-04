Timings for different heap sizes (measured by JMH):

| Heap size, MB |     Time, ms (before)      | Time, ms (after) |
|:-------------:|:--------------------------:|:----------------:|
|      256      | java.lang.OutOfMemoryError |       1907       |
|      512      |           31541            |       1933       |
|      768      |           29923            |       1937       |
|     1024      |           27494            |       1958       |
|     1280      |           27702            |       1903       |
|     1536      |           27863            |       2074       |
|     1792      |           27534            |       1914       |
|     2048      |           27722            |       1953       |