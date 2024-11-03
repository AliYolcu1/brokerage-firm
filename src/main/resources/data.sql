-- Customers
INSERT INTO customers (id, full_name, username, password)
VALUES
    (1, 'John Doe', 'johndoe', '123456'),
    (2, 'Jane Smith', 'janesmith', '123456'),
    (3, 'Michael Johnson', 'michaelj', '123456'),
    (4, 'Sarah Williams', 'sarahw', '123456'),
    (5, 'David Brown', 'davidb', '123456');

-- Employees
INSERT INTO employees (id, full_name, username, password)
VALUES
    (1, 'Ali Yilmaz', 'aliyilmaz', '123456'),
    (2, 'Ayse Demir', 'aysedemir', '123456'),
    (3, 'Mehmet Kaya', 'mehmetk', '123456'),
    (4, 'Zeynep Celik', 'zeynepc', '123456'),
    (5, 'Can Ozturk', 'canoz', '123456');

-- TRY varlıkları
INSERT INTO assets (customer_id, asset_name, size, usable_size)
VALUES
    (1, 'TRY', 10000.00, 10000.00),
    (2, 'TRY', 15000.00, 15000.00),
    (3, 'TRY', 20000.00, 20000.00),
    (4, 'TRY', 17500.00, 17500.00),
    (5, 'TRY', 12500.00, 12500.00);

-- Diğer varlıklar
INSERT INTO assets (customer_id, asset_name, size, usable_size)
VALUES
    (1, 'AAPL', 100.00, 100.00),
    (1, 'GOOGL', 50.00, 50.00),
    (2, 'MSFT', 75.00, 75.00),
    (2, 'AAPL', 80.00, 80.00),
    (3, 'TSLA', 30.00, 30.00),
    (3, 'AMZN', 20.00, 20.00),
    (4, 'NVDA', 40.00, 40.00),
    (5, 'META', 60.00, 60.00);

-- Örnek siparişler
INSERT INTO orders (customer_id, asset_name, order_side, size, price, status, create_date)
VALUES
    (1, 'AAPL', 'BUY', 100.00, 150.00, 'PENDING', CURRENT_TIMESTAMP()),
    (1, 'GOOGL', 'SELL', 50.00, 250.00, 'PENDING', CURRENT_TIMESTAMP()),
    (2, 'MSFT', 'BUY', 75.00, 350.00, 'MATCHED', CURRENT_TIMESTAMP()),
    (2, 'AAPL', 'SELL', 25.00, 155.00, 'CANCELED', CURRENT_TIMESTAMP()),
    (3, 'TSLA', 'BUY', 30.00, 220.00, 'PENDING', CURRENT_TIMESTAMP()),
    (4, 'NVDA', 'SELL', 40.00, 450.00, 'MATCHED', CURRENT_TIMESTAMP()),
    (5, 'META', 'BUY', 60.00, 300.00, 'PENDING', CURRENT_TIMESTAMP());