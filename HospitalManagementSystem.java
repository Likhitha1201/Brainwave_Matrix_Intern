import java.io.*;
import java.util.*;

public class HospitalManagementSystem {

	private static final Scanner scanner = new Scanner(System.in);

	private static final List<Patient> patients = new ArrayList<>();
	private static final List<Appointment> appointments = new ArrayList<>();
	private static final List<Staff> staffMembers = new ArrayList<>();
	private static final Map<String, Integer> inventory = new HashMap<>();
	private static final Map<String, List<String>> patientHistory = new HashMap<>();

	private static final String DATA_FILE = "hospital_data.txt";

	public static void main(String[] args) {
		loadData();
		if (!authenticate()) {
			System.out.println("Access denied. Exiting system.");
			return;
		}

		int choice = 0;
		do {
			System.out.println("\n🏥 Welcome to Advanced Hospital Management System 🏥\n");
			System.out.println("1. Register Patient");
			System.out.println("2. Schedule Appointment");
			System.out.println("3. View Electronic Health Records (EHR)");
			System.out.println("4. Billing and Invoicing");
			System.out.println("5. Manage Inventory");
			System.out.println("6. Staff Management");
			System.out.println("7. Patient History Management");
			System.out.println("8. Generate Reports");
			System.out.println("9. Exit");
			System.out.print("Enter your choice: ");
			try {
				choice = scanner.nextInt();
				scanner.nextLine(); 
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number between 1 and 9.");
				scanner.nextLine(); 
				continue;
			}

			switch (choice) {
			case 1 : registerPatient();
			break;
			case 2 : scheduleAppointment();
			break;
			case 3 : viewEHR();
			break;
			case 4 : generateInvoice();
			break;
			case 5 : manageInventory();
			break;
			case 6 : manageStaff();
			break;
			case 7 : managePatientHistory();
			break;
			case 8 : generateReports();
			break;
			case 9 : saveData();
			break;
			default : System.out.println("Invalid choice. Please try again.");
			}
		} 
		while (choice != 9);

		System.out.println("Exiting system. Goodbye!");
	}

	private static boolean authenticate() {
		System.out.println("Login Required:");
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		return "admin".equals(username) && "password123".equals(password);
	}

	private static void registerPatient() {
		System.out.print("Enter patient name: ");
		String name = scanner.nextLine();
		System.out.print("Enter age: ");
		int age = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter contact number: ");
		String contact = scanner.nextLine();

		patients.add(new Patient(name, age, contact));
		System.out.println("Patient registered successfully.");
	}

	private static void scheduleAppointment() {
		System.out.print("Enter patient name: ");
		String patientName = scanner.nextLine();
		System.out.print("Enter appointment date (DD/MM/YYYY): ");
		String date = scanner.nextLine();
		System.out.print("Enter time slot (e.g., 10:00 AM): ");
		String time = scanner.nextLine();

		appointments.add(new Appointment(patientName, date, time));
		System.out.println("Appointment scheduled successfully.");
	}

	private static void viewEHR() {
		System.out.println("\nElectronic Health Records (EHR):");
		if (patients.isEmpty()) {
			System.out.println("No patient records found.");
			return;
		}

		System.out.printf("%-20s %-10s %-15s%n", "Name", "Age", "Contact Number");
		System.out.println("--------------------------------------------------");
		for (Patient patient : patients) {
			System.out.printf("%-20s %-10d %-15s%n", patient.name, patient.age, patient.contact);
		}

		System.out.print("\nSearch for a patient by name (or press Enter to skip): ");
		String searchName = scanner.nextLine();
		if (!searchName.isEmpty()) {
			patients.stream()
			.filter(patient -> patient.name.equalsIgnoreCase(searchName))
			.forEach(System.out::println);
		}
	}

	private static void generateInvoice() {
		System.out.print("Enter patient name for billing: ");
		String patientName = scanner.nextLine();
		System.out.print("Enter bill amount: ");
		double amount = scanner.nextDouble();
		scanner.nextLine(); 

		System.out.printf("Invoice generated for %s: $%.2f\n", patientName, amount);
	}

	private static void manageInventory() {
		System.out.println("\nInventory Management:");
		System.out.println("1. View Inventory\n2. Add Inventory\n3. Update Inventory\n4. Remove Inventory");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); 

