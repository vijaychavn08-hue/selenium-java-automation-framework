// Product discovery script extracting names and prices directly from DOM
var elements = document.querySelectorAll('.inventory_item');
var products = [];

elements.forEach(function(el) {
    var nameEl = el.querySelector('.inventory_item_name');
    var priceEl = el.querySelector('.inventory_item_price');
    if (nameEl && priceEl) {
        products.push({
            name: nameEl.innerText.trim(),
            price: priceEl.innerText.trim()
        });
    }
});

return products;
