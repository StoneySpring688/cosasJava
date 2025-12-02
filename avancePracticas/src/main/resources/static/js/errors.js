document.addEventListener('DOMContentLoaded', function () {
    // --- SISTEMA DE LOGS / POP-UP ---

    const popupElement = document.getElementById('system-message-popup');
    const popupContent = document.getElementById('popup-content-area');
    const btnClosePopup = document.getElementById('btn-close-popup');

    // Objeto global para usar en cualquier parte de tu código
    window.Logger = {

        // Método para mostrar un mensaje normal
        log: function (message) {
            this._appendMessage(message, 'normal');
        },

        // Método para mostrar un error (rojo)
        error: function (message) {
            this._appendMessage(message, 'error');
        },

        // Método privado que gestiona la lógica
        _appendMessage: function (text, type) {
            // 1. Si está oculto, mostrarlo
            if (popupElement.style.display === 'none') {
                popupElement.style.display = 'flex';
            }

            // 2. Crear el elemento de párrafo
            const p = document.createElement('div');
            p.classList.add('msg-log');

            // Añadir clase de error si corresponde
            if (type === 'error') {
                p.classList.add('msg-error');
            }

            // 3. Añadir Timestamp (opcional, pero útil)
            const time = new Date().toLocaleTimeString();
            p.innerHTML = `<span class="msg-time">[${time}]</span> ${text}`;

            // 4. Agregar al contenedor (Append)
            popupContent.appendChild(p);

            // 5. Scroll automático al fondo para ver lo último
            popupContent.scrollTop = popupContent.scrollHeight;
        },

        // Método para limpiar y cerrar
        close: function () {
            popupElement.style.display = 'none';
            popupContent.innerHTML = ''; // Limpiar mensajes
        }
    };

    // Evento para el botón de cerrar (X)
    btnClosePopup.addEventListener('click', () => {
        window.Logger.close();
    });
});