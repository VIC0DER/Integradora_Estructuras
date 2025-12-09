const btnGuardar = document.getElementById("btnGuardar");
const btnCancelar = document.getElementById("btnCancelar");
const btnRegistrar = document.getElementById("btnRegistrar");
const formContainer = document.getElementById("formContainer");
let citasActivas = [];
// Inicializa la tabla de citas
document.addEventListener("DOMContentLoaded", obtenerCitas);
// Muestra el formulario de registro de doctores
btnRegistrar.addEventListener("click", () => {
    formContainer.style.display = "block";
});
btnCancelar.addEventListener("click", () => {
    formContainer.style.display = "none";
});

document.getElementById("formCita").addEventListener("submit", async (e) => {
    e.preventDefault();

    const cita = {
        pacienteCurp: document.getElementById("inputCurp").value.trim(),
        departamento: document.getElementById("inputDepartamento").value,
        fecha: document.getElementById("inputFecha").value,
        hora: document.getElementById("inputHora").value,
        motivo: document.getElementById("inputMotivo").value
    };

    const response = await fetch("http://localhost:8080/api/citas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cita)
    });

    if (response.ok) {
        //alert("Cita agendada correctamente.");
        tableCitas.ajax.reload();
    } else {
        alert("Error al agendar cita. Verifica la CURP primero.");
    }
    e.target.reset();
});

// Simula la atención de citas 
let atenderCita = setInterval(async () => {
    let table = $('#dataTableCitas').DataTable();
    let cantidadRegistros = table.data().count();
    if(cantidadRegistros > 0){
        const response = await fetch(
            `http://localhost:8080/api/citas/atender-siguiente`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                }
            }
        )
        if(response.ok){
            table.ajax.reload();
        } else {
            const responseJson = await response.json();
            console.log(responseJson.mensaje);
        }
    }
}, 10000);

let tableCitas = null;
async function obtenerCitas() {
    // Inicializa la tabla con los datos obtenidos de la API
    tableCitas = new DataTable('#dataTableCitas', {
        ajax: {
            url: "http://localhost:8080/api/citas",
            dataSrc: "listaCitas"
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
            { data: "pacienteCurp" },
            { data: "departamento" },
            { data: "fecha" },
            { data: "hora" },
            {   data: null,
                render: (data, type, row, meta) => {
                    return `<b class="${pintarEtiquetaEstado(row.estado)}" data-id="${row.id}">${row.estado}</b>`
                }
            },
            /*{
                data: null,
                render: (data, type, row, meta) => {
                    return `<button class="btn btn-danger btnEliminar" data-id="${row.id}">Eliminar</button>`
                }
            },*/
        ]
    })
}
function pintarEtiquetaEstado(estado) {
    switch(estado.toLowerCase()){
        case 'solicitada': return 'text-primary';
        case 'atendida': return 'text-success';
        case 'cancelada': return 'text-danger';
    }
}
