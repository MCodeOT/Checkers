let fieldSize;
let field;
let previousField;
const fieldArr = [];

let curX, curY, tarX, tarY;
let selectedPiece;
let firstClickedPiece;

window.onload = waitForJava;

/**
 * **Generates the UI for the checkerboard**
 * - fetches fieldsize from the controller-class in java
 * - copyies the board initially through copyBoard
 * - assigns coordinates to each field
 * - creates click-eventlistener
 *
 * @see {copyBoard}
 * @returns {void}
 */
function load_Board() {
    if (window.java) {
        fieldSize = window.java.getBoardSize();
    } else {
        fieldSize = 8;
    }

    const boardContainer = document.querySelector('.boardContainer');
    for (let row = fieldSize - 1; row >= 0; row--) {
        for (let col = 0; col < fieldSize; col++) {
            field = document.createElement('div');

            field.classList.add('baseField');
            field.classList.add((row + col) % 2 === 0 ? 'black' : 'white');


            field.dataset.x = col;
            field.dataset.y = row;

            boardContainer.appendChild(field);
            fieldArr.push(field);
            field.addEventListener('click', (event) => isClicked(event.currentTarget));
        }
    }
    copyBoard();
}

/**
 * **Calls the Java function copyBoard**
 */
function copyBoard() {
    if (!window.java) return;
    window.java.copyBoard();
}

/**
 * **Creates a piece of the corresponding color on the given field**
 * @param current1dPos one dimensional position of the piece that will be added
 * @param pieceColor color of the piece that will be added
 */
function createPiece(current1dPos, pieceColor) {
    const position = convertToTwoDimensional(current1dPos);
    const pieces = document.createElement('img');
    pieces.src = "img/draughts.svg";
    pieces.classList.add(pieceColor);

    const coordX = position.x;
    const coordY = position.y;

    document.querySelector('.boardContainer').querySelectorAll('*').forEach(element => {
        if (parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY) {
            element.innerHTML = '';
            element.appendChild(pieces);
        }
    });
}

/**
 * **Method called from Java and directing to the createPiece-Method with colour of the piece, that will be added**
 * @param current1dPos one dimensional position of the piece that will be added
 * @see createPiece
 */
function createBlackPiece(current1dPos) {
    createPiece(current1dPos, "black_player");
}

/**
 * **Method called from Java to create a white piece**
 * @param current1dPos one dimensional position of the piece that will be added
 * @see createPiece
 */
function createWhitePiece(current1dPos) {
    createPiece(current1dPos, "white_player")
}

/**
 * **Removes the playerpiece on the given coordinates**
 * @param current1dPos 1 dimensional position of the piece that will be removed
 */
function removePiece(current1dPos) {
    const position = convertToTwoDimensional(current1dPos);
    const coordX = position.x;
    const coordY = position.y;

    document.querySelector('.boardContainer').querySelectorAll('*').forEach(element => {
        if (parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY) {
            element.innerHTML = '';
        }
    });

}

/**
 * **Makes a callback to the GraphicalUI class in Java, to check if there is a piece is at the given position**
 * @param x coordinate
 * @param y coordinate
 * @returns {boolean} - true, if there is a piece at the given position
 */
function isPieceAtPosition(x, y) {
    if (window.java) {
        return window.java.isPieceAtPosition(x, y);
    }
    return true;
}

/**
 * **Makes a callback to the GraphicalUI class in Java, to check if the piece at the given position is black**
 * @param x coordinate
 * @param y coordinate
 * @returns {boolean} - true, if the piece at the given position is black
 */
function isPieceAtPositionBlack(x, y) {
    if (window.java) {
        return window.java.isPieceBlack(x, y);
    }
    return true;
}

/** **Makes a callback to java with the coordinate of the clicked field
 *
 * @param currentField
 */
function isClicked(currentField) {
    previousField = field;
    field = currentField;
    window.java.printShit(field.dataset.x + " " + field.dataset.y);

    if (window.java) {
        window.java.getActionFromController(convertToOneDimensional(field.dataset.x, field.dataset.y));
    }
}

/**
 * **Adds "active" highlight to the clicked playerpiece**
 */
function addHighlightToClickedPiece() {
    field.classList.add('active');
}

/**
 * **Removes highlight from clicked playerpiece**
 */
function removeHighlightFromClickedPiece() {
    document.querySelector('.active').classList.remove('active');
}

/**
 * **Adds "error" highlight, when a wrong field is clicked**
 */
function errorHighlight() {
    setTimeout(() => {
        field.classList.add('errorHighlight');
    }, 100);
    field.classList.remove('errorHighlight');
}

function addPositionToSidebar() {
    const paragraph = document.createElement("p");
    const sidebarBody = document.getElementById('sidebarBody');
    const position = document.createTextNode(previousField.dataset.x + " " + previousField.dataset.y + " → " + field.dataset.x + " " + field.dataset.y);
    paragraph.appendChild(position);

    sidebarBody.appendChild(paragraph);
    sidebarBody.scrollTop = sidebarBody.scrollHeight;


}

/**
 * **Converts given coordinates to 1 dimensional coordinates, meaning they are stored in one number instead of 2 seperates**
 * @param x coordinate
 * @param y coordinate
 * @returns {number}
 */
function convertToOneDimensional(x, y) {
    x = parseInt(x);
    y = parseInt(y);
    return y * fieldSize + x;
}

/**
 * Converts the 1 dimensional coordinate into x and y coordinate
 * @param i
 * @returns {{x: number, y: number}}
 */
function convertToTwoDimensional(i) {
    return {
        x: (parseInt(i % fieldSize)), y: (parseInt(i / fieldSize))
    };
}

function waitForJava() {
    const checkJava = setInterval(() => {
        if (window.java) {
            clearInterval(checkJava);
            console.log("window.java is available!");
            // You can now safely call Java methods👍
            // Example: window.java.someJavaMethod();👍
            load_Board();
        }
    }, 100); // Check every 100 milliseconds👍


}
