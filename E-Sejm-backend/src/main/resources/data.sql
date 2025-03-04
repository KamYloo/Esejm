INSERT INTO role (id, name)
SELECT 1, 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE id = 1);

INSERT INTO role (id, name)
SELECT 2, 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE id = 2);

INSERT INTO role (id, name)
SELECT 3, 'MODERATOR'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE id = 3);

INSERT INTO categories (id, name)
SELECT 1, 'POLSKA' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'POLSKA'
);
INSERT INTO categories (id, name)
SELECT 2, 'EUROPA' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'EUROPA'
);
INSERT INTO categories (id, name)
SELECT 3, 'SWIAT' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'SWIAT'
);
INSERT INTO categories (id, name)
SELECT 4, 'WOJSKO' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'WOJSKO'
);
INSERT INTO categories (id, name)
SELECT 5, 'GOSPODARKA' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'GOSPODARKA'
);
INSERT INTO categories (id, name)
SELECT 6, 'EKOLOGIA' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'EKOLOGIA'
);
INSERT INTO categories (id, name)
SELECT 7, 'FINANSE' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'FINANSE'
);
INSERT INTO categories (id, name)
SELECT 8, 'WYBORY' WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE name = 'WYBORY'
);