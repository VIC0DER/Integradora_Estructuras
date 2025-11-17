const API = "http://localhost:8080/api";

document.getElementById("btnVer").addEventListener("click", async () => {
    const curp = document.getElementById("curpHist").value;
    const res = await fetch(`${API}/pacientes/${curp}/historial`);
    if (!res.ok) {
        alert("No encontrado");
        return;
    }
    const arr = await res.json();
    const ul = document.getElementById("listaHist");
    ul.innerHTML = "";
    arr.forEach(h => {
        const li = document.createElement("li");
        li.className = "list-group-item";
        li.textContent = `${h.fecha}: ${h.descripcion}`;
        ul.appendChild(li);
    });
});

document.getElementById("btnPop").addEventListener("click", async () => {
    const res = await fetch(`${API}/pacientes/historial/pop`, { method: "POST" });
    if (!res.ok) {
        alert("Pila vacía");
        return;
    }
    const h = await res.json();
    alert("Pop: " + h.descripcion);
});
