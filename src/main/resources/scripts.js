let fieldSize;
let field;
let previousField;
const fieldArr=[];

let curX, curY, tarX, tarY;
let selectedPiece;
let firstClickedPiece;

window.onload = waitForJava;

/**
 * **Generates the UI for the checkerboard**
 * - fetches fieldsize from the controller-class in java
 * - adds playerpieces through addPlayerPieces function
 * - assigns coordinates to each field
 * - creates click-eventlistener
 *
 * @see {addPlayerPiece}
 * @returns {void}
 */
function load_Board() {

//    fieldSize = window.java.getBoardSize();
    fieldSize = 8;

    const container = document.querySelector('.container');
    for (let row = fieldSize - 1; row >= 0; row--) {
        for (let col = 0; col < fieldSize; col++) {
            field = document.createElement('div');

            field.classList.add((row + col) % 2 === 0 ? 'black' : 'white');

            addPlayerPiece(col, row);

            field.dataset.x = col;
            field.dataset.y = row;

            container.appendChild(field);
            fieldArr.push(field);
            field.addEventListener('click', (event) => isClicked(event.currentTarget));
        }
    }
}

/**
 * **Adds a playerpiece according to the curent positions in the board-java class**
 * - appends the piece as a child to the currently selected field
 * @param x coordinate
 * @param y coordinate
 * @return {void}
 */
function addPlayerPiece(x, y){
    if (isPieceAtPosition(x, y)) {
        const pieces = document.createElement('img'); // <img>
        pieces.src = "img/draughts.svg";
        if (isPieceAtPositionBlack(x, y)) {
            pieces.classList.add('black_player');
        } else {
            pieces.classList.add('white_player'); //<img class="white_player">
        }
        field.appendChild(pieces);
    }
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

// function isClicked(currentField) {
//     field = currentField;
//     const fieldPiece = field.firstChild;
//
//     if (firstClickedPiece) {
//         if (field === firstClickedPiece) {
//             highlightClickedPiece(selectedPiece);
//             firstClickedPiece = null;
//             selectedPiece = null;
//         } else {
//             if (!fieldPiece) {
//                 movePieces(firstClickedPiece.dataset.x, firstClickedPiece.dataset.y, field.dataset.x, field.dataset.y);
//                 firstClickedPiece = null;
//                 selectedPiece = null;
//             }
//         }
//     } else {
//         if (fieldPiece) {
//             firstClickedPiece = field;
//             selectedPiece = fieldPiece;
//             highlightClickedPiece(selectedPiece);
//         }else{
//
//         }
//     }
// }

/** **Makes a callback to java with the coordinate of the clicked field
 *
 * @param currentField
 */
function isClicked(currentField){
    previousField = field;
    field = currentField;
    if (window.java) {
        window.java.getActionFromController(convertToOneDimensional(field.dataset.x, field.dataset.y));
    }
}


function addHighlightToClickedPiece(data) {
    const position = convertToTwoDimensional(data);
    let coordX = position.x;
    let coordY = position.y;

    field.classList.add('active');
}

function removeHighlightFromClickedPiece(data){
    const position = convertToTwoDimensional(data);
    let coordX = position.x;
    let coordY = position.y;

    document.querySelector('.active').classList.remove('active');
}

function convertToOneDimensional(x, y){
    x = parseInt(x);
    y = parseInt(y);
    return y * fieldSize + x;
}
function convertToTwoDimensional(i){
    return {
        x: (parseInt(i % fieldSize)),
        y: (parseInt(i / fieldSize))
    };
}

function waitForJava() {
    const checkJava = setInterval(() => {
        if (window.java) {
            clearInterval(checkJava);
            console.log("window.java is available!");
            load_Board();
            // You can now safely call Java methods👍
            // Example: window.java.someJavaMethod();👍
        }
    }, 100); // Check every 100 milliseconds👍
}
