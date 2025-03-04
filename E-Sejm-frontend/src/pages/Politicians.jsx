import React, { useEffect, useState } from "react";
import { TransitionGroup, CSSTransition } from "react-transition-group";
import "../App.css";
import { PoliticianCard } from "../features/politicians/PoliticianCard.jsx";
import { SearchBar } from "../components/SearchBar.jsx";
import { useDispatch, useSelector } from "react-redux";
import { getMpsAction } from "../redux/MpService/Action.js";

function Politicians() {
    const [searchTerm, setSearchTerm] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 12;
    const dispatch = useDispatch();
    const { mps } = useSelector((store) => store.mp);

    const filteredDeputies = mps?.filter((deputy) => {
        const fullName = `${deputy.firstName} ${deputy.secondName} ${deputy.lastName}`.toLowerCase();
        return fullName.includes(searchTerm.toLowerCase());
    });

    const totalItems = filteredDeputies?.length || 0;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentDeputies = filteredDeputies?.slice(startIndex, endIndex);

    const handlePrevPage = () => {
        if (currentPage > 1) setCurrentPage((prev) => prev - 1);
    };

    const handleNextPage = () => {
        if (currentPage < totalPages) setCurrentPage((prev) => prev + 1);
    };

    useEffect(() => {
        dispatch(getMpsAction());
    }, [dispatch]);


    return (
        <div className="bg-gray-100 min-h-screen p-8 pt-20">
            <div className="w-full max-w-4xl mx-auto mb-10">
                <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
            </div>

            <div className="max-w-7xl mx-auto">
                <TransitionGroup className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
                    {currentDeputies?.map((deputy, index) => (
                        <CSSTransition
                            key={index}
                            timeout={300}
                            classNames="fade"
                        >
                            {ref => <PoliticianCard ref={ref} deputy={deputy} />}
                        </CSSTransition>
                    ))}
                </TransitionGroup>
            </div>
            
            <div className="flex justify-center mt-8 space-x-4">
                <button
                    onClick={handlePrevPage}
                    disabled={currentPage === 1}
                    className="px-4 py-2 bg-blue-500 text-white rounded disabled:opacity-50"
                >
                    Previous
                </button>
                <span className="px-4 py-2 bg-gray-200 rounded">
                    Page {currentPage} of {totalPages}
                </span>
                <button
                    onClick={handleNextPage}
                    disabled={currentPage === totalPages}
                    className="px-4 py-2 bg-blue-500 text-white rounded disabled:opacity-50"
                >
                    Next
                </button>
            </div>
        </div>
    );
}

export { Politicians };
