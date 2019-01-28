import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//fuente https://github.com/SriramKeerthi/SimpleThreadpool/blob/master/threadpool/src/main/java/com/caffinc/threadpool/SimpleThreadpool.java
public class SimpleThreadpool {
    private static AtomicInteger poolCount = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Runnable> runnables;
    private AtomicBoolean execute;
    private List<SimpleThreadpoolThread> threads;




    public SimpleThreadpool(int threadCount) {
        poolCount.incrementAndGet();
        this.runnables = new ConcurrentLinkedQueue<>();
        this.execute = new AtomicBoolean(true);
        this.threads = new ArrayList<>();
        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            SimpleThreadpoolThread thread = new SimpleThreadpoolThread("SimpleThreadpool" + poolCount.get() + "Thread" + threadIndex, this.execute, this.runnables);
            thread.start();
            this.threads.add(thread);
        }
    }


    public void execute(Runnable runnable) {
        if (this.execute.get()) {
            runnables.add(runnable);
        } else {
            throw new IllegalStateException("Threadpool terminating");
        }
    }

}