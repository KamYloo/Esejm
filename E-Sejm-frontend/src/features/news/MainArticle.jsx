import React from 'react';
import {useNavigate} from "react-router-dom";

const MainArticle = ({ article }) => {
    const navigate = useNavigate();

    return (
        <div className="flex flex-col lg:flex-row gap-6">
            <div className="flex-1">
                <div className="mb-3">
                    {article?.categories?.map((category, index) => (
                        <span
                            key={index}
                            className="bg-red-500 text-white text-sm font-semibold px-2 py-1 rounded m-1 inline-block">
                            {category.name}
                        </span>
                    ))}

                </div>
                <h1 className="text-2xl font-bold leading-snug mb-4">
                    {article?.title}
                </h1>
                <div className="text-gray-500 text-sm flex items-center mb-3">
                    <span>5 May 2024</span>
                    <span className="mx-2">•</span>
                    <span>{article?.author.firstName} {article?.author.lastName}</span>
                </div>

                <div
                    className="text-gray-700 mb-4"
                    dangerouslySetInnerHTML={{__html: article?.content.substring(0, 150)}}
                ></div>

                <button className="text-blue-600 font-semibold" onClick={() => navigate(`${article?.id}`)}>Czytaj
                    Więcej...
                </button>
            </div>
            <div className="flex-1">
                <img
                    src={article?.imagePath}
                    alt="Main Article"
                    className="w-full rounded-md object-cover"
                />
            </div>
        </div>
    );
};

export {MainArticle};
