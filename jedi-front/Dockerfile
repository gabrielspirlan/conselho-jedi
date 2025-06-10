# /jedi-front/Dockerfile

# Estágio 1: Build (se você tivesse um build, seria aqui. Para HTML/JS, pulamos)

# Estágio 2: Produção
FROM nginx:alpine

# Remove a configuração padrão do NGINX
RUN rm /etc/nginx/conf.d/default.conf

# Copia seus arquivos de frontend (HTML, CSS, JS) para a pasta que o NGINX serve
COPY . /usr/share/nginx/html

# Copia o seu novo arquivo de configuração do NGINX
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expõe a porta 80 (padrão do NGINX)
EXPOSE 80

# Comando para iniciar o NGINX
CMD ["nginx", "-g", "daemon off;"]