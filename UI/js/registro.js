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
