// Create patient
function createPatient(patientData) {
    return fetch('/api/v1/patients/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(patientData),
    }).then(response => response.json());
}

// Get patient by ID
function getPatientById(patientId) {
    return fetch(`/api/v1/patients/byId?id=${patientId}`).then(response => response.json());
}

// Get all patients with pagination
function getAllPatients(page, size) {
    return fetch(`/api/v1/patients/getAll?page=${page}&size=${size}`).then(response => response.json());
}

// Update patient
function updatePatient(patientId, updatedData) {
    return fetch(`/api/v1/patients/update/${patientId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
    }).then(response => response.json());
}

// Delete patient
function deletePatient(patientId) {
    return fetch(`/api/v1/patients/delete/${patientId}`, {
        method: 'DELETE',
    }).then(response => response.status === 204);
}