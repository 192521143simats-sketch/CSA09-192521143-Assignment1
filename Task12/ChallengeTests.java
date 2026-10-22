import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class ChallengeTests {
    static int passed;
    static void check(String name,Object expected,Object actual) {
        if(!expected.equals(actual)) throw new AssertionError(name+" expected="+expected+" actual="+actual);
        passed++;System.out.println("PASS | "+name+" | expected="+expected+" | actual="+actual);
    }
    static void reject(String name,Runnable action) {
        try {action.run();} catch(IllegalArgumentException | IllegalStateException e) {check(name,"rejected","rejected");return;}
        throw new AssertionError(name);
    }
    public static void main(String[] args) {
        LocalDate d=LocalDate.of(2026,10,22);
        ChallengePricing rule=new ChallengePricing(); DeliveryVan van=new DeliveryVan("VAN001");
        check("van mixed morning",90L,rule.parkingFee(van,d.atTime(8,0),d.atTime(10,0)));
        check("van mixed evening",45L,rule.parkingFee(van,d.atTime(16,30),d.atTime(17,30)));
        check("van peak day",480L,rule.parkingFee(van,d.atTime(9,0),d.atTime(17,0)));
        check("van off-peak night",480L,rule.parkingFee(van,d.atTime(17,0),d.plusDays(1).atTime(9,0)));
        check("van exact 09 boundary",60L,rule.parkingFee(van,d.atTime(9,0),d.atTime(10,0)));
        check("van exact 17 boundary",30L,rule.parkingFee(van,d.atTime(17,0),d.atTime(18,0)));
        check("van zero duration",0L,rule.parkingFee(van,d.atTime(9,0),d.atTime(9,0)));
        check("van fractional minute",1L,rule.parkingFee(van,d.atTime(9,0),d.atTime(9,0).plusSeconds(1)));
        check("van subsecond",1L,rule.parkingFee(van,d.atTime(9,0),d.atTime(9,0).plusNanos(1)));
        check("van whole 24 hours",960L,rule.parkingFee(van,d.atStartOfDay(),d.plusDays(1).atStartOfDay()));
        check("van two days",1920L,rule.parkingFee(van,d.atStartOfDay(),d.plusDays(2).atStartOfDay()));
        reject("van negative interval",()->rule.parkingFee(van,d.atTime(10,0),d.atTime(9,0)));
        EBike bike=new EBike("EB001");
        check("e-bike zero",0L,bike.calculateFee(0));
        check("e-bike positive minute",20L,bike.calculateFee(1));
        check("e-bike long flat stay",20L,bike.calculateFee(3000));
        check("e-bike date overload",20L,rule.parkingFee(bike,d.atTime(8,0),d.atTime(10,0)));
        reject("e-bike negative",()->bike.calculateFee(-1));
        OneDayPass pass=new OneDayPass("DAY001",d);
        check("day pass valid",true,pass.covers("DAY001","VISITOR",d.atTime(0,0),d.atTime(23,59)));
        check("day pass midnight expiry",false,pass.covers("DAY001","VISITOR",d.atTime(0,0),d.plusDays(1).atStartOfDay()));
        check("day pass wrong role",false,pass.covers("DAY001","STAFF",d.atTime(8,0),d.atTime(9,0)));
        check("day pass wrong plate",false,pass.covers("OTHER","VISITOR",d.atTime(8,0),d.atTime(9,0)));
        ParkingFacility p=new ParkingFacility(new int[]{2,3,4,2,5},rule);
        check("five unequal floors total",16,p.capacity());
        check("floor 4 last valid slot",true,p.isValidSlot(4,4));
        check("floor 0 invalid slot",false,p.isValidSlot(0,2));
        for(int i=0;i<11;i++)p.enter(new Car("REG"+i),d.atTime(8,0),"VISITOR",Pass.none(),false);
        reject("all general slots full",()->p.enter(new Car("FULL1"),d.atTime(8,0),"VISITOR",Pass.none(),false));
        for(int i=0;i<5;i++)p.enter(new ElectricCar("EV0"+i),d.atTime(8,0),"VISITOR",Pass.none(),true);
        check("all 16 slots occupied",16,p.occupancy());
        reject("all charging slots full",()->p.enter(new ElectricCar("EV999"),d.atTime(8,0),"VISITOR",Pass.none(),true));
        ParkingFacility q=new ParkingFacility(new int[]{2},rule);
        q.enter(new Car("DAY001"),d.atTime(8,0),"VISITOR",pass,false);
        check("day pass exit free",0L,q.exit("DAY001",d.atTime(10,0),0));
        q.enter(new Car("DAY001"),d.atTime(8,0),"VISITOR",pass,false);
        check("day pass expired pays",480L,q.exit("DAY001",d.plusDays(1).atStartOfDay(),0));
        q.enter(new ElectricCar("DAY001"),d.atTime(8,0),"VISITOR",pass,true);
        check("day pass electricity retained",120L,q.exit("DAY001",d.atTime(10,0),10));
        q.enter(van,d.atTime(8,0),"VISITOR",Pass.none(),false);
        check("facility uses van policy",90L,q.exit("VAN001",d.atTime(10,0),0));
        System.out.println("TOTAL PASSED: "+passed);
    }
}
