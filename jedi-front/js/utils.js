class JediUtils {
    static getStatusClass(status) {
        switch (status) {
            case 'Padawan':
                return 'status-padawan';
            case 'Jedi':
                return 'status-jedi';
            case 'Mestre Jedi':
                return 'status-mestre-jedi';
            default:
                return '';
        }
    }

    static formatMidichlorians(value) {
        return new Intl.NumberFormat('pt-BR').format(value);
    }

    static showAlert(message, type = 'success') {
        const alert = document.createElement('div');
        alert.className = `alert alert-${type}`;
        alert.textContent = message;
        
        document.body.appendChild(alert);
        
        setTimeout(() => {
            alert.classList.add('fade-out');
            setTimeout(() => alert.remove(), 500);
        }, 3000);
    }

    static debounce(func, wait) {
        let timeout;
        return function(...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    }
    static showModal(contentHtml) {
    const overlay = document.createElement('div');
    overlay.className = 'modal-overlay';

    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">${contentHtml}</div>
        <button class="sw-button close-modal">Fechar</button>
    `;

    overlay.appendChild(modal);
    document.body.appendChild(overlay);

    overlay.querySelector('.close-modal').addEventListener('click', () => overlay.remove());
}

}

// Estilos dinâmicos para alertas
const style = document.createElement('style');
style.textContent = `
.alert {
    position: fixed;
    top: 20px;
    right: 20px;
    padding: 15px 20px;
    border-radius: 4px;
    color: white;
    font-weight: bold;
    z-index: 1000;
    opacity: 1;
    transition: opacity 0.5s ease;
}

.alert-success {
    background-color: #4CAF50;
}

.alert-error {
    background-color: #F44336;
}

.alert-warning {
    background-color: #FF9800;
}

.fade-out {
    opacity: 0;
}
`;
document.head.appendChild(style);