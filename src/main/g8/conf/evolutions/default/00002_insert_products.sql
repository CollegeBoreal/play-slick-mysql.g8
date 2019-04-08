# Sample data for products schema

# --- !Ups

INSERT INTO products (sku, name, description, updated)
VALUES ('bppen-red-na', 'Excella Pen', 'Ballpoint click pen with red body and black clicker, white Excella logo', NOW()),
       ('stynot-wht-std', 'Excella Sticky Notes', 'Standard-sized sticky notes with red and black Excella branding', NOW()),
       ('mug-gry-sma', 'Excella Mug', '8oz gray mug with white Excella branding and red-orange interior', NOW()),
       ('tmblr-stl-med', 'Excella Steel Tumbler', '16oz Steel Tumbler with black Excella branding', NOW());

# --- !Downs

DELETE FROM products
 WHERE sku IN (
    'bppen-red-na',
    'stynot-wht-std',
    'mug-gry-sma',
    'tmblr-stl-med'
);

