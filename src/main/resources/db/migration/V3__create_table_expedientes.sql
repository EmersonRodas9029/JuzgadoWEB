CREATE TABLE IF NOT EXISTS expedientes (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           numero VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    fecha DATE NOT NULL,
    ubicacion_fisica VARCHAR(255),
    bodega VARCHAR(100),
    observaciones TEXT,
    usuario_creador_id BIGINT,
    FOREIGN KEY (usuario_creador_id) REFERENCES usuarios(id) ON DELETE SET NULL
    );