document.addEventListener('DOMContentLoaded', () => {
    // Elementos do DOM
    const jediForm = document.getElementById('jediForm');
    const jediList = document.getElementById('jediList');
    const searchInput = document.getElementById('searchInput');
    const clearBtn = document.getElementById('clearBtn');

    // Estado da aplicação
    let jedis = [];

    // Inicialização
    loadJedis();

    // Event Listeners
    jediForm.addEventListener('submit', handleFormSubmit);
    clearBtn.addEventListener('click', clearForm);
    searchInput.addEventListener('input', JediUtils.debounce(filterJedis, 300));

    // Funções
    async function loadJedis() {
        try {
            showLoading();
            jedis = await JediApi.getAllJedis();
            renderJediList(jedis);
        } catch (error) {
            console.error('Erro ao carregar Jedis:', error);
            JediUtils.showAlert(error.message, 'error');
            renderEmptyState();
        }
    }

    function showLoading() {
        jediList.innerHTML = `
            <div class="loading">
                <div class="loading-spinner"></div>
            </div>
        `;
    }

    function renderEmptyState() {
        jediList.innerHTML = `
            <div class="empty-state">
                <p>Nenhum Jedi encontrado</p>
            </div>
        `;
    }

    function renderJediList(jedisToRender) {
        jedisToRender.sort((a, b) => a.id - b.id);
        if (jedisToRender.length === 0) {
            renderEmptyState();
            return;
        }

        jediList.innerHTML = jedisToRender.map(jedi => `
            <div class="jedi-card fade-in" data-id="${jedi.id}">
                <h3>${jedi.name}</h3>
                <p>ID: ${jedi.id}</p>
                <p>Midichlorians: <span class="midichlorians">${JediUtils.formatMidichlorians(jedi.midichlorians)}</span></p>
                <p>Status: <span class="status ${JediUtils.getStatusClass(jedi.status)}">${jedi.status}</span></p>
                ${jedi.mentor ? `<p class="mentor-info">Mentor: ${jedi.mentor.name}</p>` : ''}
                <div class="card-actions">
                    <button class="sw-button sw-button-secondary details-btn" data-id="${jedi.id}">Detalhes</button>
                    <button class="sw-button edit-btn" data-id="${jedi.id}">Editar</button>
                    <button class="sw-button sw-button-danger delete-btn" data-id="${jedi.id}">Excluir</button>
                </div>
            </div>
        `).join('');

        // Adiciona event listeners aos botões
        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                editJedi(btn.dataset.id);
            });
        });

        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                deleteJedi(btn.dataset.id);
            });
        });

        // Adiciona event listener aos cards
        document.querySelectorAll('.jedi-card').forEach(card => {
            card.addEventListener('click', () => {
                const jediId = card.dataset.id;
                showJediDetails(jediId);
            });
        });
        document.querySelectorAll('.details-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                showJediDetails(btn.dataset.id);
            });
        });

    }

    function filterJedis() {
        const searchTerm = searchInput.value.toLowerCase();
        if (!searchTerm) {
            renderJediList(jedis);
            return;
        }

        const filtered = jedis.filter(jedi =>
            jedi.name.toLowerCase().includes(searchTerm) ||
            jedi.id.toString().includes(searchTerm)
        );

        renderJediList(filtered);
    }

    async function handleFormSubmit(e) {
        e.preventDefault();

        const jediId = document.getElementById('jediId').value;
        const jediData = {
            name: document.getElementById('name').value,
            status: document.getElementById('status').value,
            midichlorians: parseInt(document.getElementById('midichlorians').value),
            mentor_id: document.getElementById('mentorId').value ? parseInt(document.getElementById('mentorId').value) : null
        };

        try {
            if (jediId) {
                await JediApi.updateJedi(jediId, jediData);
                JediUtils.showAlert('Jedi atualizado com sucesso!');
            } else {
                await JediApi.createJedi(jediData);
                JediUtils.showAlert('Jedi criado com sucesso!');
            }

            clearForm();
            loadJedis();
        } catch (error) {
            console.error('Erro ao salvar Jedi:', error);
            JediUtils.showAlert('O ID do Mentor não exite no sistema. Por favor, revise o cadastro', 'error');
        }
    }

    async function editJedi(id) {
        try {
            const jedi = await JediApi.getJediById(id);

            document.getElementById('jediId').value = jedi.id;
            document.getElementById('name').value = jedi.name;
            document.getElementById('status').value = jedi.status;
            document.getElementById('midichlorians').value = jedi.midichlorians;
            document.getElementById('mentorId').value = jedi.mentor_id || '';
            document.querySelector('.jedi-form-section').scrollIntoView({ behavior: 'smooth' });
        } catch (error) {
            console.error('Erro ao editar Jedi:', error);
            JediUtils.showAlert(error.message, 'error');
        }
    }

    async function deleteJedi(id) {
        if (!confirm('Tem certeza que deseja excluir este Jedi?')) {
            return;
        }

        try {
            await JediApi.deleteJedi(id);
            JediUtils.showAlert('Jedi excluído com sucesso!');
            loadJedis();
        } catch (error) {
            console.error('Erro ao excluir Jedi:', error);
            JediUtils.showAlert('O Jedi possui aprendizes vinculados, exclua os aprendizes primeiro.', 'error');
        }
    }

    function clearForm() {
        jediForm.reset();
        document.getElementById('jediId').value = '';
    }

    async function showJediDetails(id) {
        try {
            const jedis = await JediApi.getAllJedis();
            const clickedJedi = jedis.find(j => j.id == id);

            if (!clickedJedi) {
                throw new Error("Jedi não encontrado");
            }

            const lista = await JediApi.getMestresEAprendizes();
            const mestre = lista.find(item => item.id == parseInt(id));

            let detalhes = `
            <h3>${clickedJedi.name} - Detalhes</h3>
            <p>Status: ${clickedJedi.status}</p>
            <p>Midichlorians: ${JediUtils.formatMidichlorians(clickedJedi.midichlorians)}</p>
        `;

            if (mestre && Array.isArray(mestre.apprentices) && mestre.apprentices.length > 0) {
                detalhes += `
                <h4>Aprendizes:</h4>
                <ul>
                    ${mestre.apprentices.map(ap => `<li>${ap.name} (ID: ${ap.id})</li>`).join('')}
                </ul>
            `;
            } else {
                detalhes += `<p>Este Jedi não possui aprendizes.</p>`;
            }

            JediUtils.showModal(detalhes);
        } catch (error) {
            console.error('Erro ao carregar detalhes:', error);
            JediUtils.showAlert('Erro ao carregar detalhes do Jedi', 'error');
        }
    }



});
