ALTER TABLE usuarios ADD COLUMN perfil VARCHAR(50);

UPDATE usuarios SET perfil = 'CLIENTE' WHERE perfil IS NULL;