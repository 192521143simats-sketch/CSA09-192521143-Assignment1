import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task06Demo { public static void main(String[] args) {
new TraceElectricCar();
ElectricCar ev = new ElectricCar("TN01E1");
System.out.println("ElectricCar is Car: " + (ev instanceof Car));
System.out.println("ElectricCar is Vehicle: " + (ev instanceof Vehicle));
System.out.println("Inherited registration: " + ev.getRegNo());
}}

class TraceVehicle { TraceVehicle() { System.out.println("1 Vehicle constructor"); } }
class TraceCar extends TraceVehicle { TraceCar() { super(); System.out.println("2 Car constructor"); } }
class TraceElectricCar extends TraceCar { TraceElectricCar() { super(); System.out.println("3 ElectricCar constructor"); } }
