// Muestra el formulario de registro de doctores
btnRegistrar.addEventListener("click", () => {
    formContainer.style.display = "block";
});
// Limpia los campos y oculta el formulario de registro de doctores
btnCancelar.addEventListener("click", () => {
    formContainer.style.display = "none";
});
document.getElementById("formRegistro").addEventListener("submit", async (e) => {
    e.preventDefault();

    const paciente = {
        nombre: document.getElementById("inputNombre").value,
        apellidos: document.getElementById("inputApellidos").value,
        sexo: document.querySelector("input[name='radioSex']:checked")?.value || "",
        nacimiento: document.getElementById("inputNacimiento").value,
        curp: document.getElementById("inputCurp").value.trim(),
        nss: document.getElementById("inputNSS").value.trim(),
        telefono: document.getElementById("inputTelefono").value,
        tipoSangre: document.getElementById("selectTipoSangre").value,
        medicamentos: document.getElementById("inputMedicamentos").value,
        alergia: document.getElementById("checkAlergia").checked
    };

    const response = await fetch("http://localhost:8080/api/pacientes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paciente)
    });

    if (response.ok) {
        alert("Paciente registrado con éxito.");
        tablePacientes.ajax.reload();
    }
    e.target.reset();
});
document.addEventListener("DOMContentLoaded", cargarPacientes);
const dataTableBody = document.getElementById('dataTableBody');
const modalBody = document.getElementById('modalBody');
dataTableBody.addEventListener('click', async (event) => {
    const eventTarget = event.target;
    if(eventTarget.classList.contains("btnEliminar")){
        const id = eventTarget.getAttribute("data-id");
        console.log("Eliminar persona con id "+id);

        const respuesta = await fetch(
        `http://localhost:8080/api/pacientes/${id}`,
            {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/json",
                },
            }
        )
        if(respuesta.ok){
            tablePacientes.ajax.reload();
        }
    }
    if(eventTarget.classList.contains("btnVerHistorial")){
        const id = eventTarget.getAttribute("data-id");
        
        const respuesta = await fetch(
        `http://localhost:8080/api/citas/${id}`,
            {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                },
            }
        )
        if(respuesta.ok){
            const respuestaJson = await respuesta.json();
            const citas = respuestaJson.historialCitas;
            modalBody.innerHTML = '';
            citas.forEach(cita => {
                if(cita != null){
                    const card = document.createElement("div");
                    card.className = "card mb-3";
                    card.innerHTML = 
                    `
                        <div class="card-body">
                            <h5 class="card-title">Fecha de atención:</h5>
                            <p class="card-text">${cita.fecha} ${cita.hora}</p>
                        </div>    
                    `;
                    modalBody.appendChild(card);
                }
            });
        }
    }
});
let tablePacientes = null;
async function cargarPacientes() {
    // Inicializa la tabla con los datos obtenidos de la API
    tablePacientes = new DataTable('#dataTablePacientes', {
        ajax: {
            url: "http://localhost:8080/api/pacientes",
            dataSrc: "listaPacientes"
        },
        language: {
            search: "Buscar:",
            lengthMenu: "Mostrar _MENU_ registros por página",
            info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
            paginate: {
                first: "Primero",
                previous: "Anterior",
                next: "Siguiente",
                last: "Último",
            },
            zeroRecords: "No se encontraron registros",
            infoEmpty: "No hay registros disponibles",    
        },
        columns: [
            {
                data: null,
                render: (data, type, row, meta) => {
                    return meta.row + 1
                }
            },
            { data: "nombre" },
            { data: "apellidos"},
            { data: "sexo" },
            { data: "tipoSangre"},
            {
                data: null,
                render: (data, type, row, meta) => {
                    return `<button class="btn btn-primary btnVerHistorial" data-id="${row.id}" data-bs-toggle="modal" data-bs-target="#historialModal">Ver Historial</button>
                    <button class="btn btn-danger btnEliminar" data-id="${row.id}">Eliminar</button>`
                }
            },
        ]
    })
}