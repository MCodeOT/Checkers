let fieldSize = 8;

window.onload = init;
//Funktion um das Board zu erstellen
function load_Board() {

    const container = document.querySelector('#container');
    for (let col = 0; col < fieldSize; col++) {
        for (let row = 0; row < fieldSize; row++) {
            const field = document.createElement('div');

            field.classList.add((col + row) % 2 === 0 ? 'white' : 'black');

            field.dataset.x = row+1;
            field.dataset.y = fieldSize-col;

            container.appendChild(field);
            field.addEventListener('click', (event) => isClicked(event.currentTarget));


        }
    }
}


function isClicked(field) {
    const color = field.classList.contains('black') ? 'black' : 'white';

    const x = field.dataset.x;
    const y = field.dataset.y;

    // Java-Callback aufrufen
    if (!field.classList.contains('active')) {
        if (window.java) {
            window.java.handleFieldClick(`${color}, ${x}, ${y}`); // Übergibt die Farbe und Koordinaten an Java
        }
    }

    if (field.classList.contains('active')) {
        field.classList.remove('active');

    } else {
        document.querySelectorAll('.active').forEach(f => {
            f.classList.remove('active');
        });
        field.classList.add('active');
    }



}

function getCoordinates()    {

}

function init() {
    load_Board();
}

function reloadCheckers()    {
    console.log('reloaded checkers');
    handleReloadCheckers();

}


