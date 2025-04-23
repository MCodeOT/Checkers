let fieldSize;
let fieldSizeIsSet;
let field;
let previousField;
const fieldArr = [];

let curX, curY, tarX, tarY;
let selectedPiece;
let firstClickedPiece;
let wasPieceCrowned;

window.onload = waitForJava;

function boardSizeInput() {
    const elements = [
        {selector: '.sidebarHeader8x8', size: 8},
        {selector: '.sidebarHeader10x10', size: 10},
        {selector: '.sidebarHeader12x12', size: 12}
    ];

    elements.forEach(entry => {
        const el = document.querySelector(entry.selector);
        if (el) {
            el.addEventListener('click', () => {
                // Falls die Größe schon gesetzt ist → nichts tun
                if (fieldSizeIsSet) return;

                // Setze Größe
                window.java.setBoardSize(entry.size);
                window.java.printShit("Größe geändert auf: " + entry.size);
                fieldSizeIsSet = true;

                // Visuelles Feedback: Buttons deaktivieren
                elements.forEach(e => {
                    const btn = document.querySelector(e.selector);
                    if (btn) {
                        btn.style.display = 'none';
                    }
                });

                loadBoard();
            });
        }
    });
}

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
function loadBoard() {
    if (window.java) {
        fieldSize = window.java.getBoardSize();
    }

    const boardContainer = document.querySelector('.boardBorder');

    boardContainer.style.gridTemplateRows = `0.5fr repeat(${fieldSize}, 1fr) 0.5fr`;
    boardContainer.style.gridTemplateColumns = `0.5fr repeat(${fieldSize}, 1fr) 0.5fr`;

    const checkerBoard = document.querySelector('.checkerBoard');
    checkerBoard.style.gridArea = `2/2/(${fieldSize + 2})/(${fieldSize + 2})`;

    checkerBoard.style.gridRow = `2 / ${fieldSize + 2}`;
    checkerBoard.style.gridColumn = `2 / ${fieldSize + 2}`;

    checkerBoard.style.display = 'grid';
    checkerBoard.style.gridTemplateColumns = `repeat(${fieldSize}, 1fr)`;
    checkerBoard.style.gridTemplateRows = `repeat(${fieldSize}, 1fr)`;
    checkerBoard.style.outline = '2px solid #4DA147FF';

    const totalSize = fieldSize + 2;

    for (let row = 0; row < totalSize; row++) {
        for (let col = 0; col < totalSize; col++) {

            if (row > 0 && row < totalSize - 1 && col > 0 && col < totalSize - 1) {
                continue;
            }
            const borderCell = document.createElement('div');
            borderCell.classList.add('borderDiv');
            borderCell.style.backgroundColor = '#eae8d9';

            // if (row === 0 && col > 0 && col < totalSize - 1) {
            //     borderCell.textContent = String.fromCharCode(64 + col); // Buchstaben (A, B, C, ...)
            // } else if (row === totalSize - 1 && col > 0 && col < totalSize - 1) {
            //     borderCell.textContent = String.fromCharCode(64 + col); // Buchstaben (A, B, C, ...)
            // } else if (col === 0 && row > 0 && row < totalSize - 1) {
            //     borderCell.textContent = totalSize - 1 - row; // Zahlen (8, 7, 6, ...)
            // } else if (col === totalSize - 1 && row > 0 && row < totalSize - 1) {
            //     borderCell.textContent = totalSize - 1 - row; // Zahlen (8, 7, 6, ...)
            // }

            if (row === 0 && col > 0 && col < totalSize - 1) {
                borderCell.textContent = col-1; // Zahlen für obere Achse
            } else if (row === totalSize - 1 && col > 0 && col < totalSize - 1) {
                borderCell.textContent = col-1; // Zahlen für untere Achse
            } else if (col === 0 && row > 0 && row < totalSize - 1) {
                borderCell.textContent = totalSize - 1 - row-1; // Zahlen für linke Achse
            } else if (col === totalSize - 1 && row > 0 && row < totalSize - 1) {
                borderCell.textContent = totalSize - 1 - row-1; // Zahlen für rechte Achse
            }

            boardContainer.appendChild(borderCell);
        }
    }


    for (let row = fieldSize - 1; row >= 0; row--) {
        for (let col = 0; col < fieldSize; col++) {
            field = document.createElement('div');

            field.classList.add('baseField');
            field.classList.add((row + col) % 2 === 0 ? 'black' : 'white');


            field.dataset.x = col;
            field.dataset.y = row;

            checkerBoard.appendChild(field);
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

    const wrapper = document.createElement('div');
    wrapper.classList.add("piece");

    const pieces = document.createElement('img');
    pieces.src = "img/checkerPiece(old).svg";
    pieces.classList.add(pieceColor);

    wrapper.appendChild(pieces);

    if (isPieceCrowned(position.x, position.y)) {
        const crown = document.createElement('img');
        crown.src = "img/crown.svg";
        crown.classList.add("crown");
        wrapper.appendChild(crown);
    }

    const coordX = position.x;
    const coordY = position.y;

    document.querySelectorAll('.baseField').forEach(element => {
        if (parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY) {
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }
            element.appendChild(wrapper);
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

    document.querySelector('.boardBorder').querySelectorAll('*').forEach(element => {
        if (parseInt(element.dataset.x) === coordX && parseInt(element.dataset.y) === coordY) {
            element.innerHTML = '';
        }
    });

}

/**
 * **Makes a callback to the GraphicalUI class in Java, to check if there is a piece is at the given position**
 * @param x coordinate
 * @param y coordinate
 * @returns {boolean}  true, if there is a piece at the given position
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
 * @returns {boolean} true, if the piece is black
 */
function isPieceAtPositionBlack(x, y) {
    if (window.java) {
        return window.java.isPieceBlack(x, y);
    }
    return true;
}

/**
 * **Makes a callback to the GrahpicalUI class in Java, to check if the piece at the given position is crowned **
 * @param x coordinate
 * @param y coordinate
 * @returns {boolean} true, if the piece is crowned
 */
function isPieceCrowned(x, y) {
    return window.java.isPieceCrowned(x, y);
}

/**
 * **Makes a callback to java with the coordinate of the clicked field
 *
 * @param currentField
 */
function isClicked(currentField) {
    previousField = field;
    field = currentField;
    wasPieceCrowned = isPieceCrowned(previousField.dataset.x, previousField.dataset.y);
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
    field.classList.remove('errorHighlight');
    void field.offsetWidth; // Reflow, damit Animation erneut triggert
    field.classList.add('errorHighlight');

    // Nach Ablauf der Animation (z. B. 400ms) Klasse wieder entfernen
    setTimeout(() => {
        field.classList.remove('errorHighlight');
    }, 600);
}

function addPositionToSidebar() {
    const sidebarBody = document.getElementById('sidebarBody');
    const paragraph = document.createElement("div");
    paragraph.classList.add("sidebarMoveEntry");

    // 1. Erstelle den farbigen Kreis (je nach Spielerfarbe)
    const colorIndicator = document.createElement("span");
    colorIndicator.classList.add("moveIndicator");

    const x = parseInt(field.dataset.x);
    const y = parseInt(field.dataset.y);
    const isBlack = isPieceAtPositionBlack(x, y);
    colorIndicator.style.backgroundColor = isBlack ? "black" : "white";
    colorIndicator.style.border = "1px solid black"; // für weißen Stein sichtbar

    // 2. Textinhalt
    const text = document.createElement("span");
    // text.textContent = `${parseInt(previousField.dataset.x) + 1} ${parseInt(previousField.dataset.y) + 1} → ${x + 1} ${y + 1}`;
    text.textContent = `${parseInt(previousField.dataset.x)} ${parseInt(previousField.dataset.y)} → ${x} ${y}`;

    // 3. Baue den DOM-Eintrag zusammen
    paragraph.appendChild(colorIndicator);
    paragraph.appendChild(text);

    // 4. Krone-Styling bei Bedarf
    if (isPieceCrowned(field.dataset.x, field.dataset.y) && !wasPieceCrowned) {
        paragraph.classList.add('sidebarBodyCrownedPiece');
    }

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
            window.java.printShit("Ping zum Aufwärmen.");
            boardSizeInput();
        }
    }, 100); // Check every 100 milliseconds👍


}
