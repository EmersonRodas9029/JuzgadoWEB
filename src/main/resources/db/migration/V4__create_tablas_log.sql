CREATE TABLE IF NOT EXISTS logs (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    accion VARCHAR(50) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expediente_id BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (expediente_id) REFERENCES expedientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
    );