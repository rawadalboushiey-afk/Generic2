package generic02;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

interface HospitalRecord {
    String getRecordId();
    String getDepartment();
    void processRecord();
}

class PatientRecord implements HospitalRecord {
    private String patientId;
    private String fullName;
    private String diagnosis;

    public PatientRecord(String patientId, String fullName, String diagnosis) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.diagnosis = diagnosis;
    }

    @Override
    public String getRecordId() {
        return "PAT-" + patientId;
    }

    @Override
    public String getDepartment() {
        return "Emergency & Inpatient Care";
    }

    @Override
    public void processRecord() {
        System.out.println("Processing patient diagnosis: " + diagnosis + " for " + fullName);
    }
}

class MedicalEquipment implements HospitalRecord {
    private String equipmentCode;
    private String deviceName;
    private boolean isSterilized;

    public MedicalEquipment(String equipmentCode, String deviceName, boolean isSterilized) {
        this.equipmentCode = equipmentCode;
        this.deviceName = deviceName;
        this.isSterilized = isSterilized;
    }

    @Override
    public String getRecordId() {
        return "EQP-" + equipmentCode;
    }

    @Override
    public String getDepartment() {
        return "Surgery & Intensive Care Unit";
    }

    @Override
    public void processRecord() {
        System.out.println("Checking equipment status: " + deviceName + " | Sterilization Ready: " + isSterilized);
    }
}

class MedicalVault<T extends HospitalRecord> {
    private T record;
    private String vaultLocation;
    private LocalDateTime entryTimestamp;

    public MedicalVault(String vaultLocation) {
        this.vaultLocation = vaultLocation;
        this.entryTimestamp = LocalDateTime.now();
    }

    public void secureRecord(T record) {
        this.record = record;
    }

    public T retrieveRecord() {
        return record;
    }

    public void displayVaultStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("==================================================");
        System.out.println("Hospital Vault Location: " + vaultLocation);
        System.out.println("System Entry Time: " + entryTimestamp.format(formatter));

        if (record != null) {
            System.out.println("Record Reference ID: " + record.getRecordId());
            System.out.println("Associated Department: " + record.getDepartment());
            System.out.println("Data Object Type: " + record.getClass().getSimpleName());
            record.processRecord();
        } else {
            System.out.println("Status: Vault slot is currently vacant.");
        }
        System.out.println("==================================================\n");
    }
}

public class Generic02 {
    public static void main(String[] args) {
        System.out.println("=== Advanced Smart Medical System Demonstration ===\n");

        PatientRecord patient = new PatientRecord("98214", "Michael Vance", "Acute Appendicitis");
        MedicalVault<PatientRecord> patientVault = new MedicalVault<>("Wing A - Floor 3");
        patientVault.secureRecord(patient);
        patientVault.displayVaultStatus();

        MedicalEquipment equipment = new MedicalEquipment("5501", "Digital Ultrasound Scanner", true);
        MedicalVault<MedicalEquipment> equipmentVault = new MedicalVault<>("Wing B - ICU Sector");
        equipmentVault.secureRecord(equipment);
        equipmentVault.displayVaultStatus();
    }
}