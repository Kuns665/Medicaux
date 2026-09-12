const API = '/api';
const $ = id => document.getElementById(id);

function message(text, type = 'success') {
    const box = $('message');
    box.textContent = text;
    box.className = type;
    setTimeout(() => box.className = '', 3000);
}

async function request(url, options = {}) {
    const res = await fetch(url, options);
    if (!res.ok) {
        let data = {};
        try { data = await res.json(); } catch (e) {}
        throw new Error(data.message || 'Une erreur est survenue');
    }
    return res.status === 204 ? null : res.json();
}

function showSection(id) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    $(id).classList.add('active');
    refreshAll();
}

async function refreshAll() {
    await Promise.all([loadPatients(), loadSpecialites(), loadMedecins(), loadRdv()]);
    loadDashboard();
}

async function loadDashboard() {
    const [patients, medecins, rdvs] = await Promise.all([
        request(`${API}/patients`), request(`${API}/medecins`), request(`${API}/rendez-vous`)
    ]);
    $('countPatients').textContent = patients.length;
    $('countMedecins').textContent = medecins.length;
    $('countRdv').textContent = rdvs.length;
}

async function loadPatients() {
    const patients = await request(`${API}/patients`);
    $('patientsBody').innerHTML = patients.map(p => `
        <tr><td>${p.nom}</td><td>${p.prenom}</td><td>${p.telephone}</td><td>${p.email || ''}</td>
        <td class="actions"><button onclick="editPatient(${p.id})">Modifier</button>
        <button class="delete" onclick="deletePatient(${p.id})">Supprimer</button></td></tr>`).join('');
    fillSelect('rdvPatient', patients, p => `${p.nom} ${p.prenom}`, 'Choisir un patient');
    window.patientsData = patients;
}

