import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.IdentityHashMap;
public final class Task11MemoryAgent {
    private static Instrumentation instrumentation;
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.home"));
    }
    public static void premain(String arguments, Instrumentation instance) {
        instrumentation = instance;
    }
    public static long containerBytes(Object root) throws IllegalAccessException {
        if (instrumentation == null) throw new IllegalStateException("Run with the memory agent");
        IdentityHashMap<Object, Boolean> seen = new IdentityHashMap<>();
        ArrayDeque<Object> pending = new ArrayDeque<>();
        pending.add(root);
        long bytes = 0;
        while (!pending.isEmpty()) {
            Object value = pending.removeFirst();
            Class<?> type = value.getClass();
            if (!(value instanceof Object[]) && !type.getName().startsWith("java.util.")) continue;
            if (seen.put(value, Boolean.TRUE) != null) continue;
            bytes += instrumentation.getObjectSize(value);
            if (value instanceof Object[] array) {
                for (Object item : array) if (item != null) pending.add(item);
            } else {
                for (Class<?> c = type; c != Object.class; c = c.getSuperclass()) {
                    for (Field field : c.getDeclaredFields()) {
                        if (Modifier.isStatic(field.getModifiers()) || field.getType().isPrimitive()) continue;
                        if (!field.trySetAccessible()) throw new IllegalStateException("Open java.base/java.util for measurement");
                        Object item = field.get(value);
                        if (item != null) pending.add(item);
                    }
                }
            }
        }
        return bytes;
    }
}
