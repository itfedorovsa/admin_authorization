CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   surname TEXT NOT NULL,
   login VARCHAR NOT NULL UNIQUE,
   password TEXT NOT NULL,
   department_id INT NOT NULL REFERENCES departments(id),
   phone TEXT DEFAULT 'Fill',
   email TEXT DEFAULT 'Fill',
   timezone TEXT DEFAULT 'UTC'
);