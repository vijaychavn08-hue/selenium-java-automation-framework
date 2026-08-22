// Dynamic DOM discovery script for page health and elements overview
var items = document.querySelectorAll('.inventory_item');
var header = document.querySelector('.app_logo');
var cart = document.querySelector('.shopping_cart_link');

return {
    itemCount: items.length,
    appTitle: header ? header.innerText : null,
    cartAvailable: cart !== null,
    url: window.location.href
};
