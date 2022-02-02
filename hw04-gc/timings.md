Timings for different heap sizes (measured by JMH):

| Heap size, MB |                  Time, ms                   |
|:-------------:|:-------------------------------------------:|
|      256      | java.lang.OutOfMemoryError: Java heap space |
|      512      |                    31541                    |
|      768      |                    29923                    |
|     1024      |                    27494                    |
|     1280      |                    27702                    |
|     1536      |                    27863                    |
|     1792      |                    27534                    |
|     2048      |                    27722                    |