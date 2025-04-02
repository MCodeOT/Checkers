package de.check.checkers.structures;

// PTC stands for PositionTransmissionCode
public enum PTC {
    FAILURE_GENERIC_UNAVAILABLE_MOVE,
    FAILURE_BOTH_POSITIONS_IDENTICAL,
    FAILURE_NO_PIECE_ON_FIELD,
    FAILURE_WRONG_PLAYER_SELECTED,
    FAILURE_FIELD_NOT_AVAILABLE,
    FAILURE_FORCED_CAPTURE_EXISTS,

    SUCCESSFUL_GENERIC_AVAILABLE_MOVE,
    SUCCESSFUL_PLAYER_WON
}
