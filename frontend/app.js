// app.js
const BASE_URL = "http://localhost:8080/mibancoapi/gastos";

const tablaGastos = document.getElementById("tablaGastos");
const totalGastos = document.getElementById("totalGastos");
const btnAgregar = document.getElementById("btnAgregar");
const btnFiltrar = document.getElementById("btnFiltrar");
const mesFiltro = document.getElementById("mesFiltro");
const anioFiltro = document.getElementById("anioFiltro");

const modalGasto = new bootstrap.Modal(document.getElementById("modalGasto"));
const formGasto = document.getElementById("formGasto");
const modalTitulo = document.getElementById("modalTitulo");

let gastos = [];

// Cargar gastos iniciales
document.addEventListener("DOMContentLoaded", () => {
  cargarGastos();
});

// Fetch GET
async function cargarGastos(mes = null, anio = null) {
  try {
    let url = BASE_URL;
    if (mes && anio) {
      url += `/mes-anio?mes=${mes}&anio=${anio}`;
    }
    console.log(url);
    const resp = await fetch(url);
    if (!resp.ok) throw new Error("Error al obtener gastos");
    gastos = await resp.json();
    console.log(gastos);
    renderTabla();
  } catch (error) {
    Swal.fire("Error", error.message, "error");
  }
}

// Render tabla
function renderTabla() {
  tablaGastos.innerHTML = "";
  let total = 0;
  gastos.forEach(g => {
    total += g.monto;
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${g.titulo}</td>
      <td>${g.motivo}</td>
      <td>${g.fecha}</td>
      <td>${g.monto.toFixed(2)}</td>
      <td><button class="btn btn-warning btn-sm" onclick="editarGasto('${g.id}')">Editar</button></td>
      <td><button class="btn btn-danger btn-sm" onclick="eliminarGasto('${g.id}')">Eliminar</button></td>
    `;
    tablaGastos.appendChild(fila);
  });
  totalGastos.textContent = total.toFixed(2);
}

// Filtrar
btnFiltrar.addEventListener("click", () => {
  const mes = mesFiltro.value;
  const anio = anioFiltro.value;
  if (mes && anio) {
    cargarGastos(mes, anio);
  } else {
    cargarGastos();
  }
});

// Agregar
btnAgregar.addEventListener("click", () => {
  formGasto.reset();
  document.getElementById("gastoId").value = "";
  modalTitulo.textContent = "Agregar Gasto";
  modalGasto.show();
});

// Guardar (Crear/Editar)
formGasto.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("gastoId").value;
  const titulo = document.getElementById("titulo").value.trim();
  const motivo = document.getElementById("motivo").value.trim();
  const fecha = document.getElementById("fecha").value;
  const monto = parseFloat(document.getElementById("monto").value);

  if (!titulo || !motivo || !fecha || isNaN(monto) || monto <= 0) {
    Swal.fire("Error", "Todos los campos son obligatorios y válidos", "error");
    return;
  }

  const gasto = { titulo, motivo, fecha, monto };

  try {
    let resp;
    if (id) {
      resp = await fetch(`${BASE_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(gasto)
      });
      if (!resp.ok) throw new Error("Error al actualizar gasto");
      Swal.fire("Éxito", "Gasto actualizado correctamente", "success");
    } else {
      resp = await fetch(BASE_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(gasto)
      });
      if (!resp.ok) throw new Error("Error al crear gasto");
      Swal.fire("Éxito", "Gasto creado correctamente", "success");
    }
    modalGasto.hide();
    cargarGastos();
  } catch (error) {
    Swal.fire("Error", error.message, "error");
  }
});

// Editar gasto
window.editarGasto = (id) => {
  const gasto = gastos.find(g => g.id === id);
  if (!gasto) return;

  document.getElementById("gastoId").value = gasto.id;
  document.getElementById("titulo").value = gasto.titulo;
  document.getElementById("motivo").value = gasto.motivo;
  document.getElementById("fecha").value = gasto.fecha;
  document.getElementById("monto").value = gasto.monto;

  modalTitulo.textContent = "Editar Gasto";
  modalGasto.show();
};

// Eliminar gasto
window.eliminarGasto = async (id) => {
  const confirmacion = await Swal.fire({
    title: "¿Eliminar gasto?",
    text: "Esta acción no se puede deshacer",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Sí, eliminar",
    cancelButtonText: "Cancelar"
  });

  if (confirmacion.isConfirmed) {
    try {
      const resp = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
      if (!resp.ok) throw new Error("Error al eliminar gasto");
      Swal.fire("Eliminado", "El gasto fue eliminado correctamente", "success");
      cargarGastos();
    } catch (error) {
      Swal.fire("Error", error.message, "error");
    }
  }
};
