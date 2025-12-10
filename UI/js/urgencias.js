const inputCurp = document.getElementById("inputCurp");
const selectDoctor = document.getElementById("selectDoctor");
let prioridad;
const btnGuardar = document.getElementById("btnGuardar");
const btnCancelar = document.getElementById("btnCancelar");
const formContainer = document.getElementById("formContainer");
const btnRegistrar = document.getElementById("btnRegistrar");
let registroUrgencias = [];
const cardContainer = document.getElementById("card-container");
const atendidasContainer = document.getElementById("atendidas-container");
const btnVerAtendidas = document.getElementById("btnVerAtendidas");
const btnVerActivas = document.getElementById("btnVerActivas");
let viendoActivas = true;


btnVerAtendidas.addEventListener("click", () => {
    cardContainer.style.display = "none";
    atendidasContainer.style.display = "flex";
    viendoActivas = false;
    cargarContenido();
});
btnVerActivas.addEventListener("click", () => {
    cardContainer.style.display = "flex";
    atendidasContainer.style.display = "none";
    viendoActivas = true;
    cargarContenido();
})


document.addEventListener("DOMContentLoaded", () => {
    cargarContenido();
});

// Carga la lista de registros de urgencias y los muestra en tarjetas
async function cargarContenido(){
    const responseDoctores = await fetch(
        'http://localhost:8080/api/doctores/disponibles',
        {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
            }
        }
    );
    if(responseDoctores.ok){
        const responseJson = await responseDoctores.json();
        const doctores = responseJson.listaDoctores;
        selectDoctor.innerHTML = "<option value='0'>Seleccione un doctor</option>";
        doctores.forEach(doctor => {
            if(doctor.disponible === true){
                const option = document.createElement("option");
                option.value = doctor.id;
                option.textContent = `${doctor.nombre} - ${doctor.especialidad}`;
                selectDoctor.appendChild(option);
            }
        });
    }


    registroUrgencias = [];
    const responseUrgencias = await fetch(
        'http://localhost:8080/api/urgencias',
        {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
            }
        }
    );
    if(responseUrgencias.ok){
        const urgenciasJson = await responseUrgencias.json();
        const activas = urgenciasJson.urgenciasActivas;
        
        cardContainer.innerHTML = '';
        atendidasContainer.innerHTML = '';
        if(viendoActivas){
            activas.forEach(urgencia => {
                if(urgencia != null){
                    registroUrgencias.push(urgencia);
                    let mensaje;
                    switch(urgencia.prioridad){
                        case 1: mensaje = "Alta"; break;
                        case 2: mensaje = "Emergencia"; break;
                        case 3: mensaje = "Moderada"; break;
                        case 4: mensaje = "Urgencia Menor"; break;
                        default: mensaje = "No Urgente"; break;
                    }
                    const col = document.createElement("div");
                    col.className = "col-4";
                    const card = document.createElement("div");
                    card.className = "card mb-3";
                    card.innerHTML = 
                    `
                        <div class="card-body">
                            <h5 class="card-title">CURP: ${urgencia.pacienteCurp}</h5>
                            <p class="card-text">${urgencia.doctor == null ? "<button class='btn btn-primary'>Aisgnar doctor</button>" : urgencia.doctor.nombre}</p>
                            <p class="card-text">Prioridad: ${mensaje}</p>
                        </div>    
                    `;
                    col.appendChild(card);
                    cardContainer.appendChild(col);
                }
            });
        }else {
            const atendidas = urgenciasJson.urgenciasAtendidas;
            atendidas.forEach(urgencia => {
                if(urgencia != null){
                    const col = document.createElement("div");
                    col.className = "col-4";
                    const card = document.createElement("div");
                    card.className = "card mb-3";
                    card.innerHTML = 
                    `
                        <div class="card-body">
                            <h5 class="card-title">CURP: ${urgencia.pacienteCurp}</h5>
                            <p class="card-text">${urgencia.doctor.nombre}</p>
                            <p class="card-text">${urgencia.fechaDeAlta}</p>
                        </div>    
                    `;
                    col.appendChild(card);
                    atendidasContainer.appendChild(col);
                }
            });
        }


    }  
}

// Cambia el estado de la urgencia a atendida y el doctor a cargo vuelve a estar disponible 
let atenderUrgencia = setInterval(async () => { 
    if(registroUrgencias.length > 0){
        console.log(registroUrgencias.length);
        console.log("Atendiendo urgencia con prioridad "+registroUrgencias[0].prioridad);
        let doctor = registroUrgencias[0].doctor;
        if(doctor != null){
            const response = await fetch(
                `http://localhost:8080/api/doctores/${doctor.id}/${1}`,
                {
                    method: 'PATCH',
                    headers: {
                        "Content-Type": "application/json",
                    }
                }
            )
            const responseUrgencia = await fetch(
                `http://localhost:8080/api/urgencias/${registroUrgencias[0].id}/atendida`,
                {
                    method: 'PATCH',
                    headers: {
                        "Content-Type": "application/json",
                    }
                }
            )
            if(responseUrgencia.ok && response.ok){
                cargarContenido();
            }
        }else{
            
        }

        

    }

    
}, 3000);

// Muestra el formulario de registro de urgencias
btnRegistrar.addEventListener("click", () => {
    formContainer.style.display = "block";
});
// Limpia los campos y oculta el formulario de registro
btnCancelar.addEventListener("click", ocultarFormulario);
// Guarda un nuevo registro mediante una solicitud POST a la API
btnGuardar.addEventListener("click", async () => {
    prioridad = document.querySelector('input[name="prioridad"]:checked');
    const obj = {};
    obj.pacienteCurp = inputCurp.value;
    obj.doctorId = selectDoctor.value;
    obj.atendida = false;
    if(prioridad != null){
        obj.prioridad = prioridad.value; 
        const response = await fetch(
            'http://localhost:8080/api/urgencias',
            {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(obj)
            }
        );
        if(response.ok){
            ocultarFormulario();
            cargarContenido();
        }
    }

});

function ocultarFormulario() {
    prioridad = document.querySelector('input[name="prioridad"]:checked');
    inputCurp.value = "";
    selectDoctor.value = "";
    if(prioridad != null) prioridad.checked = false;
    formContainer.style.display = "none";
}