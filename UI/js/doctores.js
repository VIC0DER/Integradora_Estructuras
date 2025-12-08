const inputNombre = document.getElementById("inputNombreDoctor");
const inputEspecialidad = document.getElementById("inputEspecialidadDoctor");
const btnGuardarDoctor = document.getElementById("btnGuardarDoctor");
const btnCancelar = document.getElementById("btnCancelar");
const btnRegistrar = document.getElementById("btnRegistrar");
const formContainer = document.getElementById("formContainer");

// Inicializa la tabla de doctores 
document.addEventListener("DOMContentLoaded", obtenerDoctores);
// Muestra el formulario de registro de doctores
btnRegistrar.addEventListener("click", () => {
    formContainer.style.display = "block";
});
// Limpia los campos y oculta el formulario de registro de doctores
btnCancelar.addEventListener("click", ocultarFormulario);
// Guarda un nuevo doctor mediante una solicitud POST a la API
btnGuardarDoctor.addEventListener("click", async () => {
    const obj = {};
    obj.nombre = inputNombre.value;
    obj.especialidad = inputEspecialidad.value;
    obj.disponible = false;
    const response = await fetch(
        'http://localhost:8080/api/doctores',
        {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(obj)
        }
    );
    if(response.ok){
        tableDoctores.ajax.reload();
        ocultarFormulario();
    } 
});

function ocultarFormulario() {
    inputNombre.value = "";
    inputEspecialidad.value = "";
    formContainer.style.display = "none";
}

const dataTableBody = document.getElementById("dataTableBody");
dataTableBody.addEventListener("click", async (event) => {
    const eventTarget = event.target;

    if(eventTarget.classList.contains("switchChecked") || eventTarget.classList.contains("switchDefault")){
        const id = eventTarget.getAttribute("data-id");
        let disponible;
        let isSwitchChecked = eventTarget.classList.contains("switchChecked");
        isSwitchChecked ? disponible = 0 : disponible = 1;
        const response = await fetch(
            `http://localhost:8080/api/doctores/${id}/${disponible}`,
            {
                method: 'PATCH',
                headers: {
                    "Content-Type": "application/json",
                }
            }
        )
        if(response.ok){
            tableDoctores.ajax.reload();
            if(isSwitchChecked){
                eventTarget.classList.remove("switchChecked");
                eventTarget.classList.add("switchDefault");
            }else{
                eventTarget.classList.remove("switchDefault");
                eventTarget.classList.add("switchChecked");
            }
        }else{
            alert("Error al actualizar disponibilidad del doctor.");
        }
    }
});

let tableDoctores = null;
async function obtenerDoctores() {
    // Inicializa la tabla con los datos obtenidos de la API
    tableDoctores = new DataTable('#dataTableDoctores', {
        ajax: {
            url: "http://localhost:8080/api/doctores",
            dataSrc: "listaDoctores"
        },
        language: {
            loadingRecords: "Cargando...",
            search: "Buscar:",
            lengthMenu: "Mostrar _MENU_ registros por página",
            info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
            paginate: {
                first: "Primero",
                previous: "Anterior",
                next: "Siguiente",
                last: "Último",
            },
            zeroRecords: "No se encontraron registros coincidentes",
            infoEmpty: "Aún no hay registros disponibles",    
        },
        columns: [
            {
                data: null,
                render: (data, type, row, meta) => {
                    return meta.row + 1
                }
            },
            { data: "nombre" },
            { data: "especialidad" },
            {   data: null,
                render: (data, type, row, meta) => {
                    return row.disponible ? 
                        `<div class="form-check form-switch">
                            <input class="form-check-input switchChecked" type="checkbox" role="switch" data-id="${row.id}" checked>
                        </div>` : 
                        `<div class="form-check form-switch">
                            <input class="form-check-input switchDefault" type="checkbox" role="switch" data-id="${row.id}">
                        </div>`
                }
            },
        ]
    })
}