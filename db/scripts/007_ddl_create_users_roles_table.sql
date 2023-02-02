CREATE TABLE IF NOT EXISTS users_roles (
   id SERIAL PRIMARY KEY,
   user_id INT NOT NULL REFERENCES users(id),
   role_id INT NOT NULL REFERENCES roles(id)
);
