import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchThreadsAction } from "../redux/PostService/Action.js";
import Thread from "../components/Forum/Thread.jsx";
import SortThreads from "../components/Forum/SortThreads.jsx";
import { Link } from "react-router-dom";

function ForumPage() {
    const [searchTerm, setSearchTerm] = useState("");
    const [isSortOpen, setIsSortOpen] = useState(false);
    const [sortOption, setSortOption] = useState(0); // Default to "Data (najnowsze)"
    const [currentPage, setCurrentPage] = useState(1);

    const dispatch = useDispatch();
    const threads = useSelector((state) => state.thread.data);
    const totalPages = useSelector((state) => state.thread.totalPages);

    // Fetch threads when sortOption or currentPage changes
    useEffect(() => {
        dispatch(fetchThreadsAction({ sortOption, page: currentPage - 1, size: 12 }));
    }, [dispatch, sortOption, currentPage]);

    const changePage = (page) => {
        if (page >= 1 && page <= totalPages) {
            setCurrentPage(page);
        }
    };

    const filteredThreads = threads.filter((thread) =>
        thread.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="p-32">
            <h1 className="text-3xl font-bold text-center mb-10">Forum Dyskusyjne</h1>

            <div className="max-w-6xl mx-auto">
                <div className="flex justify-between items-center mb-6 space-x-4">
                    <div className="relative">
                        <button
                            className="px-6 py-2 bg-blue-900 text-white rounded-full hover:bg-blue-800"
                            onClick={() => setIsSortOpen(!isSortOpen)}
                        >
                            Sortuj po: {["Data (najnowsze)", "Data (najstarsze)", "Liczba komentarzy", "Oceny"][sortOption]}
                        </button>

                        {isSortOpen && (
                            <div className="absolute mt-2 bg-white shadow-lg rounded-md z-10">
                                <SortThreads
                                    onSortChange={(option) => {
                                        setSortOption(option);
                                        setIsSortOpen(false);
                                        setCurrentPage(1);
                                    }}
                                />
                            </div>
                        )}
                    </div>

                    <div className="flex-grow">
                        <div className="relative w-full max-w-md mx-auto">
                            <input
                                type="text"
                                placeholder="Szukaj..."
                                className="w-full px-4 py-2 border border-gray-300 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                            />
                        </div>
                    </div>

                    <Link to="/dodaj-temat">
                        <button className="px-6 py-2 bg-blue-900 text-white rounded-full hover:bg-blue-800">
                            Dodaj nowy temat
                        </button>
                    </Link>
                </div>

                <div className="grid grid-cols-3 gap-8">
                    {filteredThreads.map((thread) => (
                        <Link to={`/forum/${thread.id}`} key={thread.id}>
                            <Thread
                                title={thread.title}
                                category={thread.categories.map((cat) => cat.name).join(", ")}
                                date={thread.createDate}
                                comments={thread.commentsCount}
                                score={thread.score}
                            />
                        </Link>
                    ))}
                </div>

                <div className="flex justify-center items-center space-x-4 mt-10">
                    <button
                        className="px-6 py-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:opacity-50"
                        onClick={() => changePage(currentPage - 1)}
                        disabled={currentPage === 1}
                    >
                        Poprzednia
                    </button>
                    <span className="text-lg text-gray-700 font-medium">
                        Strona {currentPage} z {totalPages}
                    </span>
                    <button
                        className="px-6 py-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:opacity-50"
                        onClick={() => changePage(currentPage + 1)}
                        disabled={currentPage === totalPages}
                    >
                        Następna
                    </button>
                </div>
            </div>
        </div>
    );
}

export { ForumPage };
