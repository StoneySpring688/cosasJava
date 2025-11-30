document.addEventListener('DOMContentLoaded', function () {

const btnAplicarFiltros = document.getElementById('btn-aplicar-filtros');
const btnBorrarFiltros = document.getElementById('btn-borrar-filtros');

const inputPropietario = document.getElementById('filtro-propietario');
const inputEstado = document.getElementById('filtro-estado');
const inputImporteDesde = document.getElementById('filtro-importe-desde');
const inputImporteHasta = document.getElementById('filtro-importe-hasta');
const inputFechaDesde = document.getElementById('filtro-fecha-desde');
const inputFechaHasta = document.getElementById('filtro-fecha-hasta');

// Función para limpiar campos
btnBorrarFiltros.addEventListener('click', () => {
    document.getElementById('filter-form').reset();
    console.log("Filtros limpiados");
});

// Función para aplicar filtros y generar JSON
btnAplicarFiltros.addEventListener('click', () => {
    
    const filtrosDTO = {
        propietario: inputPropietario.value.trim(),
        estado: inputEstado.value,
        
        importeDesde: inputImporteDesde.value ? parseFloat(inputImporteDesde.value) : null,
        importeHasta: inputImporteHasta.value ? parseFloat(inputImporteHasta.value) : null,
        
        // Formato YYYY-MM-DD
        deFecha: inputFechaDesde.value,
        hastaFecha: inputFechaHasta.value
    };

    // TODO BORRAR LOG DEL JSON
    console.log('--- ENVIANDO FILTROS AL BACKEND ---');
    console.log(JSON.stringify(filtrosDTO, null, 2));
    
    // Cerrar el menú tras filtrar
    document.getElementById('filter-menu').style.display = 'none';
});

});