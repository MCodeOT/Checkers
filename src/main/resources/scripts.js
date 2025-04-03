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
 * - copyies the board initially through copyBoard
 * - assigns coordinates to each field
 * - creates click-eventlistener
 *
 * @see {copyBoard}
 * @returns {void}
 */
function load_Board() {

   fieldSize = window.java.getBoardSize();
//     fieldSize = 8;

    const container = document.querySelector('.container');
    for (let row = fieldSize - 1; row >= 0; row--) {
        for (let col = 0; col < fieldSize; col++) {
            field = document.createElement('div');

            field.classList.add((row + col) % 2 === 0 ? 'black' : 'white');

            field.dataset.x = col;
            field.dataset.y = row;

            container.appendChild(field);
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
    window.java.copyBoard();
}
function createPiece(current1dPos, pieceColor){
    const position = convertToTwoDimensional(current1dPos);
    const pieces = document.createElement('img'); // <img>
    pieces.src = "img/draughts.svg";
    pieces.classList.add(pieceColor);

    const coordX = position.x;
    const coordY = position.y;

    document.querySelector('.container').querySelectorAll('*').forEach(element=>{
        if(parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY){
            element.innerHTML = '';
            element.appendChild(pieces);
        }
    });
}
function createBlackPiece(current1dPos){
    createPiece(current1dPos, "black_player");
}

function createWhitePiece(current1dPos){
    createPiece(current1dPos, "white_player")
}

function removePiece(current1dPos){
    const position = convertToTwoDimensional(current1dPos);
    const coordX = position.x;
    const coordY = position.y;

    document.querySelector('.container').querySelectorAll('*').forEach(element=>{
        if(parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY){
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
function isClicked(currentField){
    previousField = field;
    field = currentField;
    if (window.java) {
        window.java.getActionFromController(convertToOneDimensional(field.dataset.x, field.dataset.y));
    }
}

function addHighlightToClickedPiece() {
    field.classList.add('active');
}

function removeHighlightFromClickedPiece(){
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
