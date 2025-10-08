package Laboratorio_4;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

class RelojSimulacion{
private GregorianCalendar tiempo;
    //CONSTRUCTOR
    RelojSimulacion(){
        this.tiempo = new GregorianCalendar(TimeZone.getTimeZone("GMT-3:00"));
    }
    public long TimestampActual(){
        return tiempo.getTimeInMillis() / 1000; //Tranforma tiempo a segundos ya que lo obtiene en milisegundos.
    }
}
    //Comparator según hora de llegada.
    class CriticalPatientComparator implements Comparator<Patient>{
        public int compare(Patient p1, Patient p2){
            return Long.compare(p1.ArrivalTime, p2.ArrivalTime);
        }
    }
    
    //Comparator según nivel de urgencia y luego hora de llegada.
    class NonCriticalPatientsComparator implements Comparator<Patient>{
        public int compare(Patient p1, Patient p2){
            if (p1.UrgencyLevel != p2.UrgencyLevel){
                return Integer.compare(p1.UrgencyLevel, p2.UrgencyLevel);
            }
            return Long.compare(p1.ArrivalTime, p2.ArrivalTime);
        }
}

class Patient{
String Name;
String LastName;
int UrgencyLevel; //1 = critical cases, 2= possible critical case, 3= estable.
String ID; // RUN o Pasaporte del paciente.
long ArrivalTime; // Instante de llegada en 00:00:00 UTC / Chile Desfase de -03:00.
String Status; // Valores : vacio (paciente sin registrar), awaiting (espera), in treatment, discharged (alta).

//CONSTRUCTOR
Patient(String Name, String LastName, int UrgencyLevel, String ID, long ArrivalTime, String Status){
    this.Name = Name;
    this.LastName = LastName;
    this.UrgencyLevel = UrgencyLevel;
    this.ID = ID;
    this.ArrivalTime = ArrivalTime;
    this.Status = Status;
}
//METODOS
public long TimeSinceArrival(long currentTime){
long elapsedSeconds = currentTime - ArrivalTime;
return Math.round(elapsedSeconds / 3600); //Convertir en horas.
}
}

//CLASE Room

class Room{
String Type; //Tipo de sala, UCI (UL = 1) o normal (UL = 2,3.) 
boolean Avalaible; // True = disponible, False = ocupada.
int Number; // De N habitaciones desde 1 a N.

//CONSTRUCTOR
Room(String Type, boolean Avalaible, int Number){
this.Type = Type;
this.Avalaible = true; // disponible por defecto.
this.Number = Number;
}
//METODOS
void SetUsed(){this.Avalaible = false;}

void SetAvalaible(){this.Avalaible = true;}

boolean IsAvailable(){return this.Avalaible;}

}

//CLASE PatientsQueue

class PatientsQueue{
PriorityQueue<Patient> CriticalPatiensQueue; //Prioridad de 1 basada en el t de llegada.
PriorityQueue<Patient> NonCriticalPatiensQueue; //Para 2 y 3, prioridad basada en 1: Urgencia, 2: t de llegada.

//CONSTRUCTOR
PatientsQueue(){

this.CriticalPatiensQueue = new PriorityQueue<Patient>(new CriticalPatientComparator());
this.NonCriticalPatiensQueue = new PriorityQueue<Patient>(new NonCriticalPatientsComparator());

}
//METODOS
Patient GetNextCriticalPatient(){

    Patient nextPatient = CriticalPatiensQueue.peek();
    CriticalPatiensQueue.poll();
    return nextPatient;
}

void InsertCriticalPatient(Patient patient){
CriticalPatiensQueue.add(patient);
}

Patient GetNextNonCriticalPatient(){

    Patient nextPatient = NonCriticalPatiensQueue.peek();
    NonCriticalPatiensQueue.poll();
    return nextPatient;
}

void InsertNonCriticalPatient(Patient patient){
    NonCriticalPatiensQueue.add(patient);
}
}

