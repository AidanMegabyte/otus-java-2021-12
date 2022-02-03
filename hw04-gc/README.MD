Timings for different heap sizes (measured by JMH):

| Heap size, MB |     Time, ms (before)      | Time, ms (after) |
|:-------------:|:--------------------------:|:----------------:|
|      256      | java.lang.OutOfMemoryError |       6387       |
|      512      |           31541            |       4930       |
|      768      |           29923            |       4110       |
|     1024      |           27494            |       3982       |
|     1280      |           27702            |       4539       |
|     1536      |           27863            |       4022       |
|     1792      |           27534            |       3980       |
|     2048      |           27722            |       3954       |