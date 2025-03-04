import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { format } from "date-fns";
import { useDispatch, useSelector } from "react-redux";
import { getProceedingByDateAction } from "../redux/ProceedingService/Action.js";
import { getStatementsByDateAction } from "../redux/StatementService/Action.js";
import { FaBookReader } from "react-icons/fa";
import {getVotingsByDateAction} from "../redux/VotingService/Action.js";
import { MdHowToVote } from "react-icons/md";

const Proceeding = () => {
    const { date } = useParams();
    const { meet } = useSelector((store) => store.proceeding);
    const { statements } = useSelector((store) => store.statement);
    const { votings } = useSelector((store) => store.voting);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [searchTerm, setSearchTerm] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(9);

    useEffect(() => {
        dispatch(getProceedingByDateAction(date));
        dispatch(getStatementsByDateAction(date));
        dispatch(getVotingsByDateAction(date));
    }, [date]);

    const filteredStatements = statements.filter((statement) =>
        statement.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const totalPages = Math.ceil(filteredStatements.length / itemsPerPage);
    const currentStatements = filteredStatements.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const handlePageChange = (direction) => {
        if (direction === "next" && currentPage < totalPages) {
            setCurrentPage((prev) => prev + 1);
        } else if (direction === "prev" && currentPage > 1) {
            setCurrentPage((prev) => prev - 1);
        }
    };

    return (
        <div className="bg-gray-100 min-h-screen">
            <div
                className="relative h-48 bg-cover bg-center mt-4 mx-auto max-w-6xl rounded-lg shadow-lg"
                style={{
                    backgroundImage: `url('https://24kurier.pl/media/os1n0ngs/zrzut-ekranu-2023-11-13-o-12-09-22.jpg')`,
                    backgroundColor: "#061D31",
                    filter: "brightness(0.65)",
                }}
            >
                <div className="absolute inset-0 bg-[#061D31] opacity-40 rounded-lg"></div>
                <div className="relative z-10 text-white text-center flex flex-col items-center justify-center h-full">
                    <h1 className="text-3xl font-bold">
                        {meet?.proceeding?.title || "Posiedzenie Sejmu"}
                    </h1>
                    <p className="text-2xl mt-2">Kadencja: 10</p>
                    <p className="text-lg mt-1">{meet?.date || "Brak daty"}</p>
                </div>
            </div>

            <main className="max-w-6xl mx-auto py-6">
                <div className="mb-12">
                    <div className="flex items-center space-x-4 mb-6">
                        <h2 className="text-3xl font-bold text-gray-800">Wypowiedzi</h2>
                        <div className="flex-grow border-t-8 border-lineColor"></div>
                    </div>
                    <section className="bg-white shadow-lg rounded-lg p-6">
                        <div className="relative mb-6">
                            <input
                                type="text"
                                placeholder="Wyszukaj po nazwie..."
                                className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />
                            <div className="absolute inset-y-0 right-4 flex items-center">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    className="h-5 w-5 text-gray-400"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    stroke="currentColor"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth="2"
                                        d="M10 14l2-2m0 0l2-2m-2 2H3m13 4l-2 2m0 0l-2-2m2 2V7"
                                    />
                                </svg>
                            </div>
                        </div>
                        {currentStatements.length > 0 ? (
                            <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                                {currentStatements.map((statement) => (
                                    <li
                                        key={statement.id}
                                        className="relative bg-blue-100 shadow-md p-4 rounded-lg overflow-hidden hover:shadow-lg transition duration-300 cursor-pointer group"
                                        onClick={() =>
                                            navigate(`wypowiedz/${statement.id}/num/${statement.num}`)
                                        }
                                    >
                                        <div
                                            className="absolute inset-0 bg-[#061D31] opacity-0 group-hover:opacity-90 transition-opacity duration-500"
                                        ></div>
                                        <div
                                            className="absolute inset-0 flex items-center justify-center z-30"
                                        >
                                            <div
                                                className="bg-blue-50 rounded-full p-3 transition-transform duration-500 transform scale-0 group-hover:scale-100"
                                            >
                                                <FaBookReader className="text-gray-900 text-2xl"/>
                                            </div>
                                        </div>
                                        <div
                                            className="relative z-20 transition-colors duration-500 group-hover:text-gray-400"
                                        >
                                            <div className="flex justify-between items-center mb-2">
                                                <h3 className="text-lg font-bold text-gray-800 group-hover:text-blue-200 transition-colors duration-500">
                                                    {statement.name}
                                                </h3>
                                                <span
                                                    className="text-sm text-gray-500 group-hover:text-blue-50 transition-colors duration-500"
                                                >
                                                    {format(
                                                        new Date(statement.startDateTime),
                                                        "yyyy-MM-dd HH:mm"
                                                    )}
                                                </span>
                                            </div>
                                            <p className="text-gray-600 group-hover:text-blue-100 transition-colors duration-500">
                                                Funkcja: {statement.function || "Brak"}
                                            </p>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p className="text-gray-600">Brak wypowiedzi dla tego posiedzenia.</p>
                        )}

                        {totalPages > 1 && (
                            <div className="flex flex-col items-center mt-6 space-y-4">
                                <span className="text-gray-700">
                                    Strona {currentPage} z {totalPages}
                                </span>

                                <div className="flex space-x-4">
                                    <button
                                        onClick={() => handlePageChange("prev")}
                                        className={`px-4 py-2 rounded-lg transition duration-300 ${
                                            currentPage === 1
                                                ? "bg-gray-400 text-gray-600 cursor-not-allowed"
                                                : "bg-[#061D31] text-white hover:bg-[#042d53cc]"
                                        }`}
                                        disabled={currentPage === 1}
                                    >
                                        Poprzednia
                                    </button>
                                    <button
                                        onClick={() => handlePageChange("next")}
                                        className={`px-4 py-2 rounded-lg transition duration-300 ${
                                            currentPage === totalPages
                                                ? "bg-gray-400 text-gray-600 cursor-not-allowed"
                                                : "bg-[#061D31] text-white hover:bg-[#042d53cc]"
                                        }`}
                                        disabled={currentPage === totalPages}
                                    >
                                        Następna
                                    </button>
                                </div>
                            </div>
                        )}
                    </section>
                </div>

                <div className="mb-12">
                    <div className="flex items-center space-x-4 mb-6">
                        <h2 className="text-3xl font-bold text-gray-800">Lista Głosowań</h2>
                        <div className="flex-grow border-t-8 border-lineColor"></div>
                    </div>
                    <section className="bg-white shadow-lg rounded-lg p-6">
                        <div className="relative mb-6">
                            <input
                                type="text"
                                placeholder="Wyszukaj po temacie lub tytule..."
                                className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />
                        </div>

                        {votings && votings.length > 0 ? (
                            <ul className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                                {votings
                                    .filter((voting) =>
                                        (voting.topic || voting.title)
                                            .toLowerCase()
                                            .includes(searchTerm.toLowerCase())
                                    )
                                    .map((voting) => (
                                        <li
                                            key={voting.id}
                                            className="relative bg-blue-100 shadow-md p-4 rounded-lg overflow-hidden hover:shadow-lg transition duration-300 cursor-pointer group"
                                            onClick={() => navigate(`/glosowania/${voting.id}`)}
                                        >
                                            <div
                                                className="absolute inset-0 bg-[#061D31] opacity-0 group-hover:opacity-90 transition-opacity duration-500"></div>
                                            <div className="absolute inset-0 flex items-center justify-center z-30">
                                                <div
                                                    className="bg-blue-50 rounded-full p-3 transition-transform duration-500 transform scale-0 group-hover:scale-100">
                                                    <MdHowToVote  className="text-gray-900 text-2xl"/>
                                                </div>
                                            </div>
                                            <div
                                                className="relative z-20 transition-colors duration-500 group-hover:text-gray-400">
                                                <div className="flex flex-col mb-2">
                                                    <h3 className="text-lg font-bold text-gray-800 group-hover:text-blue-200 transition-colors duration-500">
                                                        {voting.topic || voting.title}
                                                    </h3>
                                                    <span
                                                        className="text-sm text-gray-500 group-hover:text-blue-50 transition-colors duration-500">
                                        {format(new Date(voting.date), "yyyy-MM-dd HH:mm")}
                                    </span>
                                                </div>
                                            </div>
                                        </li>
                                    ))}
                            </ul>
                        ) : (
                            <p className="text-gray-600">Brak głosowań dla tego posiedzenia.</p>
                        )}
                    </section>
                </div>


            </main>
        </div>
    );
};

export default Proceeding;
