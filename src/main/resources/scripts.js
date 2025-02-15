let fieldSize = 8;

window.onload = init;
//Funktion um das Board zu erstellen
function load_Board() {

    const container = document.querySelector('#container');
    for (let row = 0; row < fieldSize; row++) {
        for (let col = 0; col < fieldSize; col++) {
            const field = document.createElement('div');

            field.classList.add((row + col) % 2 === 0 ? 'white' : 'black');
            container.appendChild(field);
            field.addEventListener('click', (event) => isClicked(event.currentTarget));

        }
    }
}


function isClicked(field) {
    const color = field.classList.contains('black') ? 'black' : 'white';

    // Java-Callback aufrufen
    if (!field.classList.contains('active')) {
        if (window.java) {
            window.java.handleFieldClick(color); // Übergibt die Farbe an Java
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


function init() {
    load_Board();
}



