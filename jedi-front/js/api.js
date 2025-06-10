// const API_BASE_URL = 'http://localhost:8108/jedi';
const API_BASE_URL = '/api/jedi';
class JediApi {
    static async handleError(response) {
        let message = 'Erro desconhecido';

        try {
            const errorData = await response.json();
            message = errorData.message || message;
        } catch (_) {
            // Se não for possível ler JSON, mantém a mensagem padrão
        }

        throw new Error(message);
    }

    static async getAllJedis() {
        try {
            const response = await fetch(API_BASE_URL);
            if (!response.ok) {
                return this.handleError(response);
            }
            return await response.json();
        } catch (error) {
            console.error('Erro na API:', error);
            throw error;
        }
    }

    static async getJediById(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`);
            if (!response.ok) {
                return this.handleError(response);
            }
            return await response.json();
        } catch (error) {
            console.error('Erro na API:', error);
            throw error;
        }
    }

    static async createJedi(jediData) {
        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jediData),
            });

            if (!response.ok) {
                return this.handleError(response);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro na API:', error);
            throw error;
        }
    }

    static async updateJedi(id, jediData) {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jediData),
            });

            if (!response.ok) {
                return this.handleError(response);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro na API:', error);
            throw error;
        }
    }

    static async deleteJedi(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                return this.handleError(response);
            }

            return true;
        } catch (error) {
            console.error('Erro na API:', error);
            throw error;
        }
    }
    static async getMestresEAprendizes() {
    try {
        const response = await fetch(`${API_BASE_URL}/mestres-aprendizes`);
        if (!response.ok) {
            throw new Error('Erro ao buscar mestres e aprendizes');
        }
        return await response.json();
    } catch (error) {
        console.error('Erro na API:', error);
        throw error;
    }
}

}

