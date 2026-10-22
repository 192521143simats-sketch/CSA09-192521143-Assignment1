import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class LookupBenchmark {
    static volatile int sink;
    static ParkingTicket linear(ParkingTicket[] a, String key) {
        for(ParkingTicket t:a) if(t.getVehicle().getRegNo().equals(key)) return t;
        return null;
    }
    static ParkingTicket linearList(java.util.ArrayList<ParkingTicket> a,String key) {
        for(ParkingTicket t:a) if(t.getVehicle().getRegNo().equals(key)) return t;
        return null;
    }
    public static void main(String[] args) {
        System.out.println("n,array_ns_per_lookup,list_ns_per_lookup,map_ns_per_lookup");
        for(int n:new int[]{20,150,500}) {
            ParkingTicket[] a=new ParkingTicket[n];
            java.util.ArrayList<ParkingTicket> list=new java.util.ArrayList<>();
            java.util.HashMap<String,ParkingTicket> map=new java.util.HashMap<>();
            for(int i=0;i<n;i++) {
                ParkingTicket t=new ParkingTicket(new Car("REG"+i),new Slot(0,i,false),LocalDateTime.of(2026,10,22,10,0));
                a[i]=t;list.add(t);map.put(t.getVehicle().getRegNo(),t);
            }
            String key="REG"+(n-1); int repeats=20000;
            for(int i=0;i<10000;i++) {sink=linear(a,key).hashCode();sink=linearList(list,key).hashCode();sink=map.get(key).hashCode();}
            long start=System.nanoTime();
            for(int i=0;i<repeats;i++) sink=linear(a,key).hashCode();
            long arr=System.nanoTime()-start;start=System.nanoTime();
            for(int i=0;i<repeats;i++) sink=linearList(list,key).hashCode();
            long lst=System.nanoTime()-start;start=System.nanoTime();
            for(int i=0;i<repeats;i++) sink=map.get(key).hashCode();
            long hm=System.nanoTime()-start;
            System.out.println(n+","+arr/repeats+","+lst/repeats+","+hm/repeats);
        }
        System.out.println("Last-key lookup; warm-up=10000, repetitions=20000; illustrative JVM timings");
    }
}
