import React, { useState } from "react";
import { CSSTransition } from "react-transition-group";
import { ArticleCard } from "./ArticleCard.jsx";
import {useNavigate} from "react-router-dom";

const NewsSection = ({ newsData }) => {
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(1)
    const [direction, setDirection] = useState("forward")
    const articlesPerPage = 6

    const indexOfLastArticle = currentPage * articlesPerPage
    const indexOfFirstArticle = indexOfLastArticle - articlesPerPage
    const currentArticles = newsData.slice(indexOfFirstArticle, indexOfLastArticle)
    const totalPages = Math.ceil(newsData.length / articlesPerPage)

    const goToNextPage = () => {
        if (currentPage < totalPages) {
            setDirection("forward");
            setCurrentPage(currentPage + 1)
        }
    }

    const goToPrevPage = () => {
        if (currentPage > 1) {
            setDirection("backward");
            setCurrentPage(currentPage - 1)
        }
    }

    return (
        <div className="relative my-8 overflow-visible">
            <CSSTransition
                key={currentPage}
                timeout={500}
                classNames={{
                    enter: direction === "forward" ? "translate-x-full opacity-0" : "-translate-x-full opacity-0",
                    enterActive: "transform transition-transform duration-500 translate-x-0 opacity-100",
                    exit: "transform translate-x-0 opacity-100",
                    exitActive: direction === "forward" ? "-translate-x-full opacity-0" : "translate-x-full opacity-0",
                }}
            >
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {currentArticles.map((article) => (
                        <ArticleCard
                            key={article.id}
                            title={article.title}
                            description={article.description}
                            author={article.author}
                            image={article.imagePath}
                            onClick={() => navigate(`${article.id}`)}
                        />
                    ))}
                </div>
            </CSSTransition>

            <div className="flex justify-center mt-8 space-x-4">
                <button
                    onClick={goToPrevPage}
                    disabled={currentPage === 1}
                    className={`px-4 py-2 border rounded ${currentPage === 1 ? "bg-gray-300" : "bg-red-700 text-white hover:bg-red-800"}`}
                >
                    Poprzednia
                </button>
                <span className="flex items-center">{`Strona ${currentPage} z ${totalPages}`}</span>
                <button
                    onClick={goToNextPage}
                    disabled={currentPage === totalPages}
                    className={`px-4 py-2 border rounded ${currentPage === totalPages ? "bg-gray-300" : "bg-red-700 text-white hover:bg-red-800"}`}
                >
                    Nastepna
                </button>
            </div>
        </div>
    );
};

export { NewsSection };
