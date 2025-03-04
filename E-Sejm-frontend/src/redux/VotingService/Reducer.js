import {
    GET_ALL_VOTINGS_REQUEST,
    GET_VOTING_DETAILS_REQUEST,
    SEARCH_VOTINGS_REQUEST,
    GET_VOTES_BY_VOTING_REQUEST, GET_VOTINGS_BY_DATE_REQUEST,
} from "./ActionType.js";

const initialValue = {
    votings: null, // Lista wszystkich głosowań
    votingDetails: null, // Szczegóły wybranego głosowania
    searchResults: null, // Wyniki wyszukiwania głosowań
    votes: null, // Lista głosów dla konkretnego głosowania
};

export const votingReducer = (state = initialValue, { type, payload }) => {
    switch (type) {
        case GET_ALL_VOTINGS_REQUEST:
        case SEARCH_VOTINGS_REQUEST:
            return { ...state, votings: payload }; // Obsługuje listę głosowań
        case GET_VOTING_DETAILS_REQUEST:
            return { ...state, votingDetails: payload }; // Obsługuje szczegóły głosowania
        case GET_VOTES_BY_VOTING_REQUEST:
            return { ...state, votes: payload }; // Obsługuje głosy dla danego głosowania
        case GET_VOTINGS_BY_DATE_REQUEST:
            return { ...state, votings: payload };
        default:
            return state;
    }
};
