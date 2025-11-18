document.getElementById("formRegistro").addEventListener("submit", async (e) => {
    e.preventDefault();

    const paciente = {
        nombre: document.getElementById("inputNombre").value,
        apellidos: document.getElementById("inputApellidos").value,
        sexo: document.querySelector("input[name='radioSex']:checked")?.value || "",
        fechaNacimiento: document.getElementById("inputNacimiento").value,
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
        e.target.reset();
    } else {
        alert("Error al registrar paciente.");
    }
});
document.addEventListener("DOMContentLoaded", cargarPacientes);
const dataTableBody = document.getElementById('dataTableBody');
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
    }
});
let tablePacientes = null;
async function cargarPacientes() {
    tablePacientes = new DataTable('#dataTablePacientes', {
        ajax: {
            url: "http://localhost:8080/api/pacientes",
            dataSrc: "listaPacientes"
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
                    return `<button class="btn btn-danger btnEliminar" data-id="${row.id}">Eliminar</button>`
                }
            },
        ]
    })
}