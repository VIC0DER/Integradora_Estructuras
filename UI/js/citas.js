document.getElementById("formCita").addEventListener("submit", async (e) => {
    e.preventDefault();

    const cita = {
        curp: document.getElementById("inputCurp").value.trim(),
        departamento: document.getElementById("inputDepartamento").value,
        fechaHora: document.getElementById("inputFechaHora").value,
        motivo: document.getElementById("inputMotivo").value
    };

    const response = await fetch("http://localhost:8080/api/citas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cita)
    });

    if (response.ok) {
        alert("Cita agendada correctamente.");
        e.target.reset();
    } else {
        alert("Error al agendar cita. Verifica la CURP primero.");
    }
});