$('patientForm').addEventListener('submit', async e => {
    e.preventDefault();
    const id = $('patientId').value;
    const data = {
        nom: $('patientNom').value,
        prenom: $('patientPrenom').value,
        telephone: $('patientTelephone').value,
        email: $('patientEmail').value
    };
    try {
        await request(`${API}/patients${id ? '/' + id : ''}`, {
            method: id ? 'PUT' : 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        message('Patient enregistré');
        resetPatientForm();
        refreshAll();
    } catch (err) { message(err.message, 'error'); }
});

function editPatient(id) {
    const p = window.patientsData.find(x => x.id === id);
    $('patientId').value = p.id; $('patientNom').value = p.nom;
    $('patientPrenom').value = p.prenom; $('patientTelephone').value = p.telephone;
    $('patientEmail').value = p.email || '';
    showSection('patients');
}

async function deletePatient(id) {
    if (!confirm('Supprimer ce patient ?')) return;
    try { await request(`${API}/patients/${id}`, {method: 'DELETE'}); refreshAll(); }
    catch (err) { message(err.message, 'error'); }
}

function resetPatientForm() { $('patientForm').reset(); $('patientId').value = ''; }

async function loadSpecialites() {
    const data = await request(`${API}/specialites`);
    $('specialitesBody').innerHTML = data.map(s => `
        <tr><td>${s.id}</td><td>${s.nom}</td><td class="actions">
        <button onclick="editSpecialite(${s.id})">Modifier</button>
        <button class="delete" onclick="deleteSpecialite(${s.id})">Supprimer</button></td></tr>`).join('');
    fillSelect('medecinSpecialite', data, s => s.nom, 'Choisir une spécialité');
    window.specialitesData = data;
}

$('specialiteForm').addEventListener('submit', async e => {
    e.preventDefault();
    const id = $('specialiteId').value;
    try {
        await request(`${API}/specialites${id ? '/' + id : ''}`, {
            method: id ? 'PUT' : 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({nom: $('specialiteNom').value})
        });
        message('Spécialité enregistrée');
        resetSpecialiteForm(); refreshAll();
    } catch (err) { message(err.message, 'error'); }
});

function editSpecialite(id) {
    const s = window.specialitesData.find(x => x.id === id);
    $('specialiteId').value = s.id; $('specialiteNom').value = s.nom;
    showSection('specialites');
}
async function deleteSpecialite(id) {
    if (!confirm('Supprimer cette spécialité ?')) return;
    try { await request(`${API}/specialites/${id}`, {method: 'DELETE'}); refreshAll(); }
    catch (err) { message(err.message, 'error'); }
}
function resetSpecialiteForm() { $('specialiteForm').reset(); $('specialiteId').value = ''; }

async function loadMedecins() {
    const data = await request(`${API}/medecins`);
    $('medecinsBody').innerHTML = data.map(m => `
        <tr><td>${m.nom}</td><td>${m.prenom}</td><td>${m.specialite.nom}</td>
        <td>${m.telephone}</td><td class="actions"><button onclick="editMedecin(${m.id})">Modifier</button>
        <button class="delete" onclick="deleteMedecin(${m.id})">Supprimer</button></td></tr>`).join('');
    fillSelect('rdvMedecin', data, m => `Dr. ${m.nom} ${m.prenom} - ${m.specialite.nom}`, 'Choisir un médecin');
    window.medecinsData = data;
}

$('medecinForm').addEventListener('submit', async e => {
    e.preventDefault();
    const id = $('medecinId').value;
    const specialiteId = $('medecinSpecialite').value;
    const data = {
        nom: $('medecinNom').value, prenom: $('medecinPrenom').value,
        telephone: $('medecinTelephone').value, email: $('medecinEmail').value
    };
    try {
        await request(`${API}/medecins${id ? '/' + id : ''}?specialiteId=${specialiteId}`, {
            method: id ? 'PUT' : 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        message('Médecin enregistré'); resetMedecinForm(); refreshAll();
    } catch (err) { message(err.message, 'error'); }
});

function editMedecin(id) {
    const m = window.medecinsData.find(x => x.id === id);
    $('medecinId').value = m.id; $('medecinNom').value = m.nom;
    $('medecinPrenom').value = m.prenom; $('medecinTelephone').value = m.telephone;
    $('medecinEmail').value = m.email || ''; $('medecinSpecialite').value = m.specialite.id;
    showSection('medecins');
}
async function deleteMedecin(id) {
    if (!confirm('Supprimer ce médecin ?')) return;
    try { await request(`${API}/medecins/${id}`, {method: 'DELETE'}); refreshAll(); }
    catch (err) { message(err.message, 'error'); }
}
function resetMedecinForm() { $('medecinForm').reset(); $('medecinId').value = ''; }

async function loadRdv() {
    const data = await request(`${API}/rendez-vous`);
    $('rdvBody').innerHTML = data.map(r => `
        <tr><td>${r.patient.nom} ${r.patient.prenom}</td>
        <td>Dr. ${r.medecin.nom} ${r.medecin.prenom}</td>
        <td>${r.dateRdv}</td><td>${r.heureRdv}</td><td>${r.statut}</td>
        <td class="actions"><button onclick="editRdv(${r.id})">Modifier</button>
        <button class="delete" onclick="deleteRdv(${r.id})">Supprimer</button></td></tr>`).join('');
    window.rdvData = data;
}

$('rdvForm').addEventListener('submit', async e => {
    e.preventDefault();
    const id = $('rdvId').value;
    const patientId = $('rdvPatient').value;
    const medecinId = $('rdvMedecin').value;
    const data = {
        dateRdv: $('rdvDate').value,
        heureRdv: $('rdvHeure').value,
        statut: $('rdvStatut').value
    };
    try {
        await request(`${API}/rendez-vous${id ? '/' + id : ''}?patientId=${patientId}&medecinId=${medecinId}`, {
            method: id ? 'PUT' : 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        message('Rendez-vous enregistré'); resetRdvForm(); refreshAll();
    } catch (err) { message(err.message, 'error'); }
});

function editRdv(id) {
    const r = window.rdvData.find(x => x.id === id);
    $('rdvId').value = r.id; $('rdvPatient').value = r.patient.id;
    $('rdvMedecin').value = r.medecin.id; $('rdvDate').value = r.dateRdv;
    $('rdvHeure').value = r.heureRdv.substring(0,5); $('rdvStatut').value = r.statut;
    showSection('rdv');
}
async function deleteRdv(id) {
    if (!confirm('Supprimer ce rendez-vous ?')) return;
    try { await request(`${API}/rendez-vous/${id}`, {method: 'DELETE'}); refreshAll(); }
    catch (err) { message(err.message, 'error'); }
}
function resetRdvForm() { $('rdvForm').reset(); $('rdvId').value = ''; }

function fillSelect(id, items, label, placeholder) {
    const current = $(id).value;
    $(id).innerHTML = `<option value="">${placeholder}</option>` +
        items.map(x => `<option value="${x.id}">${label(x)}</option>`).join('');
    if ([...$(id).options].some(o => o.value === current)) $(id).value = current;
}

refreshAll().catch(err => message(err.message, 'error'));
