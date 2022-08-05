import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private AtomicInteger x = new AtomicInteger(0);

    public void increase() {
        x.incrementAndGet();
    }

    public void decrease() {
        x.decrementAndGet();
    }

    public int getValorContadorAtomic() {
        return x.get();
    }
}