		switch (choice) {
		case 1 : viewInventory();
		break;
		case 2 : addInventory();
		break;
		case 3 : updateInventory();
		break;
		case 4 : removeInventory();
		break;
		default : System.out.println("Invalid choice.");
		}
	}

	private static void viewInventory() {
		System.out.println("\nCurrent Inventory:");
		for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
			System.out.printf("%s: %d units\n", entry.getKey(), entry.getValue());
		}
	}

	private static void addInventory() {
		System.out.print("Enter item name: ");
		String itemName = scanner.nextLine();
		System.out.print("Enter quantity: ");
		int quantity = scanner.nextInt();
		scanner.nextLine(); 

		inventory.put(itemName, inventory.getOrDefault(itemName, 0) + quantity);
		System.out.println("Item added to inventory.");
	}

	private static void updateInventory() {
		System.out.print("Enter item name: ");
		String itemName = scanner.nextLine();
		System.out.print("Enter new quantity: ");
		int quantity = scanner.nextInt();
		scanner.nextLine(); 

		if (inventory.containsKey(itemName)) {
			inventory.put(itemName, quantity);
			System.out.println("Inventory updated.");
		} else {
			System.out.println("Item not found in inventory.");
		}
	}

	private static void removeInventory() {
		System.out.print("Enter item name to remove: ");
		String itemName = scanner.nextLine();
		if (inventory.remove(itemName) != null) {
			System.out.println("Item removed from inventory.");
		} else {
			System.out.println("Item not found in inventory.");
		}
	}

	private static void manageStaff() {
		System.out.println("\nStaff Management:");
		System.out.println("1. View Staff\n2. Add Staff Member\n3. Remove Staff Member");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); 

		switch (choice) {
		case 1 : viewStaff();
        break;
		case 2 : addStaffMember();
        break;
		case 3 : removeStaffMember();
        break;
		default : System.out.println("Invalid choice.");
		}
	}

	private static void viewStaff() {
		System.out.println("\nCurrent Staff Members:");

		if (staffMembers.isEmpty()) {
			System.out.println("No staff members found.");
			return;
		}

		System.out.printf("%-20s %-20s%n", "Name", "Position");
		System.out.println("------------------------------------------------");

		for (Staff staff : staffMembers) {
			System.out.printf("%-20s %-20s%n", staff.name, staff.position);
		}
	}
	private static void addStaffMember() {
		System.out.print("Enter staff name: ");
		String name = scanner.nextLine();
		System.out.print("Enter position: ");
		String position = scanner.nextLine();

		staffMembers.add(new Staff(name, position));
		System.out.println("Staff member added successfully.");
	}

	private static void removeStaffMember() {
		System.out.print("Enter staff name to remove: ");
		String name = scanner.nextLine();
		staffMembers.removeIf(staff -> staff.name.equalsIgnoreCase(name));
		System.out.println("Staff member removed successfully.");
	}

	private static void managePatientHistory() {
		System.out.println("\nPatient History Management:");
		System.out.println("1. Add Patient History\n2. View Patient History");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1 : addPatientHistory();
        break;
		case 2 : viewPatientHistory();
        break;
		default : System.out.println("Invalid choice.");
		}
	}

	private static void addPatientHistory() {
		System.out.print("Enter patient name: ");
		String name = scanner.nextLine();
		System.out.print("Enter medical history note: ");
		String note = scanner.nextLine();

		patientHistory.computeIfAbsent(name, k -> new ArrayList<>()).add(note);
		System.out.println("Patient history updated successfully.");
	}

	private static void viewPatientHistory() {
		System.out.print("Enter patient name to view history: ");
		String name = scanner.nextLine();

		List<String> history = patientHistory.get(name);
		if (history != null) {
			System.out.println("\nMedical History for " + name + ":");
			history.forEach(note -> System.out.println("- " + note));
		} else {
			System.out.println("No history found for this patient.");
		}
	}

	private static void generateReports() {
		System.out.println("\nHospital Summary Report:");
		System.out.println("Total Patients: " + patients.size());
		System.out.println("Total Appointments: " + appointments.size());
		System.out.println("Total Staff: " + staffMembers.size());
		System.out.println("Total Inventory Items: " + inventory.size());
	}

	private static void saveData() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
			oos.writeObject(patients);
			oos.writeObject(appointments);
			oos.writeObject(staffMembers);
			oos.writeObject(inventory);
			oos.writeObject(patientHistory);
			System.out.println("Data saved successfully.");
		} catch (IOException e) {
			System.out.println("Error saving data: " + e.getMessage());
		}
	}

	private static void loadData() {
		File file = new File(DATA_FILE);
		if (!file.exists()) return;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			patients.addAll((List<Patient>) ois.readObject());
			appointments.addAll((List<Appointment>) ois.readObject());
			staffMembers.addAll((List<Staff>) ois.readObject());
			inventory.putAll((Map<String, Integer>) ois.readObject());
			patientHistory.putAll((Map<String, List<String>>) ois.readObject());
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading data: " + e.getMessage());
		}
	}

	static class Patient implements Serializable {
		String name;
		int age;
		String contact;

		Patient(String name, int age, String contact) {
			this.name = name;
			this.age = age;
			this.contact = contact;
		}

		@Override
		public String toString() {
			return "Patient{name='" + name + "', age=" + age + ", contact='" + contact + "'}";
		}
	}

	static class Appointment implements Serializable {
		String patientName;
		String date;
		String time;

		Appointment(String patientName, String date, String time) {
			this.patientName = patientName;
			this.date = date;
			this.time = time;
		}

		@Override
		public String toString() {
			return "Appointment{patientName='" + patientName + "', date='" + date + "', time='" + time + "'}";
		}
	}

	static class Staff implements Serializable {
		String name;
		String position;

		Staff(String name, String position) {
			this.name = name;
			this.position = position;
		}

		@Override
		public String toString() {
			return "Staff{name='" + name + "', position='" + position + "'}";
		}
	}
}
