# Imagen base con JDK 17 (coincide con tu Jenkinsfile)
FROM jenkins/jenkins:lts-jdk17

# Cambiamos a root para instalar Maven y procps (necesario para pkill)
USER root

# Instalamos Maven y procps
RUN apt-get update && \
    apt-get install -y maven procps && \
    rm -rf /var/lib/apt/lists/*

# Volvemos al usuario jenkins para que el contenedor corra seguro
USER jenkins