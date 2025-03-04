import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllVotingsAction, searchVotingsAction } from "../redux/VotingService/Action.js";
import VotingList from "../features/Voting/VotingList.jsx";
import VotingSearch from "../features/Voting/VotingSearch.jsx";
import VotingFilter from "../features/Voting/VotingFilter.jsx";

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
    const handlePageChange = (page) => {
        if (page >= 0 && page < totalPages) {
            onPageChange(page);
        }
    };

    return (
        <div className="flex justify-center mt-6">
            <button
                className="px-4 py-2 bg-blue-500 text-white rounded-lg mx-2"
                onClick={() => handlePageChange(currentPage - 1)}
                disabled={currentPage === 0}
            >
                Poprzednia
            </button>
            <span className="px-4 py-2">
                Strona {currentPage + 1} z {totalPages}
            </span>
            <button
                className="px-4 py-2 bg-blue-500 text-white rounded-lg mx-2"
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={currentPage + 1 >= totalPages}
            >
                Następna
            </button>
        </div>
    );
};

const VotingPage = () => {
    const dispatch = useDispatch();
    const votings = useSelector((state) => state.voting.votings?.content || []);
    const totalPages = useSelector((state) => state.voting.votings?.totalPages || 1);
    const [searchQuery, setSearchQuery] = useState("");
    const [filters, setFilters] = useState({
        sessionNumber: "",
        votingNumber: "",
    });
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(20);

    useEffect(() => {
        const fetchData = async () => {
            const queryParams = {
                page: currentPage,
                size: pageSize,
                ...(searchQuery && { title: searchQuery }), // Dodajemy tylko, gdy wartość istnieje
                ...(filters.sessionNumber && { meetingId: filters.sessionNumber }),
                ...(filters.votingNumber && { votingNumber: filters.votingNumber }),
            };

            dispatch(searchVotingsAction(queryParams));
        };
        fetchData();
    }, [dispatch, currentPage, pageSize, searchQuery, filters]);

    const handleSearch = (query) => {
        setSearchQuery(query);
        setCurrentPage(0); // Resetujemy stronę do pierwszej
    };

    const handleFilter = (sessionNumber, votingNumber) => {
        setFilters({ sessionNumber, votingNumber });
        setCurrentPage(0); // Resetujemy stronę do pierwszej
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div className="min-h-screen bg-gray-100 pt-24">
            <div className="container mx-auto max-w-7xl px-4">
                <h1 className="text-4xl font-bold text-gray-800 mb-6">Lista Głosowań</h1>
                <VotingSearch onSearch={handleSearch} />
                <VotingFilter onFilter={handleFilter} />
                <VotingList votings={votings} />
                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={handlePageChange}
                />
            </div>
        </div>
    );
};

export default VotingPage;
