import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task11Benchmark {
    static volatile int sink;

    static ParkingTicket linear(ParkingTicket[] a, String key) {
        for (ParkingTicket t : a) if (t.getVehicle().getRegNo().equals(key)) return t;
        return null;
    }

    static ParkingTicket linearList(java.util.ArrayList<ParkingTicket> a, String key) {
        for (ParkingTicket t : a) if (t.getVehicle().getRegNo().equals(key)) return t;
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("n,array_ns,list_ns,map_ns,array_bytes,list_bytes,map_bytes");
        for (int n : new int[] {20, 150, 500}) {
            ParkingTicket[] a = new ParkingTicket[n];
            java.util.ArrayList<ParkingTicket> list = new java.util.ArrayList<>();
            java.util.HashMap<String, ParkingTicket> map = new java.util.HashMap<>();
            for (int i = 0; i < n; i++) {
                ParkingTicket t =
                        new ParkingTicket(
                                new Car("REG" + i),
                                new Slot(0, i, false),
                                LocalDateTime.of(2026, 10, 22, 10, 0));
                a[i] = t;
                list.add(t);
                map.put(t.getVehicle().getRegNo(), t);
            }
            String key = "REG" + (n - 1);
            int repeats = 20000;
            for (int i = 0; i < 10000; i++) {
                sink = linear(a, key).hashCode();
                sink = linearList(list, key).hashCode();
                sink = map.get(key).hashCode();
            }
            long start = System.nanoTime();
            for (int i = 0; i < repeats; i++) sink = linear(a, key).hashCode();
            long arr = System.nanoTime() - start;
            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) sink = linearList(list, key).hashCode();
            long lst = System.nanoTime() - start;
            start = System.nanoTime();
            for (int i = 0; i < repeats; i++) sink = map.get(key).hashCode();
            long hm = System.nanoTime() - start;
            long arrayBytes = Task11MemoryAgent.containerBytes(a);
            long listBytes = Task11MemoryAgent.containerBytes(list);
            long mapBytes = Task11MemoryAgent.containerBytes(map);
            if (arrayBytes <= 0 || listBytes <= 0 || mapBytes <= 0)
                throw new AssertionError("Invalid memory measurement");
            System.out.println(
                    n
                            + ","
                            + arr / repeats
                            + ","
                            + lst / repeats
                            + ","
                            + hm / repeats
                            + ","
                            + arrayBytes
                            + ","
                            + listBytes
                            + ","
                            + mapBytes);
        }
        System.out.println("Lookup: last key; warm-up=10000; repetitions=20000");
        System.out.println(
                "Bytes: container, backing arrays and map nodes; shared ticket/key objects excluded");
        System.out.println(
                "JVM: "
                        + System.getProperty("java.vm.name")
                        + " "
                        + System.getProperty("java.version"));
    }
}