public class Hospital {
Map<String,Patient> Patients;
Map<String, PriorityQueue<Room>> Rooms; // Un map con una pqueue para las salas.
PatientsQueue PatientsQueue;

//CONSTRUCTOR

Hospital(int K1, int K2){
this.Patients = new HashMap<String, Patient>();
this.Rooms = new HashMap<>();
this.PatientsQueue = new PatientsQueue();

//Inicializacion de habitaciones UCI
PriorityQueue<Room> uciRooms = new PriorityQueue<>(Comparator.comparingInt(r -> r.Number));
for(int i = 1; i <= K1; i++){
    uciRooms.add(new Room("UCI", true, i));
}
Rooms.put("UCI", uciRooms);

//Inicializacion de habitaciones normales
PriorityQueue<Room> normalRooms = new PriorityQueue<>(Comparator.comparingInt(r -> r.Number));
for (int i = 1; i <= K2; i++){
    normalRooms.add(new Room("Normal", true, i));
}
Rooms.put("Normal", normalRooms);
}

//Metodos de Hospital:

long RegisterPatient(String name, String lastname, String id, int urgencylevel){
long currentTime = new RelojSimulacion().TimestampActual();
Patient newPatient = new Patient(name, lastname, urgencylevel, id, currentTime, "awaiting");

if (urgencylevel == 1){
    PatientsQueue.InsertCriticalPatient(newPatient);
} else{
    PatientsQueue.InsertNonCriticalPatient(newPatient);
}
Patients.put(id, newPatient);
return currentTime;
}

long DischargePatient(String id){
    Patient patient = Patients.get(id);
    if (patient == null ||! "in treatment".equals(patient.Status)){
        return 0;
    }
    patient.Status = "discharged";
    return new RelojSimulacion().TimestampActual();
}

Room GetPatientRoom(String id) {

    Patient patient = Patients.get(id);
    if (patient == null || !"in treatment".equals(patient.Status)) {
        return null;
    }

    String roomType = patient.UrgencyLevel == 1 ? "UCI" : "Normal";
    PriorityQueue<Room> suitableRooms = Rooms.get(roomType);
    if (suitableRooms == null || suitableRooms.isEmpty()) {
        return null; 

    }
    for (Room room : suitableRooms) {
        if (!room.IsAvailable()) {
            return room;
        }
    }
    return null; 
}

boolean AssignRoomToPatient(String id) {
    Patient patient = Patients.get(id);
    if (patient == null || !"awaiting".equals(patient.Status)) {
        return false;
    }

    String roomType = patient.UrgencyLevel == 1 ? "UCI" : "Normal";
    PriorityQueue<Room> suitableRooms = Rooms.get(roomType);
    if (suitableRooms == null || suitableRooms.isEmpty()) {
        return false;
    }

    // Buscar la primera habitación disponible y marcarla como ocupada
    for (Room room : suitableRooms) {
        if (room.IsAvailable()) {
            room.SetUsed();  // Marcar habitación como ocupada
            patient.Status = "in treatment"; // Cambiar el estado del paciente
            break; // Salir del ciclo cuando se asigna una habitación
        }
    }
    return true;
}

Room[] GetAvailaibleRooms() {
    int totalRooms = 0;

    // Contar cuantas habitaciones estan disponibles
    for (PriorityQueue<Room> roomQueue : Rooms.values()) {
        for (Room room : roomQueue) {
            if (room.IsAvailable()) {
                totalRooms++;
            }
        }
    }

    // Crear un arreglo de habitaciones disponibles
    Room[] availableRooms = new Room[totalRooms];
    int index = 0;
    for (PriorityQueue<Room> roomQueue : Rooms.values()) {
        for (Room room : roomQueue) {
            if (room.IsAvailable()) {
                availableRooms[index++] = room;
            }
        }
    }
    
    // Imprimir las habitaciones disponibles
    System.out.println("Habitaciones disponibles:");
    for (Room room : availableRooms) {
        System.out.println(room);
    }
    
    return availableRooms;
}


Patient[] GetTopKAwaitingPatients(int urgencyLevel, int k) {
    PriorityQueue<Patient> queue;
    if (urgencyLevel == 1) {
        queue = PatientsQueue.CriticalPatiensQueue; 
    } else {
        queue = PatientsQueue.NonCriticalPatiensQueue; 
    }

    int size = Math.min(k, queue.size());
    Patient[] topKPatients = new Patient[size];
    int index = 0;

    for (Patient patient : queue) {
        if (index >= size) {
            break;
        }
        topKPatients[index++] = patient;
    }
    return topKPatients;
}
}

class GenerateData {
    // Generar un timestamp aleatorio dentro del rango de dos semanas
    private static long generateRandomTimestamp(long start, long end) {
        Random rand = new Random();
        return start + (long) (rand.nextDouble() * (end - start));
    }

