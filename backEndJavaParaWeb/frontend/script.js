document.getElementById("btnLlamar").addEventListener("click", () => {

    fetch("http://localhost:8080/hola")
        .then(response => response.text())
        .then(data => {
            document.getElementById("respuesta").textContent = data;
        })
        .catch(err => {
            document.getElementById("respuesta").textContent = "Error al llamar API";
            console.error(err);
        });

});