    // Generar lista de pacientes aleatorios
    public static ArrayList<Patient> generatePatients(int N, long startTime, long endTime) {
        ArrayList<Patient> patients = new ArrayList<>();
        Random rand = new Random();
        
        for (int i = 0; i < N; i++) {
            // Generar atributos aleatorios para cada paciente
            String name = "Paciente" + (i + 1);
            String lastName = "Apellido" + (i + 1);
            int urgencyLevel = rand.nextInt(3) + 1; // UrgencyLevel entre 1 y 3
            String id = String.valueOf(rand.nextInt(100000)); // ID aleatorio
            long arrivalTime = generateRandomTimestamp(startTime, endTime); // Timestamp de llegada
            String status = ""; // Estado vacío al momento de la creación

            // Crear paciente
            Patient patient = new Patient(name, lastName, urgencyLevel, id, arrivalTime, status);
            patients.add(patient);
        }
        return patients;
    }

    // Generar lista de tiempos de alta aleatorios
    public static ArrayList<Long> generateDischargeTimes(ArrayList<Patient> patients, long endTime) {
        ArrayList<Long> dischargeTimes = new ArrayList<>();
        for (Patient patient : patients) {
            long dischargeTime;
            // Generar un tiempo de alta que sea mayor al ArrivalTime del paciente y menor al tiempo de simulación
            do {
                dischargeTime = generateRandomTimestamp(patient.ArrivalTime + 1, endTime); // El tiempo de alta debe ser mayor al ArrivalTime
            } while (dischargeTime <= patient.ArrivalTime); // Asegurarse que el DischargeTime es mayor que ArrivalTime
            dischargeTimes.add(dischargeTime);
        }
        return dischargeTimes;
    }

    // Guardar los pacientes en un archivo N_Patients.txt
    public static void savePatientsToFile(ArrayList<Patient> patients, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Patient patient : patients) {
                writer.write(patient.Name + "," + patient.LastName + "," + patient.UrgencyLevel + "," +
                        patient.ID + "," + patient.ArrivalTime + "," + patient.Status);
                writer.newLine();
            }
        }
    }

    // Guardar los tiempos de alta en un archivo N_DischargeTimes.txt
public static void saveDischargeTimesToFile(ArrayList<Long> dischargeTimes, String fileName, Map<String, Patient> patients) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        // Crear un set para asegurarse de no asignar el mismo dischargeTime a diferentes pacientes
        Set<String> processedPatients = new HashSet<>();
        
        for (Long dischargeTime : dischargeTimes) {
            // Buscar el paciente cuyo dischargeTime corresponde
            for (Patient patient : patients.values()) {
                if (dischargeTime > patient.ArrivalTime && dischargeTime <= new RelojSimulacion().TimestampActual()) {
                    // Verifica si ya se ha procesado el paciente
                    if (!processedPatients.contains(patient.ID)) {
                        processedPatients.add(patient.ID);  // Marca como procesado
                        // Actualiza el estado del paciente si es necesario
                        patient.Status = "Alta";
                        writer.write(patient.Name + "," + patient.LastName + "," + patient.ID + "," + patient.Status + "," + dischargeTime);
                        writer.newLine();
                        break; // salir del ciclo después de escribir la información
                    }
                }
            }
        }
    }
}

    public static void main(String[] args) {
        // Definir intervalo de dos semanas para ArrivalTime y DischargeTime
        long startTime = 1730457600L; // Timestamp para Viernes 01 Aug 2024 00:00:00 GMT-03:00
        long endTime = 1731676800L;   // Timestamp para Viernes 15 Aug 2024 00:00:00 GMT-03:00

        int N = 500; // Número de pacientes a generar

        int K1 = 10; // Número de habitaciones UCI
        int K2 = 20; // Número de habitaciones normales
        Hospital hospital = new Hospital(K1, K2); // O como crees que se inicializa tu hospital
        
        // Generar pacientes aleatorios
        ArrayList<Patient> patients = generatePatients(N, startTime, endTime);

        // Agregar los pacientes generados al mapa del hospital
        for (Patient patient : patients) {
            hospital.Patients.put(patient.ID, patient);
        }

        // Generar tiempos de alta aleatorios
        ArrayList<Long> dischargeTimes = generateDischargeTimes(patients, endTime);

        try {
            // Guardar los pacientes y los tiempos de alta en archivos
            savePatientsToFile(patients, "N_Patients.txt");
            saveDischargeTimesToFile(dischargeTimes, "N_DischargeTimes.txt", hospital.Patients);

            System.out.println("Datos generados y guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los archivos: " + e.getMessage());
        }
    }
}

//TimeSinceArrival acceso al tiempo actual.
//Crear clase propia para para almacenar la hora, puesto la clase instant es inmutable.
//Clase: RelojSimulacion; atributo: public long timestamps.
//Time since arrival realizarlo con un Gregorian Calendar.